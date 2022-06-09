package ru.otus.shurupov.webflux.source.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.shurupov.webflux.source.domain.Address;
import ru.otus.shurupov.webflux.source.repository.AddressRepository;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final ExecutorService executorService;

    public Flux<Address> getAll() {
        var future = CompletableFuture
            .supplyAsync(addressRepository::findAll, executorService);
        return Mono.fromFuture(future).flatMapMany(Flux::fromIterable);
    }

    public Mono<Address> get(Long id) {
        var future = CompletableFuture
            .supplyAsync(() -> addressRepository.findById(id).orElseThrow(), executorService);
        return Mono.fromFuture(future);
    }

    public Mono<Address> add(Mono<Address> addressMono) {

        addressMono.map()

        var future = addressMono.toFuture();
        addressMono.

        return addressMono.map(addressRepository::save);
    }
}
