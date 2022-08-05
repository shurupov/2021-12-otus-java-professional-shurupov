package ru.shurupov.homeowners.core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.shurupov.homeowners.core.domain.User;
import ru.shurupov.homeowners.core.domain.dto.UserDto;
import ru.shurupov.homeowners.core.exception.NotFoundException;
import ru.shurupov.homeowners.core.repository.UserRepository;
import ru.shurupov.homeowners.core.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users")
    public List<UserDto> getAll() {
        return userService.findAll();
    }

    @GetMapping("/api/users/{id}")
    public UserDto get(@PathVariable Integer id) {
        return userService
            .findById(id)
            .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
