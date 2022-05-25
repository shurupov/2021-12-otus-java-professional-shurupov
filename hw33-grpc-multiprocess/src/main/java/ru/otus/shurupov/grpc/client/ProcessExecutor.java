package ru.otus.shurupov.grpc.client;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
public abstract class ProcessExecutor {

    private final ExecutorService executorService;
    protected final NumberProcessor numberProcessor;

    protected CountDownLatch latch;

    abstract protected void request(int from, int to);

    protected void initLatch() {
        latch = new CountDownLatch(2);
    }

    private void mainProcess() {
        executorService.submit(() -> numberProcessor.process(() -> {
            latch.countDown();
            try {
                latch.await();
            } catch (InterruptedException ignored) {
            }
        }));
    }

    private void waitAndComplete() throws InterruptedException {
        latch.countDown();
        latch.await();
    }

    abstract protected void processReceivedNumbers();

    public void process(int from, int to) throws InterruptedException {
        initLatch();

        request(from, to);

        mainProcess();

        processReceivedNumbers();

        waitAndComplete();
    }
}
