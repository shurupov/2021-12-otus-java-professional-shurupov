package ru.otus.shurupov.webflux.service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Phone {

    private Long id;

    private Long clientId;

    private String number;
}
