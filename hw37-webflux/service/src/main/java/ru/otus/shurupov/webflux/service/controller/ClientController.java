package ru.otus.shurupov.webflux.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.shurupov.webflux.service.domain.Client;
import ru.otus.shurupov.webflux.service.domain.ClientDTO;
import ru.otus.shurupov.webflux.service.service.ClientService;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping(value = "/api/clients", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ClientDTO> getAll() {
        return clientService.getAll();
    }

    @GetMapping(value = "/api/clients/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<ClientDTO> get(@PathVariable Long id) {
        return clientService.get(id);
    }

    @PostMapping(value = "/api/clients", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<ClientDTO> get(@RequestBody ClientDTO clientDTO) {
        return clientService.add(clientDTO);
    }
}
