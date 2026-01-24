package com.example.leaderboard.repository;

import com.example.leaderboard.domain.Friendship;
import com.example.leaderboard.domain.FriendshipId;
import org.springframework.data.jpa.repository.JpaRepository;

/** Spring Data access to {@link Friendship}. Custom queries are added as features need them. */
public interface FriendshipRepository extends JpaRepository<Friendship, FriendshipId> {}
