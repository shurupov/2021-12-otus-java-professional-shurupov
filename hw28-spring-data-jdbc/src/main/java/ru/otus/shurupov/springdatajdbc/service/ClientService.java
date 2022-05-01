package ru.otus.shurupov.springdatajdbc.service;

import ru.otus.shurupov.springdatajdbc.domain.Client;

import java.util.List;

public interface ClientService {

    Iterable<Client> getAll();

    void create(String name, String address, List<String> phones);

    void remove(Long id);

    Client get(Long id);

    void update(Long id, String name, String address, List<String> phones);
}
