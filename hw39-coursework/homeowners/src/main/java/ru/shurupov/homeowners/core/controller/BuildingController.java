package ru.shurupov.homeowners.core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.shurupov.homeowners.core.domain.Building;
import ru.shurupov.homeowners.core.exception.NotFoundException;
import ru.shurupov.homeowners.core.repository.BuildingRepository;

import java.util.List;
@RestController
@RequiredArgsConstructor
public class BuildingController {

    private final BuildingRepository buildingRepository;

    @GetMapping("/api/buildings")
    public List<Building> getAll() {
        return buildingRepository.findAll();
    }

    @GetMapping("/api/buildings/{id}")
    public Building get(@PathVariable Integer id) {
        return buildingRepository.findById(id).orElseThrow(() -> new NotFoundException("Building not found"));
    }

    @PostMapping("/api/buildings")
    public Building post(@RequestBody Building building) {
        return buildingRepository.save(building);
    }

    @PutMapping("/api/buildings/{id}")
    public Building post(@PathVariable Integer id, @RequestBody Building building) {
        return buildingRepository.save(building.toBuilder().id(id).build());
    }

}
