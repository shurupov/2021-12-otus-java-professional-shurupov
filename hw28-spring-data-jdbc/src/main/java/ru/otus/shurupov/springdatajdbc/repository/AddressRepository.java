package ru.otus.shurupov.springdatajdbc.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.shurupov.springdatajdbc.domain.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
