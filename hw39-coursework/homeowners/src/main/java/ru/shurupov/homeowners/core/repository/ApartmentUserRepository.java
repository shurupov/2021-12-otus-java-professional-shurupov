package ru.shurupov.homeowners.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shurupov.homeowners.core.domain.ApartmentUser;

import java.util.Optional;

public interface ApartmentUserRepository extends JpaRepository<ApartmentUser, Integer> {
    Optional<ApartmentUser> findByUsername(String username);

    boolean existsByUsername(String username);


}
