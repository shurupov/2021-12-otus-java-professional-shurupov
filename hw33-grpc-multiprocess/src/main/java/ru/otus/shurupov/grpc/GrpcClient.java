package ru.otus.shurupov.grpc;

import io.grpc.ManagedChannelBuilder;
import ru.otus.protobuf.generated.NumberGeneratorGrpc;
import ru.otus.protobuf.generated.NumberGeneratorService.*;
import ru.otus.shurupov.grpc.client.NumberProcessor;
import ru.otus.shurupov.grpc.client.ProcessExecutor;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

public class GrpcClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws InterruptedException {

        var pool = Executors.newSingleThreadExecutor();

        var channel = ManagedChannelBuilder
            .forAddress(SERVER_HOST, SERVER_PORT)
            .usePlaintext()
            .build();

        var stub = NumberGeneratorGrpc.newBlockingStub(channel);

        var latch = new CountDownLatch(2);

        var numberProcessor = new NumberProcessor();

        ProcessExecutor processExecutor = new ProcessExecutor(pool, numberProcessor, stub);

        processExecutor.process(1, 10);

        /*Iterator<NumberMessage> numbers = stub.generate(
            RunMessage.newBuilder()
                .setFrom(1)
                .setTo(20)
                .build()
        );

        pool.submit(() -> numberProcessor.process(latch));

        while (numbers.hasNext()) {
            NumberMessage numberMessage = numbers.next();
            numberProcessor.setReceived(numberMessage.getNumber());
        }
        latch.countDown();*/

        /*var latch2 = new CountDownLatch(1);

        var stub2 = NumberGeneratorGrpc.newStub(channel);

        stub2.generate(
            RunMessage.newBuilder()
                .setFrom(1)
                .setTo(20)
                .build(),
            new StreamObserver<NumberMessage>() {
                @Override
                public void onNext(NumberMessage numberMessage) {
                    System.out.println("Received from server in observer: " + numberMessage.getNumber());
                }

                @Override
                public void onError(Throwable t) {
                    System.out.println(Arrays.toString(t.getStackTrace()));
                }

                @Override
                public void onCompleted() {
                    System.out.println("Completed");
                    latch2.countDown();
                }
            }
        );

        latch2.await();*/

        channel.shutdown();
        pool.shutdown();
    }
}
