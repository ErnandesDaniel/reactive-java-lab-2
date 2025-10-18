package org.example.stats.ActiveUsers;

import org.example.models.User.User;
import org.example.models.User.UserActivity;
import java.util.List;

//Статистика по активным пользователям
public class ActiveUsersStatsGenerator {

    // Stream API с встроенными коллекторами
    public static long countActiveWithStream(List<User> users) {
        return users.stream()
                .mapToLong(u -> u.getUserActivity() == UserActivity.ACTIVE ? 1L : 0L)
                .sum();
    }

    // Stream API с собственным коллектором
    public static long countActiveWithParallelStream(List<User> users) {
        return users.parallelStream()
                .mapToLong(u -> u.getUserActivity() == UserActivity.ACTIVE ? 1L : 0L)
                .sum();
    }


    // 3.4. Параллельный Stream с кастомным коллектором
    public static long countActiveWithParallelCustomCollector(List<User> users) {
        return users.parallelStream()
                .collect(new ActiveUsersUserStatsCollector())
                .activeUsers;
    }
}




