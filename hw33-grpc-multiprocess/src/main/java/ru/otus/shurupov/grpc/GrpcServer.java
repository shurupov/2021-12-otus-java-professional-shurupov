package ru.otus.shurupov.grpc;

import io.grpc.ServerBuilder;
import ru.otus.shurupov.grpc.server.NumberGeneratorGrpcImpl;
import ru.otus.shurupov.grpc.server.NumberGeneratorService;

import java.io.IOException;

public class GrpcServer {

    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {

        NumberGeneratorService service = new NumberGeneratorService();

        NumberGeneratorGrpcImpl grpcService = new NumberGeneratorGrpcImpl(service);

        var server = ServerBuilder
            .forPort(SERVER_PORT)
            .addService(grpcService)
            .build();

        server.start();
        System.out.println("Server is waiting for client connections...");
        server.awaitTermination();
    }
}
