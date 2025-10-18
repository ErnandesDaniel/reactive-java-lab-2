package org.example.stats.DecksPerUser;

import org.example.models.User.User;
import org.example.models.User.UserActivity;
import org.example.models.User.UserGenerator;
import org.example.stats.Common.CommonStatsResult;
import org.example.stats.Common.CommonUserStatsCollector;

import java.util.List;
import java.util.stream.Collectors;

public class DecksPerUserStatsGenerator {

    // 3.1. Итерационный цикл
    public static DecksPerUserStatsResult calculateWithLoop(int userCount) {
        List<User> users = UserGenerator.generateUsers(userCount);

        long totalDecks = 0;


        long start = System.nanoTime();

        for (User user : users) {
            totalDecks += user.getDecks().size();
        }

        long end = System.nanoTime();
        double avgDecksPerUser = userCount > 0 ? (double) totalDecks / userCount : 0.0;
        return new DecksPerUserStatsResult(avgDecksPerUser, end - start);
    }

    // 3.2. Stream API с встроенными коллекторами
    public static DecksPerUserStatsResult calculateWithBuiltInCollectors(int userCount) {

        List<User> users = UserGenerator.generateUsers(userCount);

        long start = System.nanoTime();

        var totalDecks = users.stream().collect(Collectors.summingLong(u -> u.getDecks().size()));

        long end = System.nanoTime();

        double avgDecksPerUser = userCount > 0 ? (double) totalDecks / userCount : 0.0;

        return new DecksPerUserStatsResult(avgDecksPerUser, end - start);
    }

    // 3.3. Stream API с собственным коллектором
    public static DecksPerUserStatsResult calculateWithCustomCollector(int userCount) {
        List<User> users = UserGenerator.generateUsers(userCount);

        long start = System.nanoTime();
        CommonStatsResult result = users.stream()
                .collect(new CommonUserStatsCollector());
        long end = System.nanoTime();

        return new DecksPerUserStatsResult(result.avgDecksPerUser, end - start);
    }
}




