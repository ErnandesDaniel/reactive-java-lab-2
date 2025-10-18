package org.example.stats.DecksPerUser;

import org.example.models.User.User;
import org.example.models.User.UserActivity;
import org.example.stats.Common.CommonStatsResult;
import org.example.stats.Common.CommonUserStatsCollector;
import org.example.stats.DecksPerUser.DecksPerUserStatsResult;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class DecksPerUserStatsCollector implements Collector<User, DecksPerUserStatsCollector.Accumulator, DecksPerUserStatsResult> {

    public static class Accumulator {
        long totalDecks = 0;
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
            acc.userCount++;
        };
    }

    @Override
    public BinaryOperator<Accumulator> combiner() {
        return (acc1, acc2) -> {
            acc1.totalDecks += acc2.totalDecks;
            acc1.userCount += acc2.userCount;
            return acc1;
        };
    }

    @Override
    public Function<Accumulator, DecksPerUserStatsResult> finisher() {
        return acc -> {
            double avg = acc.userCount > 0 ? (double) acc.totalDecks / acc.userCount : 0.0;
            return new DecksPerUserStatsResult(avg,  0);
        };
    }

    @Override
    public java.util.Set<Characteristics> characteristics() {
        return java.util.Set.of(Characteristics.UNORDERED);
    }
}