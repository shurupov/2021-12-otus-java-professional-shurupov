package ru.otus.shurupov.webflux.source.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.shurupov.webflux.source.domain.Client;
import ru.otus.shurupov.webflux.source.repository.ClientRepository;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientRepository clientRepository;

    @GetMapping(value = "/api/clients", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Client> getAll() {
        return Flux.fromIterable(clientRepository.findAll());
    }

    @GetMapping(value = "/api/clients/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Client> get(@PathVariable Long id) {
        return Mono.fromCallable(() -> clientRepository.findById(id).orElseThrow());
    }

    @PostMapping(value = "/api/clients", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Client> add(@RequestBody Mono<Client> clientMono) {
        return clientMono.map(clientRepository::save);
    }

    @PutMapping(value = "/api/clients/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Client> update(@PathVariable Long id, @RequestBody Mono<Client> clientMono) {
        return clientMono
            .map(client -> client.toBuilder().id(id).build())
            .map(clientRepository::save);
    }

    @DeleteMapping(value = "/api/clients/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Void> remove(@PathVariable Long id) {
        return Mono.fromCallable(() -> {
            clientRepository.deleteById(id);
            return null;
        });
    }
}
