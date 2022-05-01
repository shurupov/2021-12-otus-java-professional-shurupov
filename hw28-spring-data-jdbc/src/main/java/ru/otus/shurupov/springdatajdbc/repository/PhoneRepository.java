package ru.otus.shurupov.springdatajdbc.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.shurupov.springdatajdbc.domain.Phone;

public interface PhoneRepository extends CrudRepository<Phone, Long> {
}
