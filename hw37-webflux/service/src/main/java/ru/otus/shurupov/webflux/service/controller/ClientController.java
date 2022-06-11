package ru.otus.shurupov.webflux.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.shurupov.webflux.service.domain.Client;
import ru.otus.shurupov.webflux.service.service.ClientService;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping(value = "/api/clients", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Client> getAll() {
        return clientService.getAll();
    }
}
