package ru.otus.shurupov.webflux.source.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.shurupov.webflux.source.domain.Address;
import ru.otus.shurupov.webflux.source.repository.AddressRepository;

@RestController
@RequiredArgsConstructor
public class AddressController {

    private final AddressRepository addressRepository;

    @GetMapping(value = "/api/addresses", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Address> getAll() {
        return Flux.fromIterable(addressRepository.findAll());
    }

    @GetMapping(value = "/api/addresses/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Address> get(@PathVariable Long id) {
        return Mono.fromCallable(() -> addressRepository.findById(id).orElseThrow());
    }

    @PostMapping(value = "/api/addresses", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Address> add(@RequestBody Mono<Address> addressMono) {
        return addressMono.map(addressRepository::save);
    }

    @PutMapping(value = "/api/addresses/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Address> update(@PathVariable Long id, @RequestBody Mono<Address> addressMono) {
        return addressMono
            .map(address -> address.toBuilder().id(id).build())
            .map(addressRepository::save);
    }

    @DeleteMapping(value = "/api/addresses/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Void> remove(@PathVariable Long id) {
        return Mono.fromCallable(() -> {
            addressRepository.deleteById(id);
            return null;
        });
    }
}
