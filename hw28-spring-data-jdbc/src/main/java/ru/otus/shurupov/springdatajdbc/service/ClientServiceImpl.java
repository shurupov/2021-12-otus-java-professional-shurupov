package ru.otus.shurupov.springdatajdbc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.otus.shurupov.springdatajdbc.domain.Address;
import ru.otus.shurupov.springdatajdbc.domain.Client;
import ru.otus.shurupov.springdatajdbc.domain.Phone;
import ru.otus.shurupov.springdatajdbc.exception.ClientNotFoundException;
import ru.otus.shurupov.springdatajdbc.repository.AddressRepository;
import ru.otus.shurupov.springdatajdbc.repository.ClientRepository;
import ru.otus.shurupov.springdatajdbc.repository.PhoneRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        phones.forEach( number -> {
            if (StringUtils.hasText(number)) {
                phoneRepository.save(new Phone(client.getId(), number));
            }
        });
        addressRepository.save(new Address(client.getId(), address));
    }

    @Override
    @Transactional
    public void remove(Long id) {
        addressRepository.deleteAllByClientId(id);
        phoneRepository.deleteByClientId(id);
        clientRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Client get(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException("Client not found by id " + id));
        while (client.getPhones().size() < 3) {
            client.getPhones().add(new Phone(client.getId(), ""));
        }
        return client;
    }

    @Override
    @Transactional
    public void update(Long id, String name, String address, List<String> phones) {
        phoneRepository.deleteByClientId(id);
        List<Phone> phoneList = new ArrayList<>(phones.size());
        for (String number : phones) {
            if (StringUtils.hasText(number)) {
                phoneList.add(phoneRepository.save(new Phone(id, number)));
            }
        }
        Optional<Address> addressOptional = addressRepository.findTopByClientId(id);
        Address newAddress;
        if (addressOptional.isPresent()) {
            Address address1 = addressOptional.get();
            newAddress = new Address(address1.getId(), id, address);
        } else {
            newAddress = new Address(id, address);
        }
        addressRepository.save(newAddress);
        clientRepository.save(new Client(id, name, newAddress, phoneList));
    }
}
