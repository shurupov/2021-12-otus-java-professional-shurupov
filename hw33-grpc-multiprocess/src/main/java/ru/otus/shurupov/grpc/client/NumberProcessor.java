package ru.otus.shurupov.grpc.client;

import lombok.RequiredArgsConstructor;
import ru.otus.shurupov.grpc.utils.Sleeper;

@RequiredArgsConstructor
public class NumberProcessor {

    private int received;

    private final int from;
    private final int to;

    public synchronized void setReceived(int received) {
        this.received = received;
        System.out.println("received: " + received);
    }

    public void process(Runnable waitAndComplete) {
        int number = 0;
        for (int i = from; i < to; i++) {
            number += received + 1;
            System.out.println(i + ": value: " + number);
            Sleeper.sleep(1);
        }
        waitAndComplete.run();
    }
}
