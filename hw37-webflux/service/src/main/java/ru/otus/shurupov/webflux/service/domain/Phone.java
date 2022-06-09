package ru.otus.shurupov.webflux.service.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Phone {

    private final Long id;

    private final Long clientId;

    private final String number;
}
