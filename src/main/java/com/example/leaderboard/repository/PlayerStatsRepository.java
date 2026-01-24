package com.example.leaderboard.repository;

import com.example.leaderboard.domain.PlayerStatsEntity;
import com.example.leaderboard.domain.PlayerStatsId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data access to {@link PlayerStatsEntity}. Custom queries are added as features need them.
 */
public interface PlayerStatsRepository extends JpaRepository<PlayerStatsEntity, PlayerStatsId> {}
