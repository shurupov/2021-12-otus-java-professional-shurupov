package ru.otus.shurupov.webflux.service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.shurupov.webflux.service.domain.Address;
import ru.otus.shurupov.webflux.service.domain.Phone;

import java.util.Map;
import java.util.concurrent.ExecutorService;

@Service
@Slf4j
public class AddressService {
    private final WebClient webClient;

    public AddressService(WebClient.Builder builder) {
        webClient = builder
            .baseUrl("http://localhost:8181")
            .build();
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

    public Mono<Address> getFilteredByClientId(Long clientId) {
        log.info("Requested address by clientId");
        return webClient.get()
            .uri("/api/addresses?clientId={clientId}", Map.of("clientId", clientId))
            .accept(MediaType.APPLICATION_NDJSON)
            .retrieve()
            .bodyToMono(Address.class)
            .doOnNext(address -> log.info("Received address {}", address));
    }

    public Mono<Address> add(Address address) {
        log.info("Requested address creation");
        return webClient.post()
            .uri("/api/addresses")
            .bodyValue(address)
            .accept(MediaType.APPLICATION_NDJSON)
            .retrieve()
            .bodyToMono(Address.class)
            .doOnNext(created -> log.info("Received created address {}", created));
    }
}
