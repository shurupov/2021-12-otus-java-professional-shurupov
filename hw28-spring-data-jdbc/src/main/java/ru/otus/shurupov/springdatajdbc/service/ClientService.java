package ru.otus.shurupov.springdatajdbc.service;

import ru.otus.shurupov.springdatajdbc.domain.Client;

public interface ClientService {

    Iterable<Client> getAll();
}
