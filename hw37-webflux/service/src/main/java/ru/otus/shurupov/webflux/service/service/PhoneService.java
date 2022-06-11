package ru.otus.shurupov.webflux.service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.otus.shurupov.webflux.service.domain.Phone;

import java.util.Map;
import java.util.concurrent.ExecutorService;

@Service
@Slf4j
public class PhoneService {
    private final WebClient webClient;
    private final ExecutorService executorService;

    public PhoneService(WebClient.Builder builder, ExecutorService executorService) {
        webClient = builder
            .baseUrl("http://localhost:8181")
            .build();
        this.executorService = executorService;
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
}
