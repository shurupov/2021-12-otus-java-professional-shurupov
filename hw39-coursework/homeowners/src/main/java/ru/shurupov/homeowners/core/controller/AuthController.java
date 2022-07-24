package ru.shurupov.homeowners.core.controller;

import lombok.RequiredArgsConstructor;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.shurupov.homeowners.core.domain.User;
import ru.shurupov.homeowners.core.domain.Role;
import ru.shurupov.homeowners.core.domain.dto.JwtResponse;
import ru.shurupov.homeowners.core.domain.dto.LoginRequest;
import ru.shurupov.homeowners.core.domain.dto.MessageResponse;
import ru.shurupov.homeowners.core.domain.dto.SignupRequest;
import ru.shurupov.homeowners.core.domain.security.UserDetailsImpl;
import ru.shurupov.homeowners.core.repository.UserRepository;
import ru.shurupov.homeowners.core.service.AuthenticationService;
import ru.shurupov.homeowners.core.service.JwtService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    @PostMapping("/signin")
    public JwtResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticationService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @PostMapping("/refresh")
    public JwtResponse refresh() {
        return authenticationService.refreshToken();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Ошибка: Имя пользователя уже занято!"));
        }

        Optional<User> userOptional = userRepository.findByRegHash(signUpRequest.getHash());

        if (userOptional.isEmpty()) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Ошибка: Сыылка рагистрации не верная!"));
        }

        User user = userOptional.get().toBuilder()
            .username(signUpRequest.getUsername())
            .password(encoder.encode(signUpRequest.getPassword()))
            .regHash(null)
            .roles(List.of(Role.ROLE_USER))
            .build();


        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Вы успешно зарегистрировались!"));
    }

}
