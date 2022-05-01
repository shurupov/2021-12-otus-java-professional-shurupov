package ru.otus.shurupov.springdatajdbc.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("client")
@RequiredArgsConstructor
@Getter
public class Client {

    @Id
    private final Long id;

    private final String name;

    @MappedCollection(idColumn = "client_id", keyColumn = "id")
    private final Address address;

    @MappedCollection(idColumn = "client_id", keyColumn = "id")
    private final List<Phone> phones;
}
