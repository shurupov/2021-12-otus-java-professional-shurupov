package ru.shurupov.homeowners.core.domain.security;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class UserBuilding {
    private final Integer id;
    private final String address;
    private final List<UserApartment> apartments;
}