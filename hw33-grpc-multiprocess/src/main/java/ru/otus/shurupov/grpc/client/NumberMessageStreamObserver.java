package ru.otus.shurupov.grpc.client;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.NumberGeneratorService;

import java.util.Arrays;

@RequiredArgsConstructor
public class NumberMessageStreamObserver implements StreamObserver<NumberGeneratorService.NumberMessage> {

    private static final Logger logger = LoggerFactory.getLogger(NumberMessageStreamObserver.class);

    private final NumberProcessor numberProcessor;

    @Override
    public void onNext(NumberGeneratorService.NumberMessage numberMessage) {
        logger.info("Received: {}", numberMessage.getNumber());
        numberProcessor.setReceived(numberMessage.getNumber());
    }

    @Override
    public void onError(Throwable t) {
        System.out.println(Arrays.toString(t.getStackTrace()));
    }

    @Override
    public void onCompleted() {
        logger.info("Observe completed");
    }
}
