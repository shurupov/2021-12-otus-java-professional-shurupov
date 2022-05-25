package ru.otus.shurupov.grpc.client;

import ru.otus.shurupov.grpc.utils.Sleeper;

import java.util.concurrent.CountDownLatch;

public class NumberProcessor {

    private int received;

    public synchronized void setReceived(int received) {
        this.received = received;
        System.out.println("received: " + received);
    }

    public void process(CountDownLatch latch) {
        int number = 0;
        for (int i = 0; i < 20; i++) {
            number += received + 1;
            System.out.println(i + ": value: " + number);
            Sleeper.sleep(1);
        }
        latch.countDown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
