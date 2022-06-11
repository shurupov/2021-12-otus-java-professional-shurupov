package ru.otus.shurupov.webflux.service.service;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.otus.shurupov.webflux.service.domain.Address;
import ru.otus.shurupov.webflux.service.domain.Client;
import ru.otus.shurupov.webflux.service.domain.Phone;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientService {
    private final WebClient webClient;
    private final ExecutorService executorService;
    private final PhoneService phoneService;
    private final AddressService addressService;

    public ClientService(WebClient.Builder builder, ExecutorService executorService, PhoneService phoneService, AddressService addressService) {
        webClient = builder
            .baseUrl("http://localhost:8181")
            .build();
        this.executorService = executorService;
        this.phoneService = phoneService;
        this.addressService = addressService;
    }

    public Flux<Client> getAll() {

        var allPhones = phoneService.getAll()
            .collect( () -> new ArrayList<Phone>(), ArrayList::add)
            .toFuture();

        var allAddresses = addressService.getAll()
            .collect( () -> new ArrayList<Address>(), ArrayList::add)
            .toFuture();


        return webClient.get()
            .uri("/api/clients")
            .accept(MediaType.APPLICATION_NDJSON)
            .retrieve()
            .bodyToFlux(Client.class)
            .map(client -> {
                log.info("Received client {}", client);
                client.setPhones(
                    allPhones.join()
                        .stream()
                        .filter(phone -> phone.getClientId().equals(client.getId()))
                        .collect(Collectors.toList())
                );
                log.info("Added phones to client {}", client);
                return client;
            })
            .map(client -> {
                client.setAddress(
                    allAddresses.join()
                        .stream()
                        .filter(address -> address.getClientId().equals(client.getId()))
                        .findAny().orElse(null)
                );
                log.info("Added address to client {}", client);
                return client;
            });
    }
}
