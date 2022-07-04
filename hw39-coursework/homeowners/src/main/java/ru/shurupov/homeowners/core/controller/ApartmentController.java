package ru.shurupov.homeowners.core.controller;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.shurupov.homeowners.core.domain.Apartment;
import ru.shurupov.homeowners.core.repository.ApartmentRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApartmentController {

    private final ApartmentRepository apartmentRepository;

    @GetMapping("/api/apartments")
    public List<Apartment> getAll() {
        return apartmentRepository.findAll();
    }

    @GetMapping("/api/apartments/{id}")
    public Apartment get(@PathVariable Integer id) throws NotFoundException {
        return apartmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Apartment not found"));
    }

    @PostMapping("/api/apartments")
    public Apartment post(@RequestBody Apartment Apartment) {
        return apartmentRepository.save(Apartment);
    }

    @PutMapping("/api/apartments/{id}")
    public Apartment post(@PathVariable Integer id, @RequestBody Apartment Apartment) {
        return apartmentRepository.save(Apartment.toBuilder().id(id).build());
    }
}
