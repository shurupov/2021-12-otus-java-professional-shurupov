package ru.shurupov.homeowners.core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shurupov.homeowners.core.domain.Building;
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

}
