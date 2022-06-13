package ru.otus.shurupov.webflux.source.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.shurupov.webflux.source.domain.Address;

import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address, Long> {

    Optional<Address> findTopByClientId(Long clientId);

    @Modifying
    @Query("delete from address where client_id = :clientId")
    void deleteByClientId(@Param("clientId") Long clientId);
}
