package ru.otus.shurupov.webflux.source.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.shurupov.webflux.source.domain.Client;
import ru.otus.shurupov.webflux.source.repository.ClientRepository;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ExecutorService executorService;

    public Flux<Client> getAll() {
        var future = CompletableFuture
            .supplyAsync(clientRepository::findAll, executorService);
        return Mono.fromFuture(future).flatMapMany(Flux::fromIterable);
    }

    public Mono<Client> get(Long id) {
        var future = CompletableFuture
            .supplyAsync(() -> clientRepository.findById(id).orElseThrow(), executorService);
        return Mono.fromFuture(future);
    }

    public Mono<Client> add(Client client) {
        var future = CompletableFuture
            .supplyAsync(() -> clientRepository.save(client), executorService);
        return Mono.fromFuture(future);
    }

    public Mono<Client> update(Long id, Client client) {
        Client updated = client.toBuilder()
            .id(id)
            .build();
        var future = CompletableFuture
            .supplyAsync(() -> clientRepository.save(updated), executorService);
        return Mono.fromFuture(future);
    }

    public Mono<Void> remove(Long id) {
        CompletableFuture<Void> future = CompletableFuture
            .supplyAsync(() -> {
                clientRepository.deleteById(id);
                return null;
            }, executorService);
        return Mono.fromFuture(future);
    }
}
