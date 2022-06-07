package ru.otus.shurupov.webflux.source.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.shurupov.webflux.source.domain.Address;

import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address, Long> {

    Optional<Address> findTopByClientId(Long clientId);

    void deleteAllByClientId(Long clientId);
}
