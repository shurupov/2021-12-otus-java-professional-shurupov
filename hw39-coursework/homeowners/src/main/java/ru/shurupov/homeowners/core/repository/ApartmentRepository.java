package ru.shurupov.homeowners.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shurupov.homeowners.core.domain.Apartment;

public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {
}
