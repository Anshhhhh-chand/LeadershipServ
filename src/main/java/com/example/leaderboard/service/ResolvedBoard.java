package com.example.leaderboard.service;

import com.example.leaderboard.cache.BoardKey;
import com.example.leaderboard.domain.Leaderboard;
import com.example.leaderboard.domain.SortOrder;

/**
 * A leaderboard definition paired with the concrete period being read or written. Passing this
 * around (instead of three loose strings) means the period is resolved exactly once per request.
 */
public record ResolvedBoard(Leaderboard definition, BoardKey key) {

  public SortOrder sortOrder() {
    return definition.getSortOrder();
  }
}
