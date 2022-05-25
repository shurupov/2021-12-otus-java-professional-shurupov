package ru.otus.shurupov.grpc.server;

import java.util.function.Consumer;

public class NumberGeneratorService {

    public void generate(int from, int to, Consumer<Integer> consumer) {
        for (int i = from; i <= to; i++) {

            consumer.accept(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
