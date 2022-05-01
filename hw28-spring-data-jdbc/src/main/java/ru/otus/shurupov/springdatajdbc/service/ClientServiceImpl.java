package ru.otus.shurupov.springdatajdbc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.shurupov.springdatajdbc.domain.Address;
import ru.otus.shurupov.springdatajdbc.domain.Client;
import ru.otus.shurupov.springdatajdbc.domain.Phone;
import ru.otus.shurupov.springdatajdbc.repository.AddressRepository;
import ru.otus.shurupov.springdatajdbc.repository.ClientRepository;
import ru.otus.shurupov.springdatajdbc.repository.PhoneRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final PhoneRepository phoneRepository;
    private final AddressRepository addressRepository;

    @Override
    @Transactional(readOnly = true)
    public Iterable<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    @Transactional
    public void create(String name, String address, List<String> phones) {
        final Client client = clientRepository.save(new Client(name));
        phones.forEach( number -> phoneRepository.save(new Phone(client.getId(), number)) );
        addressRepository.save(new Address(client.getId(), address));
    }
}
