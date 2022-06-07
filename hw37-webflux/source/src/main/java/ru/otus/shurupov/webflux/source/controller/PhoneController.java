package ru.otus.shurupov.webflux.source.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.shurupov.webflux.source.domain.Phone;
import ru.otus.shurupov.webflux.source.repository.PhoneRepository;

@RestController
@RequiredArgsConstructor
public class PhoneController {

    private final PhoneRepository phoneRepository;

    @GetMapping(value = "/api/phones", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Phone> getAll() {
        return Flux.fromIterable(phoneRepository.findAll());
    }

    @GetMapping(value = "/api/phones/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Phone> get(@PathVariable Long id) {
        return Mono.fromCallable(() -> phoneRepository.findById(id).orElseThrow());
    }

    @PostMapping(value = "/api/phones", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Phone> add(@RequestBody Mono<Phone> phoneMono) {
        return phoneMono.map(phoneRepository::save);
    }

    @PutMapping(value = "/api/phones/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Phone> update(@PathVariable Long id, @RequestBody Mono<Phone> phoneMono) {
        return phoneMono
            .map(phone -> phone.toBuilder().id(id).build())
            .map(phoneRepository::save);
    }

    @DeleteMapping(value = "/api/phones/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Void> remove(@PathVariable Long id) {
        return Mono.fromCallable(() -> {
            phoneRepository.deleteById(id);
            return null;
        });
    }
}
