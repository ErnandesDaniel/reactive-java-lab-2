package org.example.stats.ActiveUsers;

public class ActiveUsersStatsResult {
    public final long activeUsers;
    public final long executionTimeNanos;

    public ActiveUsersStatsResult(long activeUsers, long executionTimeNanos) {
        this.activeUsers = activeUsers;
        this.executionTimeNanos = executionTimeNanos;
    }

    @Override
    public String toString() {
        return String.format("Активных пользователей: %d | Время работы метода: %.2f мс", activeUsers, executionTimeNanos / 1_000_000.0);
    }
}
