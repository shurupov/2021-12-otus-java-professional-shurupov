package ru.otus.shurupov.grpc.client;

import lombok.RequiredArgsConstructor;
import ru.otus.protobuf.generated.NumberGeneratorGrpc;
import ru.otus.protobuf.generated.NumberGeneratorService;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
public class ProcessExecutor {

    private final ExecutorService executorService;
    private final NumberProcessor numberProcessor;
    private final NumberGeneratorGrpc.NumberGeneratorBlockingStub stub;
    private CountDownLatch latch;

    private Iterator<NumberGeneratorService.NumberMessage> numbers;

    protected void request(int from, int to) {
        numbers = stub.generate(
            NumberGeneratorService.RunMessage.newBuilder()
                .setFrom(from)
                .setTo(to)
                .build()
        );
    }

    protected void initLatch() throws InterruptedException {
        latch = new CountDownLatch(2);
    }

    private void mainProcess() {
        executorService.submit(() -> numberProcessor.process(latch));
    }

    private void waitAndComplete() throws InterruptedException {
        latch.countDown();
        if (latch.getCount() > 0) {
            latch.await();
        }
    }

    protected void processReceivedNumbers() {
        while (numbers.hasNext()) {
            NumberGeneratorService.NumberMessage numberMessage = numbers.next();
            numberProcessor.setReceived(numberMessage.getNumber());
        }
    }

    public void process(int from, int to) throws InterruptedException {
        initLatch();

        request(from, to);

        mainProcess();

        processReceivedNumbers();

        waitAndComplete();
    }
}
