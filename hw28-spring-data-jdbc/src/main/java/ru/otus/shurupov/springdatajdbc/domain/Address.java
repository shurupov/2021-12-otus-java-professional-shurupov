package ru.otus.shurupov.springdatajdbc.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("address")
@RequiredArgsConstructor
@Getter
public class Address {

    @Id
    private final Long id;

    private final String street;
}
