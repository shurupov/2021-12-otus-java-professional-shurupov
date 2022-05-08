package ru.otus.shurupov.concurrency;

public class Concurrency {

    private int current = 0;

    private int step = 1;

    public synchronized void doIt(boolean update) {

        while (!update) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (update) {
                    updateValue();
                }
                System.out.printf("[%s] %s%n", Thread.currentThread().getName(), current);
                sleep();
                this.notifyAll();
                this.wait();
            } catch (InterruptedException e) {
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
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {

        Concurrency object = new Concurrency();

        new Thread(() -> object.doIt(true), "Thread 1").start();
        new Thread(() -> object.doIt(false), "Thread 2").start();
    }
}
