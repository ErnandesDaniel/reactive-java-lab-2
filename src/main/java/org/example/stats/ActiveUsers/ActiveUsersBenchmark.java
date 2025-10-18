package org.example.stats.ActiveUsers;

import org.example.models.User.User;
import org.example.models.User.UserGenerator;
import org.example.stats.ActiveUsers.ActiveUsersStatsGenerator;
import org.openjdk.jmh.annotations.*;
        import org.openjdk.jmh.infra.Blackhole;

import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
public class ActiveUsersBenchmark {

    @Param({"5000", "50000", "250000"})
    public int userCount;

    @Param({"0", "1"}) // 0 = без задержки, 1 = 1 мс на пользователя (имитация БД)
    public int delayPerUserMs;

    private List<User> users;
    private long totalDelayMs;

    @Setup
    public void setup() {
        users = UserGenerator.generateUsers(userCount);
        totalDelayMs = (long) userCount * delayPerUserMs;
    }

    private void simulateDbDelay() {
        if (totalDelayMs > 0) {
            try {
                Thread.sleep(totalDelayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Benchmark
    public void stream(Blackhole bh) {
        simulateDbDelay();
        long result = ActiveUsersStatsGenerator.countActiveWithStream(users);
        bh.consume(result);
    }

    @Benchmark
    public void parallelStream(Blackhole bh) {
        simulateDbDelay();
        long result = ActiveUsersStatsGenerator.countActiveWithParallelStream(users);
        bh.consume(result);
    }

    // Добавьте, если используете кастомный коллектор
    @Benchmark
    public void parallelCustom(Blackhole bh) {
        simulateDbDelay();
        long result = ActiveUsersStatsGenerator.countActiveWithParallelCustomCollector(users);
        bh.consume(result);
    }
}
