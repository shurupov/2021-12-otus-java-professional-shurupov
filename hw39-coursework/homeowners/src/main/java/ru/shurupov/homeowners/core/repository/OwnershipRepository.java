package ru.shurupov.homeowners.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shurupov.homeowners.core.domain.Ownership;

public interface OwnershipRepository extends JpaRepository<Ownership, Integer> {
}
