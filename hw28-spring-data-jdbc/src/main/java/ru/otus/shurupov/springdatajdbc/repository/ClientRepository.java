package ru.otus.shurupov.springdatajdbc.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.shurupov.springdatajdbc.domain.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
