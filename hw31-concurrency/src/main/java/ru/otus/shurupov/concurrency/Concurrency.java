package ru.otus.shurupov.concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Concurrency {

    private static final Logger logger = LoggerFactory.getLogger(Concurrency.class);

    private int current = 0;

    private int step = 1;

    private int lastThread = 2;

    public synchronized void doIt(boolean update, int thread) {

        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (lastThread == thread) {
                    this.wait();
                }
                if (update) {
                    updateValue();
                }
                logger.info(Integer.toString(current));
                lastThread = thread;
                sleep();
                this.notifyAll();

            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }
    }

    public void updateValue() {
        current += step;
        if (current == 10) {
            step = -1;
        }
        if (current == 1) {
            step = 1;
        }
    }

    private void sleep() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {

        Concurrency object = new Concurrency();

        new Thread(() -> object.doIt(true, 1), "Thread 1").start();
        new Thread(() -> object.doIt(false,2), "Thread 2").start();
    }
}
