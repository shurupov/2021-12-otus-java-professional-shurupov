package ru.otus.shurupov.webflux.source.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table("phone")
@Getter
public class Phone {

    @Id
    private final Long id;

    private final Long clientId;

    private final String number;

    @PersistenceConstructor
    public Phone(Long id, Long clientId, String number) {
        this.id = id;
        this.clientId = clientId;
        this.number = number;
    }

    public Phone(Long clientId, String number) {
        this(null, clientId, number);
    }
}
