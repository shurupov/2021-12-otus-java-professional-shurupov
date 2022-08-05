package ru.shurupov.homeowners.core.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private final Integer id;
    private final String fullName;
    private final String shortName;
    private final String phoneNumber;
    private final String telegram;
}
