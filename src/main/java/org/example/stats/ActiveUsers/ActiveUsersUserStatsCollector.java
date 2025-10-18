package org.example.stats.ActiveUsers;

import org.example.models.User.User;
import org.example.models.User.UserActivity;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class ActiveUsersUserStatsCollector implements Collector<User, ActiveUsersUserStatsCollector.Accumulator, ActiveUsersStatsResult> {

    public static class Accumulator {
        long activeUsers = 0;
    }

    @Override
    public Supplier<Accumulator> supplier() {
        return Accumulator::new;
    }

    @Override
    public BiConsumer<Accumulator, User> accumulator() {
        return (acc, user) -> {
            if (user.getUserActivity() == UserActivity.ACTIVE) {
                acc.activeUsers++;
            }
        };
    }

    @Override
    public BinaryOperator<Accumulator> combiner() {
        return (acc1, acc2) -> {
            acc1.activeUsers += acc2.activeUsers;
            return acc1;
        };
    }

    @Override
    public Function<Accumulator, ActiveUsersStatsResult> finisher() {
        return acc -> {
            return new ActiveUsersStatsResult(acc.activeUsers, 0);
        };
    }

    @Override
    public java.util.Set<Characteristics> characteristics() {
        return java.util.Set.of(Characteristics.UNORDERED);
    }
}