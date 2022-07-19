package ru.shurupov.homeowners.core.domain.security;

import lombok.Builder;
import lombok.Getter;
import ru.shurupov.homeowners.core.domain.ApartmentType;

@Builder
@Getter
public class UserApartment {
    private final Integer id;
    private final ApartmentType apartmentType;
    private final Integer number;
    private final Integer floor;
    private final Float square;

    private final String ownershipType;
    private final Float share;
    private final Boolean decisionMaker;
}
