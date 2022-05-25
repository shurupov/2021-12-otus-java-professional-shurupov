package ru.otus.shurupov.grpc.client;

import ru.otus.protobuf.generated.NumberGeneratorGrpc;
import ru.otus.protobuf.generated.NumberGeneratorService;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;

public class IterationProcessExecutor extends ProcessExecutor {
    private final NumberGeneratorGrpc.NumberGeneratorBlockingStub stub;

    private Iterator<NumberGeneratorService.NumberMessage> numbers;

    public IterationProcessExecutor(ExecutorService executorService,
                                    NumberProcessor numberProcessor,
                                    NumberGeneratorGrpc.NumberGeneratorBlockingStub stub) {
        super(executorService, numberProcessor);
        this.stub = stub;
    }

    protected void request(int from, int to) {
        numbers = stub.generate(
            NumberGeneratorService.RunMessage.newBuilder()
                .setFrom(from)
                .setTo(to)
                .build()
        );
    }

    protected void processReceivedNumbers() {
        while (numbers.hasNext()) {
            NumberGeneratorService.NumberMessage numberMessage = numbers.next();
            numberProcessor.setReceived(numberMessage.getNumber());
        }
    }
}
