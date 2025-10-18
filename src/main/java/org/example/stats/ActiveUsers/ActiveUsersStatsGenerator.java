package org.example.stats.ActiveUsers;

import org.example.models.User.User;
import org.example.models.User.UserActivity;
import java.util.List;

//Статистика по активным пользователям
public class ActiveUsersStatsGenerator {

    // Stream API с последовательным коллектором
    public static long countActiveWithStream(List<User> users, long delayMs) {
        return users.stream()
                .mapToLong(u -> {
                    simulateDelay(delayMs);
                    return u.getUserActivity() == UserActivity.ACTIVE ? 1L : 0L;
                })
                .sum();
    }

    // Stream API с параллельным коллектором
    public static long countActiveWithParallelStream(List<User> users, long delayMs) {
        return users.parallelStream()
                .mapToLong(u -> {
                    simulateDelay(delayMs);
                    return u.getUserActivity() == UserActivity.ACTIVE ? 1L : 0L;
                })
                .sum();
    }



    // 3.4. Stream API c последовательным кастомным коллектором
    public static long countActiveWithCustomCollector(List<User> users, long delayMs) {
        return users.stream()
                .peek(u -> simulateDelay(delayMs))
                .collect(new ActiveUsersUserStatsCollector())
                .activeUsers;
    }


    // 3.4. Stream API с параллельным кастомным коллектором
    public static long countActiveWithParallelCustomCollector(List<User> users, long delayMs) {
        return users.parallelStream()
                .peek(u -> simulateDelay(delayMs))
                .collect(new ActiveUsersUserStatsCollector())
                .activeUsers;
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




