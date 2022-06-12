package ru.otus.shurupov.webflux.service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.shurupov.webflux.service.domain.Phone;

import java.util.Map;

@Service
@Slf4j
public class PhoneService {
    private final WebClient webClient;

    public PhoneService(WebClient.Builder builder) {
        webClient = builder
            .baseUrl("http://localhost:8181")
            .build();
    }

    public Flux<Phone> getAll() {
        log.info("Requested all phones");
        return webClient.get()
            .uri("/api/phones")
            .accept(MediaType.APPLICATION_NDJSON)
            .retrieve()
            .bodyToFlux(Phone.class)
            .doOnNext(phone -> log.info("Received phone {}", phone));
    }

    public Flux<Phone> getFilteredByClientId(Long clientId) {
        log.info("Requested all phones");
        return webClient.get()
            .uri("/api/phones?clientId={clientId}", Map.of("clientId", clientId))
            .accept(MediaType.APPLICATION_NDJSON)
            .retrieve()
            .bodyToFlux(Phone.class)
            .doOnNext(phone -> log.info("Received phone {}", phone));
    }

    public Mono<Phone> add(Phone phone) {
        log.info("Requested phones creation");
        return webClient.post()
            .uri("/api/phones")
            .bodyValue(phone)
            .accept(MediaType.APPLICATION_NDJSON)
            .retrieve()
            .bodyToMono(Phone.class)
            .doOnNext(created -> log.info("Received created phone {}", created));
    }

    public Mono<Void> removeByClientId(Long clientId) {
        return webClient.delete()
            .uri("/api/phones?clientId={clientId}", Map.of("clientId", clientId))
            .accept(MediaType.APPLICATION_NDJSON)
            .retrieve()
            .toBodilessEntity()
            .then();
    }
}
