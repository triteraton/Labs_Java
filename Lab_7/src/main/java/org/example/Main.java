package org.example;

import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        ThreadGenerator generator = new ThreadGenerator();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите количество потоков: ");
        generator.numThreads = scanner.nextInt();

        System.out.print("Введите действие (+, -, *): ");
        generator.action = scanner.next();

        System.out.print("Введите число а: ");
        generator.startRange = 1;
        generator.endRange = scanner.nextInt();

        generator.execute();

        List<Integer> result = generator.getResult();
        System.out.println(result);
    }
}