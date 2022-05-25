package ru.otus.shurupov.grpc;

import io.grpc.ManagedChannelBuilder;
import ru.otus.protobuf.generated.NumberGeneratorGrpc;
import ru.otus.protobuf.generated.NumberGeneratorService.*;

import java.util.Iterator;

public class GrpcClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    public static void main(String[] args) {

        var channel = ManagedChannelBuilder
            .forAddress(SERVER_HOST, SERVER_PORT)
            .usePlaintext()
            .build();

        var stub = NumberGeneratorGrpc.newBlockingStub(channel);

        Iterator<NumberMessage> numbers = stub.generate(
            RunMessage.newBuilder()
                .setFrom(1)
                .setTo(30)
                .build()
        );

        while (numbers.hasNext()) {
            NumberMessage numberMessage = numbers.next();
            System.out.println("Received: " + numberMessage.getNumber());
        }
    }
}
