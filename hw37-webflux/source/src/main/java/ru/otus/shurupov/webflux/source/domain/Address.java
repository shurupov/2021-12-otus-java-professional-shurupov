package ru.otus.shurupov.webflux.source.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table("address")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
public class Address {

    @Id
    private Long id;

    private Long clientId;

    private String street;

    @PersistenceConstructor
    public Address(Long id, Long clientId, String street) {
        this.id = id;
        this.clientId = clientId;
        this.street = street;
    }
}
