package ru.otus.shurupov.webflux.source.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.shurupov.webflux.source.domain.Phone;
import ru.otus.shurupov.webflux.source.repository.PhoneRepository;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
@RequiredArgsConstructor
public class PhoneService {
    private final PhoneRepository phoneRepository;
    private final ExecutorService executorService;

    public Flux<Phone> getAll(Long clientId) {
        CompletableFuture<Iterable<Phone>> future;
        if (clientId != null) {
            future = CompletableFuture
                .supplyAsync(() -> phoneRepository.findByClientId(clientId), executorService);
        } else {
            future = CompletableFuture
                .supplyAsync(phoneRepository::findAll, executorService);
        }

        return Mono.fromFuture(future)
            .flatMapMany(Flux::fromIterable)
            .delayElements(Duration.ofSeconds(1));
    }

    public Mono<Phone> get(Long id) {
        var future = CompletableFuture
            .supplyAsync(() -> phoneRepository.findById(id).orElseThrow(), executorService);
        return Mono.fromFuture(future)
            .delayElement(Duration.ofSeconds(1));
    }

    public Mono<Phone> add(Phone phone) {
        var future = CompletableFuture
            .supplyAsync(() -> phoneRepository.save(phone), executorService);
        return Mono.fromFuture(future)
            .delayElement(Duration.ofSeconds(1));
    }

    public Mono<Phone> update(Long id, Phone phone) {
        Phone updated = phone.toBuilder()
            .id(id)
            .build();
        var future = CompletableFuture
            .supplyAsync(() -> phoneRepository.save(updated), executorService);
        return Mono.fromFuture(future)
            .delayElement(Duration.ofSeconds(1));
    }

    public Mono<Void> remove(Long id) {
        CompletableFuture<Void> future = CompletableFuture
            .supplyAsync(() -> {
                phoneRepository.deleteById(id);
                return null;
            }, executorService);
        return Mono.fromFuture(future)
            .delayElement(Duration.ofSeconds(1));
    }

    public Mono<Void> removeByClientId(Long clientId) {
        CompletableFuture<Void> future = CompletableFuture
            .supplyAsync(() -> {
                phoneRepository.deleteByClientId(clientId);
                return null;
            }, executorService);
        return Mono.fromFuture(future)
            .delayElement(Duration.ofSeconds(1));
    }
}
