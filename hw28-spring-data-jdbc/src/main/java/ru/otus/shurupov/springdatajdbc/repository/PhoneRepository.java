package ru.otus.shurupov.springdatajdbc.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.shurupov.springdatajdbc.domain.Phone;

import java.util.List;

public interface PhoneRepository extends CrudRepository<Phone, Long> {

    @Modifying
    @Query("delete from phone where client_id = :clientId")
    void deleteByClientId(@Param("clientId") Long clientId);
}
