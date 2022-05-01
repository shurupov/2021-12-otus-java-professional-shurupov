package ru.otus.shurupov.springdatajdbc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.shurupov.springdatajdbc.domain.Client;
import ru.otus.shurupov.springdatajdbc.repository.ClientRepository;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public Iterable<Client> getAll() {
        return clientRepository.findAll();
    }
}
