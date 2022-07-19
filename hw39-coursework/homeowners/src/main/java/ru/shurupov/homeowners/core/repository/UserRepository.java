package ru.shurupov.homeowners.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shurupov.homeowners.core.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);

    Optional<User> findByRegHash(String hash);
}
