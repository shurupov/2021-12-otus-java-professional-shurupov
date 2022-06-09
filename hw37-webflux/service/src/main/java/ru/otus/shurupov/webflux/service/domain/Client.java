package ru.otus.shurupov.webflux.service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    private Long id;

    private String name;

    private Address address;

    private List<Phone> phones;
}
