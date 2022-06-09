package ru.otus.shurupov.webflux.service.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Address {

    private final Long id;

    private final Long clientId;

    private final String street;
}
