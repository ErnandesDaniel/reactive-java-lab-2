package org.example;
import org.example.stats.ActiveUsers.ActiveUsersStatsGenerator;
import org.example.stats.Common.CommonStatsGenerator;
import org.example.stats.DecksPerUser.DecksPerUserStatsGenerator;

public class Main {
    public static void main(String[] args) {
        int[] sizes = {5_000, 50_000, 250_000};

        //int[] sizes = {5_000, 50_000};

        System.out.println("\n CommonStatsGenerator");

        //Общая статистика
        for (int n : sizes) {
            System.out.println("\n=== Коллекция из " + n + " пользователей ===");

            var r1 = CommonStatsGenerator.calculateWithLoop(n);
            System.out.println("1. Цикл:        " + r1);

            var r2 = CommonStatsGenerator.calculateWithBuiltInCollectors(n);
            System.out.println("2. Stream API:  " + r2);

            var r3 = CommonStatsGenerator.calculateWithCustomCollector(n);
            System.out.println("3. Свой коллектор: " + r3);
        }

        System.out.println("\n ActiveUsersStatsGenerator");

        //Статистика по активным пользователям
        for (int n : sizes) {
            System.out.println("\n=== Коллекция из " + n + " пользователей ===");

            var r1 = ActiveUsersStatsGenerator.calculateWithLoop(n);
            System.out.println("1. Цикл:        " + r1);

            var r2 = ActiveUsersStatsGenerator.calculateWithBuiltInCollectors(n);
            System.out.println("2. Stream API:  " + r2);

            var r3 = ActiveUsersStatsGenerator.calculateWithCustomCollector(n);
            System.out.println("3. Свой коллектор: " + r3);
        }


        System.out.println("\n DecksPerUserStatsGenerator");

        for (int n : sizes) {
            System.out.println("\n=== Коллекция из " + n + " пользователей ===");

            var r1 = DecksPerUserStatsGenerator.calculateWithLoop(n);
            System.out.println("1. Цикл:        " + r1);

            var r2 = DecksPerUserStatsGenerator.calculateWithBuiltInCollectors(n);
            System.out.println("2. Stream API:  " + r2);

            var r3 = DecksPerUserStatsGenerator.calculateWithCustomCollector(n);
            System.out.println("3. Свой коллектор: " + r3);
        }
    }
}