package ru.shurupov.homeowners.core.domain.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String password;
}
