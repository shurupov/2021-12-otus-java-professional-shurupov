package ru.otus.shurupov.webflux.source.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.shurupov.webflux.source.domain.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
