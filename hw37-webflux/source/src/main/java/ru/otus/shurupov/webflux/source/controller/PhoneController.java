package ru.otus.shurupov.webflux.source.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.shurupov.webflux.source.domain.Phone;
import ru.otus.shurupov.webflux.source.service.PhoneService;

@RestController
@RequiredArgsConstructor
public class PhoneController {
    private final PhoneService phoneService;

    @GetMapping(value = "/api/phones", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Phone> getAll(@RequestParam(required = false) Long clientId) {
        return phoneService.getAll(clientId);
    }

    @GetMapping(value = "/api/phones/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Phone> get(@PathVariable Long id) {
        return phoneService.get(id);
    }

    @PostMapping(value = "/api/phones", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Phone> add(@RequestBody Phone phone) {
        return phoneService.add(phone);
    }

    @PutMapping(value = "/api/phones/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Phone> update(@PathVariable Long id, @RequestBody Phone phone) {
        return phoneService.update(id, phone);
    }

    @DeleteMapping(value = "/api/phones/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Void> remove(@PathVariable Long id) {
        return phoneService.remove(id);
    }

    @DeleteMapping(value = "/api/phones", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Void> removeByClientId(@RequestParam Long clientId) {
        return phoneService.removeByClientId(clientId);
    }
}
