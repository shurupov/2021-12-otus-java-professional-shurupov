package ru.otus.shurupov.webflux.source.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.shurupov.webflux.source.domain.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
