package org.example.stats.ActiveUsers;

import org.example.models.User.User;
import org.example.models.User.UserActivity;
import org.example.models.User.UserGenerator;
import org.example.stats.ActiveUsers.ActiveUsersStatsResult;
import org.example.stats.Common.CommonStatsResult;
import org.example.stats.Common.CommonUserStatsCollector;

import java.util.List;
import java.util.stream.Collectors;

public class ActiveUsersStatsGenerator {

    // 3.1. Итерационный цикл
    public static ActiveUsersStatsResult calculateWithLoop(int userCount) {
        List<User> users = UserGenerator.generateUsers(userCount);

        long activeUsers = 0;

        long start = System.nanoTime();

        for (User user : users) {
            if (user.getUserActivity() == UserActivity.ACTIVE) {
                activeUsers++;
            }
        }

        long end = System.nanoTime();

        return new ActiveUsersStatsResult(activeUsers,  end - start);
    }

    // 3.2. Stream API с встроенными коллекторами
    public static ActiveUsersStatsResult calculateWithBuiltInCollectors(int userCount) {

        List<User> users = UserGenerator.generateUsers(userCount);

        long start = System.nanoTime();

        long result = users.stream().collect(Collectors.summingLong(u -> u.getUserActivity() == UserActivity.ACTIVE ? 1L : 0L));

        long end = System.nanoTime();

        return new ActiveUsersStatsResult(result, end - start);
    }

    // 3.3. Stream API с собственным коллектором
    public static ActiveUsersStatsResult calculateWithCustomCollector(int userCount) {
        List<User> users = UserGenerator.generateUsers(userCount);

        long start = System.nanoTime();
        ActiveUsersStatsResult result = users.stream()
                .collect(new org.example.stats.ActiveUsersUserStatsCollector());
        long end = System.nanoTime();

        return new ActiveUsersStatsResult(result.activeUsers, end - start);
    }
}




