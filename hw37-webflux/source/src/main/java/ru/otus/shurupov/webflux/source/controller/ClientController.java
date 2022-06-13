package ru.otus.shurupov.webflux.source.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.shurupov.webflux.source.domain.Client;
import ru.otus.shurupov.webflux.source.service.ClientService;

@RestController
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping(value = "/api/clients", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Client> getAll() {
        return clientService.getAll();
    }

    @GetMapping(value = "/api/clients/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Client> get(@PathVariable Long id) {
        return clientService.get(id);
    }

    @PostMapping(value = "/api/clients", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Client> add(@RequestBody Client client) {
        return clientService.add(client);
    }

    @PutMapping(value = "/api/clients/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Client> update(@PathVariable Long id, @RequestBody Client client) {
        return clientService.update(id, client);
    }

    @DeleteMapping(value = "/api/clients/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Void> remove(@PathVariable Long id) {
        return clientService.remove(id);
    }
}
