package org.example;
import org.example.stats.StatsGenerator;

public class Main {
    public static void main(String[] args) {
        int[] sizes = {5_000, 50_000, 250_000};

        for (int n : sizes) {
            System.out.println("\n=== Коллекция из " + n + " пользователей ===");

            var r1 = StatsGenerator.calculateWithLoop(n);
            System.out.println("1. Цикл:        " + r1);

            var r2 = StatsGenerator.calculateWithBuiltInCollectors(n);
            System.out.println("2. Stream API:  " + r2);

            var r3 = StatsGenerator.calculateWithCustomCollector(n);
            System.out.println("3. Свой коллектор: " + r3);
        }
    }
}