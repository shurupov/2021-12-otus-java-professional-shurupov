package ru.shurupov.homeowners.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.shurupov.homeowners.core.domain.dto.JwtResponse;
import ru.shurupov.homeowners.core.domain.security.UserDetailsImpl;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public JwtResponse authenticateUser(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return getToken(authentication);
    }

    public JwtResponse refreshToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getToken(authentication);
    }

    private JwtResponse getToken(Authentication authentication) {
        String jwt = jwtService.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
        return JwtResponse.builder()
            .token(jwt)
            .id(userDetails.getId())
            .username(userDetails.getUsername())
            .fullName(userDetails.getFullName())
            .shortName(userDetails.getShortName())
            .phoneNumber(userDetails.getPhoneNumber())
            .telegram(userDetails.getTelegram())
            .roles(roles)
            .userBuildings(userDetails.getUserBuildings())
            .build();
    }
}
