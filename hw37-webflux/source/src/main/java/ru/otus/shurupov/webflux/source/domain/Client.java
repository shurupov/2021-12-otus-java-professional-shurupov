package ru.otus.shurupov.webflux.source.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("client")
@Getter
public class Client {

    @Id
    private final Long id;

    private final String name;

    @MappedCollection(idColumn = "client_id", keyColumn = "id")
    private final Address address;

    @MappedCollection(idColumn = "client_id", keyColumn = "id")
    private final List<Phone> phones;

    @PersistenceConstructor
    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    public Client(String name) {
        this(null, name, null, null);
    }
}
