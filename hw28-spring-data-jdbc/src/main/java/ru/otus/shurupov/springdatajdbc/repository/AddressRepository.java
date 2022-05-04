package ru.otus.shurupov.springdatajdbc.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.shurupov.springdatajdbc.domain.Address;

import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address, Long> {

    Optional<Address> findTopByClientId(Long clientId);

    void deleteAllByClientId(Long clientId);
}
