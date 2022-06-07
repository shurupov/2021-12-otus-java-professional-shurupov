package ru.otus.shurupov.webflux.source.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table("phone")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
public class Phone {

    @Id
    private Long id;

    private Long clientId;

    private String number;

    @PersistenceConstructor
    public Phone(Long id, Long clientId, String number) {
        this.id = id;
        this.clientId = clientId;
        this.number = number;
    }
}
