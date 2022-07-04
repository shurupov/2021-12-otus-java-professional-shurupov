package ru.shurupov.homeowners.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shurupov.homeowners.core.domain.ApartmentUser;

public interface ApartmentUserRepository extends JpaRepository<ApartmentUser, Integer> {
}
