package com.example.leaderboard;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.leaderboard.domain.Game;
import com.example.leaderboard.domain.Leaderboard;
import com.example.leaderboard.domain.Player;
import com.example.leaderboard.domain.Region;
import com.example.leaderboard.domain.ResetPeriod;
import com.example.leaderboard.domain.Score;
import com.example.leaderboard.domain.ScoreId;
import com.example.leaderboard.domain.SortOrder;
import com.example.leaderboard.repository.GameRepository;
import com.example.leaderboard.repository.LeaderboardRepository;
import com.example.leaderboard.repository.PlayerRepository;
import com.example.leaderboard.repository.ScoreRepository;
import com.example.leaderboard.support.IntegrationTestSupport;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

/**
 * Proves that Flyway migrations apply cleanly, Hibernate's {@code ddl-auto=validate} accepts the
 * entity mappings, and the {@code @Version} column really rejects stale writes.
 */
class SchemaIT extends IntegrationTestSupport {

  @Autowired GameRepository games;
  @Autowired LeaderboardRepository leaderboards;
  @Autowired PlayerRepository players;
  @Autowired ScoreRepository scores;

  @Test
  void staleScoreUpdateIsRejectedByVersionColumn() {
    games.save(new Game("schema-it", "Schema IT"));
    leaderboards.save(
        new Leaderboard("schema-it", "high", "High", SortOrder.DESC, ResetPeriod.NONE));
    Player player = players.save(new Player(UUID.randomUUID(), "Tester", Region.NA));
    ScoreId id = new ScoreId("schema-it", "high", "all", player.getPlayerId());
    scores.save(new Score(id, 100, Instant.now()));

    Score first = scores.findById(id).orElseThrow();
    Score stale = scores.findById(id).orElseThrow();

    first.offer(200, SortOrder.DESC, Instant.now());
    scores.save(first);
    assertThat(scores.findById(id).orElseThrow().getVersion()).isEqualTo(1);

    stale.offer(150, SortOrder.DESC, Instant.now());
    assertThatThrownBy(() -> scores.save(stale))
        .isInstanceOf(ObjectOptimisticLockingFailureException.class);
    assertThat(scores.findById(id).orElseThrow().getBestScore()).isEqualTo(200);
  }
}
