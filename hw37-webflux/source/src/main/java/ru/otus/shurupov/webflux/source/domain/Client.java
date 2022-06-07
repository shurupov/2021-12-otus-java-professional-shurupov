package ru.otus.shurupov.webflux.source.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("client")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
public class Client {

    @Id
    private Long id;

    private String name;

    @PersistenceConstructor
    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
