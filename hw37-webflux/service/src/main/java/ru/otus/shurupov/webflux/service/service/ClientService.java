package ru.otus.shurupov.webflux.service.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.otus.shurupov.webflux.service.domain.Client;
import ru.otus.shurupov.webflux.service.domain.Phone;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
public class ClientService {
    private final WebClient webClient;
    private final ExecutorService executorService;

    public ClientService(WebClient.Builder builder, ExecutorService executorService) {
        webClient = builder
            .baseUrl("http://localhost:8181")
            .build();
        this.executorService = executorService;
        ;
    }

    public Flux<Client> getAll() {
        return webClient.get()
            .uri("/api/clients")
            .accept(MediaType.APPLICATION_NDJSON)
            .retrieve()
            .bodyToFlux(Client.class)
            /*.map((client -> {
                webClient.get()
                    .uri(String.format("/api/phones?clientId=%s", client.getId()))
                    .accept(MediaType.APPLICATION_NDJSON)
                    .retrieve()
                    .bodyToFlux(Phone.class)
                    .collect( () -> new ArrayList<Phone>(), (l, p) -> l.add(p) )
                    .toFuture()
                    .whenComplete((pl, e) -> client.setPhones(pl));
                return client;
            }))*/;
    }
}
