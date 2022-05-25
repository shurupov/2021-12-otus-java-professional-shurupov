package ru.otus.shurupov.grpc.utils;

public class Sleeper {
    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
        }
    }
}
