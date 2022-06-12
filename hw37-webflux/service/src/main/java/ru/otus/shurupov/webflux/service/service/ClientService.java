package ru.otus.shurupov.webflux.service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.shurupov.webflux.service.domain.Address;
import ru.otus.shurupov.webflux.service.domain.Client;
import ru.otus.shurupov.webflux.service.domain.ClientDTO;
import ru.otus.shurupov.webflux.service.domain.Phone;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientService {
    private final WebClient webClient;
    private final PhoneService phoneService;
    private final AddressService addressService;

    public ClientService(WebClient.Builder builder, PhoneService phoneService, AddressService addressService) {
        webClient = builder
            .baseUrl("http://localhost:8181")
            .build();
        this.phoneService = phoneService;
        this.addressService = addressService;
    }

    public Flux<ClientDTO> getAll() {

        var allPhones = phoneService.getAll()
            .collect( () -> (List<Phone>) new ArrayList<Phone>(), List::add)
            .toFuture();

        var allAddresses = addressService.getAll()
            .collect( () -> (List<Address>) new ArrayList<Address>(), List::add)
            .toFuture();

        log.info("Requested all clients");
        return webClient.get()
            .uri("/api/clients")
            .accept(MediaType.APPLICATION_NDJSON)
            .retrieve()
            .bodyToFlux(Client.class)
            .doOnNext(client -> log.info("Received client {}", client))
            .map(client -> enrichWithPhones(client, allPhones))
            .map(client -> enrichWithAddress(client, allAddresses))
            .map(this::mapToDTO)
            .doOnNext(client -> log.info("Mapped client {}", client));
    }

    public Mono<ClientDTO> get(Long id) {

        var phones = phoneService.getFilteredByClientId(id)
            .collect( () -> (List<Phone>) new ArrayList<Phone>(), List::add)
            .toFuture();

        var address = addressService.getFilteredByClientId(id).toFuture();

        return webClient.get()
            .uri("/api/clients/{id}", id)
            .accept(MediaType.APPLICATION_NDJSON)
            .retrieve()
            .bodyToMono(Client.class)
            .doOnNext(client -> log.info("Received client {}", client))
            .map(client -> enrichWithPhones(client, phones))
            .map(client -> enrichWithAddress(
                client,
                address.join()
            ))
            .map(this::mapToDTO)
            .doOnNext(client -> log.info("Mapped client {}", client));
    }

    public Mono<ClientDTO> add(ClientDTO clientDTO) {
        var savedClient = webClient.post()
            .uri("/api/clients")
            .bodyValue(
                Client.builder()
                    .name(clientDTO.getName())
                    .build()
            )
            .accept(MediaType.APPLICATION_NDJSON)
            .retrieve()
            .bodyToMono(Client.class)
            .toFuture();

        CompletableFuture<Address> savedAddress = CompletableFuture.completedFuture(Address.builder().street(null).build());
        if (clientDTO.getAddress() != null) {
            savedAddress = addressService.add(
                Address.builder()
                    .clientId(savedClient.join().getId())
                    .street(clientDTO.getAddress())
                    .build()
            ).toFuture();
        }

        CompletableFuture<List<Phone>> savedPhones = CompletableFuture.completedFuture(List.of());
        if (clientDTO.getPhones() != null) {
            var phones = clientDTO.getPhones()
                .stream()
                .map(p ->
                    Phone.builder()
                        .clientId(savedClient.join().getId())
                        .number(p)
                        .build()
                )
                .map(phoneService::add)
                .toList();

            savedPhones = Flux.concat(phones)
                .collect( () -> (List<Phone>) new ArrayList<Phone>(), List::add)
                .toFuture();
        }
        return Mono.just(
            ClientDTO.builder()
                .id(savedClient.join().getId())
                .name(savedClient.join().getName())
                .address(savedAddress.join().getStreet())
                .phones(
                    savedPhones.join()
                        .stream()
                        .map(Phone::getNumber)
                        .toList()
                )
                .build()
        );
    }

    public Mono<Void> justRemoveClient(Long id) {
        return webClient.delete()
            .uri("/api/clients/{id}", Map.of("id", id))
            .accept(MediaType.APPLICATION_NDJSON)
            .retrieve()
            .toBodilessEntity()
            .then();
    }
    public Mono<Void> remove(Long id) {
        return phoneService.removeByClientId(id)
            .then(addressService.removeByClientId(id))
            .then(justRemoveClient(id));
    }

    private Client enrichWithPhones(Client client, CompletableFuture<List<Phone>> phones) {
        client.setPhones(
            phones.join()
                .stream()
                .filter(phone -> phone.getClientId().equals(client.getId()))
                .collect(Collectors.toList())
        );
        log.info("Added phones to client {}", client);
        return client;
    }

    private Client enrichWithAddress(Client client, CompletableFuture<List<Address>> allAddresses) {
        enrichWithAddress(
            client,
            allAddresses.join()
                .stream()
                .filter(address -> address.getClientId().equals(client.getId()))
                .findAny().orElse(null)
        );
        return client;
    }

    private Client enrichWithAddress(Client client, Address address) {
        client.setAddress(address);
        log.info("Added address to client {}", client);
        return client;
    }

    private ClientDTO mapToDTO(Client client) {
        return ClientDTO.builder()
            .id(client.getId())
            .name(client.getName())
            .address(client.getAddress() != null ? client.getAddress().getStreet() : null)
            .phones(
                client.getPhones() != null
                ? client.getPhones()
                    .stream()
                    .map(Phone::getNumber)
                    .collect(Collectors.toList())
                : List.of()
            )
            .build();
    }

    public Mono<ClientDTO> update(Long id, ClientDTO clientDTO) {
        log.info("Requested client update id: {}, clientDTO {}", id, clientDTO);
        var updatedClientFuture = webClient.put()
            .uri("/api/clients/{id}", Map.of("id", id))
            .bodyValue(
                Client.builder()
                    .id(clientDTO.getId())
                    .name(clientDTO.getName())
                    .build()
            )
            .accept(MediaType.APPLICATION_NDJSON)
            .retrieve()
            .bodyToMono(Client.class)
            .toFuture();

        log.info("Requested removing and creation addresses clientId: {}, clientDTO {}", id, clientDTO);
        var updatedAddresses = addressService.removeByClientId(id)
            .then(addressService.add(
                Address.builder()
                    .clientId(id)
                    .street(clientDTO.getAddress())
                    .build()
            )).toFuture();

        log.info("Requested removing phones clientId: {}, clientDTO {}", id, clientDTO);
        phoneService.removeByClientId(id)
            .toFuture()
            .join();

        log.info("Requested creation phones clientId: {}, clientDTO {}", id, clientDTO);
        List<Mono<Phone>> createdPhones = clientDTO.getPhones().stream()
            .map(number ->
                Phone.builder()
                    .clientId(id)
                    .number(number)
                    .build()
            ).map(phoneService::add)
            .toList();

        var updatedPhones = Flux.concat(createdPhones)
            .collectList()
            .toFuture();

        ClientDTO updated = ClientDTO.builder()
            .id(id)
            .name(updatedClientFuture.join().getName())
            .address(updatedAddresses.join().getStreet())
            .phones(
                updatedPhones.join().stream()
                    .map(Phone::getNumber)
                    .toList()
            )
            .build();

        log.info("Client id updated id: {}, clientDTO {}", id, updated);

        return Mono.just(updated);
    }
}
