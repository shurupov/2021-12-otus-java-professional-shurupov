package ru.otus.shurupov.webflux.source.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.shurupov.webflux.source.domain.Address;
import ru.otus.shurupov.webflux.source.repository.AddressRepository;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final ExecutorService executorService;

    public Flux<Address> getAll(Long clientId) {
        CompletableFuture<Iterable<Address>> future;
        if (clientId != null) {
            future = CompletableFuture
                .supplyAsync(
                    () -> addressRepository.findTopByClientId(clientId).stream().toList(),
                    executorService
                );
        } else {
            future = CompletableFuture
                .supplyAsync(addressRepository::findAll, executorService);
        }

        return Mono.fromFuture(future)
            .flatMapMany(Flux::fromIterable)
            .delayElements(Duration.ofSeconds(1));
    }

    public Mono<Address> get(Long id) {
        var future = CompletableFuture
            .supplyAsync(() -> addressRepository.findById(id).orElseThrow(), executorService);
        return Mono.fromFuture(future)
            .delayElement(Duration.ofSeconds(1));
    }

    public Mono<Address> getByClientId(Long clientId) {
        var future = CompletableFuture
            .supplyAsync(() -> addressRepository.findTopByClientId(clientId).orElse(null), executorService);
        return Mono.fromFuture(future)
            .delayElement(Duration.ofSeconds(1));
    }

    public Mono<Address> add(Address address) {
        var future = CompletableFuture
            .supplyAsync(() -> addressRepository.save(address), executorService);
        return Mono.fromFuture(future)
            .delayElement(Duration.ofSeconds(1));
    }

    public Mono<Address> update(Long id, Address address) {
        Address updated = address.toBuilder()
            .id(id)
            .build();
        var future = CompletableFuture
            .supplyAsync(() -> addressRepository.save(updated), executorService);
        return Mono.fromFuture(future)
            .delayElement(Duration.ofSeconds(1));
    }

    public Mono<Void> remove(Long id) {
        CompletableFuture<Void> future = CompletableFuture
            .supplyAsync(() -> {
                addressRepository.deleteById(id);
                return null;
            }, executorService);
        return Mono.fromFuture(future)
            .delayElement(Duration.ofSeconds(1));
    }

    public Mono<Void> removeByClientId(Long clientId) {
        CompletableFuture<Void> future = CompletableFuture
            .supplyAsync(() -> {
                addressRepository.deleteAllByClientId(clientId);
                return null;
            }, executorService);
        return Mono.fromFuture(future)
            .delayElement(Duration.ofSeconds(1));
    }
}
