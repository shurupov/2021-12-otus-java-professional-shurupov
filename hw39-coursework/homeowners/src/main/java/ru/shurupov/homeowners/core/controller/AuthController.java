package ru.shurupov.homeowners.core.controller;

import lombok.RequiredArgsConstructor;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.shurupov.homeowners.core.domain.ApartmentUser;
import ru.shurupov.homeowners.core.domain.Role;
import ru.shurupov.homeowners.core.domain.dto.JwtResponse;
import ru.shurupov.homeowners.core.domain.dto.LoginRequest;
import ru.shurupov.homeowners.core.domain.dto.MessageResponse;
import ru.shurupov.homeowners.core.domain.dto.SignupRequest;
import ru.shurupov.homeowners.core.repository.ApartmentUserRepository;
import ru.shurupov.homeowners.core.service.JwtService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final ApartmentUserRepository userRepository;

    private final PasswordEncoder encoder;

    private final JwtService jwtService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = List.of(Role.ROLE_USER.name());
        return ResponseEntity.ok(new JwtResponse(jwt,
            0L,
            userDetails.getUsername(),
            roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Username is already taken!"));
        }

        // Create new user's account
        ApartmentUser user = ApartmentUser.builder()
            .fullName(signUpRequest.getFullName())
            .username(signUpRequest.getUsername())
            .password(encoder.encode(signUpRequest.getPassword()))
            .build();


        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
