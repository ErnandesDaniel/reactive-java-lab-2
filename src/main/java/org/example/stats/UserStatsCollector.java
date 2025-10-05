package org.example.stats;

import org.example.models.User.User;
import org.example.models.User.UserActivity;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class UserStatsCollector implements Collector<User, UserStatsCollector.Accumulator, StatsResult> {

    public static class Accumulator {
        long totalDecks = 0;
        long activeUsers = 0;
        long inactiveUsers = 0;
        long userCount = 0;
    }

    @Override
    public Supplier<Accumulator> supplier() {
        return Accumulator::new;
    }

    @Override
    public BiConsumer<Accumulator, User> accumulator() {
        return (acc, user) -> {
            acc.totalDecks += user.getDecks().size();
            if (user.getUserActivity() == UserActivity.ACTIVE) {
                acc.activeUsers++;
            } else if (user.getUserActivity() == UserActivity.INACTIVE) {
                acc.inactiveUsers++;
            }
            acc.userCount++;
        };
    }

    @Override
    public BinaryOperator<Accumulator> combiner() {
        return (acc1, acc2) -> {
            acc1.totalDecks += acc2.totalDecks;
            acc1.activeUsers += acc2.activeUsers;
            acc1.inactiveUsers += acc2.inactiveUsers;
            acc1.userCount += acc2.userCount;
            return acc1;
        };
    }

    @Override
    public Function<Accumulator, StatsResult> finisher() {
        return acc -> {
            double avg = acc.userCount > 0 ? (double) acc.totalDecks / acc.userCount : 0.0;
            return new StatsResult(acc.totalDecks, avg, acc.activeUsers, acc.inactiveUsers, 0);
        };
    }

    @Override
    public java.util.Set<Characteristics> characteristics() {
        return java.util.Set.of(Characteristics.UNORDERED);
    }
}