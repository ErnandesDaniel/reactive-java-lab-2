package org.example.stats.Common;

import org.example.models.User.User;
import org.example.models.User.UserActivity;
import org.example.models.User.UserGenerator;

import java.util.List;
import java.util.stream.Collectors;

public class CommonStatsGenerator {

    // 3.1. Итерационный цикл
    public static CommonStatsResult calculateWithLoop(int userCount) {
        List<User> users = UserGenerator.generateUsers(userCount);

        long totalDecks = 0;
        long activeUsers = 0;
        long inactiveUsers = 0;

        long start = System.nanoTime();

        for (User user : users) {
            totalDecks += user.getDecks().size();
            if (user.getUserActivity() == UserActivity.ACTIVE) {
                activeUsers++;
            } else if (user.getUserActivity() == UserActivity.INACTIVE) {
                inactiveUsers++;
            }
        }

        long end = System.nanoTime();

        double avgDecksPerUser = userCount > 0 ? (double) totalDecks / userCount : 0.0;

        return new CommonStatsResult(totalDecks, avgDecksPerUser, activeUsers, inactiveUsers, end - start);
    }

    // 3.2. Stream API с встроенными коллекторами
    public static CommonStatsResult calculateWithBuiltInCollectors(int userCount) {

        List<User> users = UserGenerator.generateUsers(userCount);

        long start = System.nanoTime();

        var result = users.stream().collect(
                Collectors.teeing(
                        // Первый коллектор: сумма колод
                        Collectors.summingLong(u -> u.getDecks().size()),
                        // Второй коллектор: группировка по активности
                        Collectors.groupingBy(User::getUserActivity, Collectors.counting()),
                        // Комбинатор: объединяет результаты
                        (totalDecks, activityCounts) -> {
                            long active = activityCounts.getOrDefault(UserActivity.ACTIVE, 0L);
                            long inactive = activityCounts.getOrDefault(UserActivity.INACTIVE, 0L);
                            double avg = userCount > 0 ? (double) totalDecks / userCount : 0.0;
                            return new CommonStatsResult(totalDecks, avg, active, inactive, 0);
                        }
                )
        );

        long end = System.nanoTime();

        return new CommonStatsResult(
                result.totalDecks,
                result.avgDecksPerUser,
                result.activeUsers,
                result.inactiveUsers,
                end - start
        );
    }

    // 3.3. Stream API с собственным коллектором
    public static CommonStatsResult calculateWithCustomCollector(int userCount) {
        List<User> users = UserGenerator.generateUsers(userCount);

        long start = System.nanoTime();
        CommonStatsResult result = users.stream()
                .collect(new CommonUserStatsCollector());
        long end = System.nanoTime();

        return new CommonStatsResult(
                result.totalDecks,
                result.avgDecksPerUser,
                result.activeUsers,
                result.inactiveUsers,
                end - start
        );
    }
}




