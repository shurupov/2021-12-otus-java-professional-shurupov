package ru.otus.shurupov.grpc.server;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import ru.otus.protobuf.generated.NumberGeneratorGrpc;
import ru.otus.protobuf.generated.NumberGeneratorService.*;

@RequiredArgsConstructor
public class NumberGeneratorGrpcImpl extends NumberGeneratorGrpc.NumberGeneratorImplBase {

    private final ru.otus.shurupov.grpc.server.NumberGeneratorService numberGeneratorService;

    @Override
    public void generate(RunMessage request, StreamObserver<NumberMessage> responseObserver) {
        numberGeneratorService.generate(
            request.getFrom(),
            request.getTo(),
            (number) -> responseObserver.onNext(
                NumberMessage.newBuilder()
                    .setNumber(number)
                    .build()
            )
        );
        responseObserver.onCompleted();
    }
}
