package ru.otus.shurupov.webflux.source.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.shurupov.webflux.source.domain.Phone;

public interface PhoneRepository extends CrudRepository<Phone, Long> {
    Iterable<Phone> findByClientId(Long clientId);

    @Modifying
    @Query("delete from phone where client_id = :clientId")
    void deleteByClientId(@Param("clientId") Long clientId);
}
