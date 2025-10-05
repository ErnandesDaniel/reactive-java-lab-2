package org.example.stats;

public class StatsResult {
    public final long totalDecks;
    public final double avgDecksPerUser;
    public final long activeUsers;
    public final long inactiveUsers;
    public final long executionTimeNanos;

    public StatsResult(long totalDecks, double avgDecksPerUser, long activeUsers, long inactiveUsers, long executionTimeNanos) {
        this.totalDecks = totalDecks;
        this.avgDecksPerUser = avgDecksPerUser;
        this.activeUsers = activeUsers;
        this.inactiveUsers = inactiveUsers;
        this.executionTimeNanos = executionTimeNanos;
    }

    @Override
    public String toString() {
        return String.format(
                "Всего колод (у всех пользователей): %d | Среднее число колод на пользователя: %.2f | Активных пользователей: %d | Неактивных пользователей: %d | Время работы метода: %.2f нс",
                totalDecks, avgDecksPerUser, activeUsers, inactiveUsers, executionTimeNanos / 1_000_000.0
        );
    }
}
