package ru.shurupov.homeowners.core.controller;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.shurupov.homeowners.core.domain.ApartmentUser;
import ru.shurupov.homeowners.core.repository.ApartmentUserRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final ApartmentUserRepository apartmentUserRepository;

    @GetMapping("/api/users")
    public List<ApartmentUser> getAll() {
        return apartmentUserRepository.findAll();
    }

    @GetMapping("/api/users/{id}")
    public ApartmentUser get(@PathVariable Integer id) throws NotFoundException {
        return apartmentUserRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @PostMapping("/api/users")
    public ApartmentUser post(@RequestBody ApartmentUser apartmentUser) {
        return apartmentUserRepository.save(apartmentUser);
    }

    @PutMapping("/api/users/{id}")
    public ApartmentUser post(@PathVariable Integer id, @RequestBody ApartmentUser apartmentUser) {
        return apartmentUserRepository.save(apartmentUser.toBuilder().id(id).build());
    }
}
