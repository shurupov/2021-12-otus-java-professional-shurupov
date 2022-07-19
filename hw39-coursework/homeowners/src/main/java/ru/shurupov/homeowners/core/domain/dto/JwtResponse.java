package ru.shurupov.homeowners.core.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.shurupov.homeowners.core.domain.ApartmentType;
import ru.shurupov.homeowners.core.domain.Role;
import ru.shurupov.homeowners.core.domain.security.UserBuilding;
import ru.shurupov.homeowners.core.domain.security.UserDetailsImpl;
import ru.shurupov.homeowners.core.service.JwtService;

import java.util.List;

@Data
@Builder
public class JwtResponse {
    private String token;

    private Integer id;

    private String fullName;
    private String shortName;
    private String phoneNumber;
    private String telegram;
    private String username;

    private List<String> roles;

    private List<UserBuilding> userBuildings;
}
