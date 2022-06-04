package ru.otus.shurupov.grpc.server;

import ru.otus.shurupov.grpc.utils.Sleeper;

import java.util.function.Consumer;

public class NumberGeneratorService {

    public void generate(int from, int to, Consumer<Integer> consumer) {
        for (int i = from; i <= to; i++) {
            consumer.accept(i);
            Sleeper.sleep(2);
        }
    }
}
