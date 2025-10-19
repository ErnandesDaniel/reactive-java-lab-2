package org.example.stats.ActiveUsers;

import org.example.models.User.User;
import org.example.models.User.UserGenerator;
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

    @Param({"3000","5000", "50000", "250000"})
    public int userCount;

    @Param({"0", "2"})
    public int delayOperation;

    private List<User> users;

    @Setup
    public void setup() {
        users = UserGenerator.generateUsers(userCount);
    }

    @Benchmark
    public void stream(Blackhole bh) {
        long result = ActiveUsersStatsGenerator.countActiveWithStream(users, delayOperation);
        bh.consume(result);
    }

    @Benchmark
    public void parallelStream(Blackhole bh) {
        long result = ActiveUsersStatsGenerator.countActiveWithParallelStream(users, delayOperation);
        bh.consume(result);
    }

    @Benchmark
    public void customCollector(Blackhole bh) {
        long result = ActiveUsersStatsGenerator.countActiveWithCustomCollector(users, delayOperation);
        bh.consume(result);
    }

    @Benchmark
    public void parallelCustomCollector(Blackhole bh) {
        long result = ActiveUsersStatsGenerator.countActiveWithParallelCustomCollector(users, delayOperation);
        bh.consume(result);
    }

    @Benchmark
    public void customSpliterator(Blackhole bh) {
        long result = ActiveUsersStatsGenerator.countActiveWithCustomSpliterator(users, delayOperation);
        bh.consume(result);
    }

}
