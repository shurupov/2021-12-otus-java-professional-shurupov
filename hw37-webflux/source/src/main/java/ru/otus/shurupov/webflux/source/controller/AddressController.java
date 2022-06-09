package ru.otus.shurupov.webflux.source.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.shurupov.webflux.source.domain.Address;
import ru.otus.shurupov.webflux.source.service.AddressService;

@RestController
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping(value = "/api/addresses", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Address> getAll(@RequestParam(required = false) Long clientId) {
        return addressService.getAll(clientId);
    }

    @GetMapping(value = "/api/addresses/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Address> get(@PathVariable Long id) {
        return addressService.get(id);
    }

    @PostMapping(value = "/api/addresses", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Address> add(@RequestBody Address address) {
        return addressService.add(address);
    }

    @PutMapping(value = "/api/addresses/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Address> update(@PathVariable Long id, @RequestBody Address address) {
        return addressService.update(id, address);
    }

    @DeleteMapping(value = "/api/addresses/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Void> remove(@PathVariable Long id) {
        return addressService.remove(id);
    }

    @DeleteMapping(value = "/api/addresses", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Void> removeByClientId(@RequestParam Long clientId) {
        return addressService.removeByClientId(clientId);
    }
}
