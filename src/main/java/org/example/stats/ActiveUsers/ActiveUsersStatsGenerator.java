package org.example.stats.ActiveUsers;

import org.example.models.User.User;
import org.example.models.User.UserActivity;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.StreamSupport;
import java.util.concurrent.locks.LockSupport;

//Статистика по активным пользователям
public class ActiveUsersStatsGenerator {


    // Последовательная обработка (в одном виртуальном потоке)
    public static long countActiveWithOneStream(List<User> users, long delayMicros) {

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            CompletableFuture<Long> result = CompletableFuture.supplyAsync(() -> {
                long count = 0;
                for (User user : users) {
                    simulateIoDelayMicros(delayMicros);
                    if (user.getUserActivity() == UserActivity.ACTIVE) {
                        count++;
                    }
                }
                return count;
            }, executor);
            return result.join();
        }
    }

    // Параллельная обработка (по одному виртуальному потоку на пользователя)
    public static long countActiveWithParallelStreams(List<User> users, long delayMicros) {

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            return users.stream()
                    .map(user -> CompletableFuture.supplyAsync(() -> {
                        simulateIoDelayMicros(delayMicros);
                        return user.getUserActivity() == UserActivity.ACTIVE ? 1L : 0L;
                    }, executor))
                    .mapToLong(CompletableFuture::join)
                    .sum();
        }
    }

//    public static long countActiveWithCustomSpliterator(List<User> users, long delayMicros) {
//        return StreamSupport.stream(
//                        new ActiveUsersSpliterator(users, 0, users.size(), delayMicros),
//                        true
//                ).mapToLong(u -> u.getUserActivity() == UserActivity.ACTIVE ? 1L : 0L)
//                .sum();
//    }

    //Имитация неблокирующей I/O задержки в микросекундах
    private static void simulateIoDelayMicros(long delayMicros) {
        if (delayMicros > 0) {
            // parkNanos корректно работает с виртуальными потоками
            LockSupport.parkNanos(delayMicros * 1_000L);
        }
    }

}




