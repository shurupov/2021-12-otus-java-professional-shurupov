package ru.otus.shurupov.webflux.source.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.shurupov.webflux.source.domain.Address;
import ru.otus.shurupov.webflux.source.repository.AddressRepository;
import ru.otus.shurupov.webflux.source.service.AddressService;

@RestController
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping(value = "/api/addresses", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Address> getAll() {
        return addressService.getAll();
    }

    @GetMapping(value = "/api/addresses/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Address> get(@PathVariable Long id) {
        return addressService.get(id);
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
