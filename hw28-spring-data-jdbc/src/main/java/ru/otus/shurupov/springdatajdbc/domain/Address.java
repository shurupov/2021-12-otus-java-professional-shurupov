package ru.otus.shurupov.springdatajdbc.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table("address")
@Getter
public class Address {

    @Id
    private final Long id;

    private final Long clientId;

    private final String street;

    @PersistenceConstructor
    public Address(Long id, Long clientId, String street) {
        this.id = id;
        this.clientId = clientId;
        this.street = street;
    }

    public Address(Long clientId, String street) {
        this(null, clientId, street);
    }
}
