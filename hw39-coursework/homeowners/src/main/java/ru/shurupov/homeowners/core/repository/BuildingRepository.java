package ru.shurupov.homeowners.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shurupov.homeowners.core.domain.Building;

public interface BuildingRepository extends JpaRepository<Building, Integer> {
}
