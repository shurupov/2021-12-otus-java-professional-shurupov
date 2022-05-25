package ru.otus.shurupov.grpc.client;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.NumberGeneratorGrpc;
import ru.otus.protobuf.generated.NumberGeneratorService;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;

public class ObserverProcessExecutor extends ProcessExecutor {

    private static final Logger logger = LoggerFactory.getLogger(ObserverProcessExecutor.class);

    private final NumberGeneratorGrpc.NumberGeneratorStub stub;

    public ObserverProcessExecutor(ExecutorService executorService,
                                   NumberProcessor numberProcessor,
                                   NumberGeneratorGrpc.NumberGeneratorStub stub) {
        super(executorService, numberProcessor);
        this.stub = stub;
    }

    @Override
    protected void request(int from, int to) {
        stub.generate(
            NumberGeneratorService.RunMessage.newBuilder()
                .setFrom(1)
                .setTo(20)
                .build(),
            new NumberMessageStreamObserver()
        );
    }

    @Override
    protected void processReceivedNumbers() {
    }

    private class NumberMessageStreamObserver implements StreamObserver<NumberGeneratorService.NumberMessage> {

        @Override
        public void onNext(NumberGeneratorService.NumberMessage numberMessage) {
            logger.info("Received: {}", numberMessage.getNumber());
        }

        @Override
        public void onError(Throwable t) {
            System.out.println(Arrays.toString(t.getStackTrace()));
        }

        @Override
        public void onCompleted() {
            logger.info("Observe completed");
            latch.countDown();
        }
    }
}
