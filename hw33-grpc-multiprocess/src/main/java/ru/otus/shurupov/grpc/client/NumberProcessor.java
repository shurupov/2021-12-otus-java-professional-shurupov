package ru.otus.shurupov.grpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;
import ru.otus.shurupov.grpc.utils.Sleeper;

@RequiredArgsConstructor
public class NumberProcessor {

    private static final Logger logger = LoggerFactory.getLogger(NumberProcessor.class);

    private int received;

    private final int from;
    private final int to;

    public synchronized void setReceived(int received) {
        this.received = received;
        logger.info("received: {}", received);
    }

    public void process() {
        int number = 0;
        for (int i = from; i < to; i++) {
            synchronized (this) {
                number += received + 1;
            }
            logger.info("iteration: {}, received: {}, value: {}", i, received, number);
            Sleeper.sleep(1);
        }
    }
}
