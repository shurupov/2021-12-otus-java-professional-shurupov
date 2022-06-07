package ru.otus.shurupov.webflux.source.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.shurupov.webflux.source.domain.Phone;

public interface PhoneRepository extends CrudRepository<Phone, Long> {
}
