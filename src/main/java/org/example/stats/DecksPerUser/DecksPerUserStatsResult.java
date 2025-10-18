package org.example.stats.DecksPerUser;

public class DecksPerUserStatsResult {

    public final double avgDecksPerUser;
    public final long executionTimeNanos;

    public DecksPerUserStatsResult(double avgDecksPerUser, long executionTimeNanos) {

        this.avgDecksPerUser = avgDecksPerUser;
        this.executionTimeNanos = executionTimeNanos;
    }

    @Override
    public String toString() {
        return String.format(
                "Среднее число колод на пользователя: %.2f | Время работы метода: %.2f мс",
                avgDecksPerUser, executionTimeNanos / 1_000_000.0
        );
    }
}
