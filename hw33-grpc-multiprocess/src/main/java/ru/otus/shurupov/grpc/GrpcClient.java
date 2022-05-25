package ru.otus.shurupov.grpc;

import io.grpc.ManagedChannelBuilder;
import ru.otus.protobuf.generated.NumberGeneratorGrpc;
import ru.otus.shurupov.grpc.client.IterationProcessExecutor;
import ru.otus.shurupov.grpc.client.NumberProcessor;
import ru.otus.shurupov.grpc.client.ObserverProcessExecutor;
import ru.otus.shurupov.grpc.client.ProcessExecutor;

import java.util.concurrent.Executors;

public class GrpcClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws InterruptedException {

        var pool = Executors.newSingleThreadExecutor();
        var numberProcessor = new NumberProcessor(0, 50);

        var channel = ManagedChannelBuilder
            .forAddress(SERVER_HOST, SERVER_PORT)
            .usePlaintext()
            .build();

        var blockingStub = NumberGeneratorGrpc.newBlockingStub(channel);
        ProcessExecutor processExecutor = new IterationProcessExecutor(pool, numberProcessor, blockingStub);

//        var streamStub = NumberGeneratorGrpc.newStub(channel);
//        ProcessExecutor processExecutor = new ObserverProcessExecutor(pool, numberProcessor, streamStub);

        processExecutor.process(1, 20);

        channel.shutdown();
        pool.shutdown();
    }
}
