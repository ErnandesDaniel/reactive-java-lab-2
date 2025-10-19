package org.example.stats.ActiveUsers;

import org.example.models.User.User;
import org.example.models.User.UserActivity;
import java.util.List;
import java.util.stream.StreamSupport;

//Статистика по активным пользователям
public class ActiveUsersStatsGenerator {

    // Stream API с последовательным коллектором
    public static long countActiveWithStream(List<User> users, long delayMs) {
        simulateDelay(delayMs);
        return users.stream()
                .mapToLong(u -> u.getUserActivity() == UserActivity.ACTIVE ? 1L : 0L
                )
                .sum();
    }

    // Stream API с параллельным коллектором
    public static long countActiveWithParallelStream(List<User> users, long delayMs) {
        simulateDelay(delayMs);
        return users.parallelStream()
                .mapToLong(u -> u.getUserActivity() == UserActivity.ACTIVE ? 1L : 0L
                )
                .sum();
    }

    // 3.4. Stream API c последовательным кастомным коллектором
    public static long countActiveWithCustomCollector(List<User> users, long delayMs) {
        simulateDelay(delayMs);
        return users.stream()
                .collect(new ActiveUsersUserStatsCollector())
                .activeUsers;
    }


    // 3.4. Stream API с параллельным кастомным коллектором
    public static long countActiveWithParallelCustomCollector(List<User> users, long delayMs) {
        simulateDelay(delayMs);
        return users.parallelStream()
                .collect(new ActiveUsersUserStatsCollector())
                .activeUsers;
    }

    public static long countActiveWithCustomSpliterator(List<User> users, long delayMs) {
        simulateDelay(delayMs);
        return StreamSupport.stream(
                        new ActiveUsersSpliterator(users, 0, users.size()),
                        true
                ).mapToLong(u -> u.getUserActivity() == UserActivity.ACTIVE ? 1L : 0L)
                .sum();
    }

    // Вспомогательный метод для имитации задержки (в миллисекундах)
    private static void simulateDelay(long delayMs) {
        if (delayMs <= 0) return;
        try {
            // Thread.sleep работает с миллисекундами
            Thread.sleep(delayMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // восстанавливаем статус прерывания
        }
    }
}




