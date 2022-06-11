package ru.otus.shurupov.webflux.service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.otus.shurupov.webflux.service.domain.Address;
import ru.otus.shurupov.webflux.service.domain.Phone;

import java.util.concurrent.ExecutorService;

@Service
@Slf4j
public class AddressService {
    private final WebClient webClient;
    private final ExecutorService executorService;

    public AddressService(WebClient.Builder builder, ExecutorService executorService) {
        webClient = builder
            .baseUrl("http://localhost:8181")
            .build();
        this.executorService = executorService;
    }

    public Flux<Address> getAll() {
        log.info("Requested all addresses");
        return webClient.get()
            .uri("/api/addresses")
            .accept(MediaType.APPLICATION_NDJSON)
            .retrieve()
            .bodyToFlux(Address.class)
            .doOnNext(address -> log.info("Received address {}", address));
    }
}
