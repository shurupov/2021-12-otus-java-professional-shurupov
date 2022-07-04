package ru.shurupov.homeowners.core.controller;


import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.shurupov.homeowners.core.domain.Ownership;
import ru.shurupov.homeowners.core.repository.OwnershipRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OwnershipController {

    private final OwnershipRepository ownershipRepository;

    @GetMapping("/api/ownerships")
    public List<Ownership> getAll() {
        return ownershipRepository.findAll();
    }

    @GetMapping("/api/ownerships/{id}")
    public Ownership get(@PathVariable Integer id) throws NotFoundException {
        return ownershipRepository.findById(id).orElseThrow(() -> new NotFoundException("Ownership not found"));
    }

    @PostMapping("/api/ownerships")
    public Ownership post(@RequestBody Ownership ownership) {
        return ownershipRepository.save(ownership);
    }

    @PutMapping("/api/ownerships/{id}")
    public Ownership post(@PathVariable Integer id, @RequestBody Ownership ownership) {
        return ownershipRepository.save(ownership.toBuilder().id(id).build());
    }
}
