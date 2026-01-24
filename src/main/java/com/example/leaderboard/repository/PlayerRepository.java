package com.example.leaderboard.repository;

import com.example.leaderboard.domain.Player;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/** Spring Data access to {@link Player}. Custom queries are added as features need them. */
public interface PlayerRepository extends JpaRepository<Player, UUID> {}
