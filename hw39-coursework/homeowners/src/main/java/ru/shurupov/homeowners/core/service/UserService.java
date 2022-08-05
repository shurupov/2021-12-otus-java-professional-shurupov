package ru.shurupov.homeowners.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shurupov.homeowners.core.domain.User;
import ru.shurupov.homeowners.core.domain.dto.UserDto;
import ru.shurupov.homeowners.core.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public List<UserDto> findAll() {
        return userRepository.findAll()
            .stream()
            .map(this::map)
            .collect(Collectors.toList());
    }

    public Optional<UserDto> findById(Integer id) {
        return userRepository.findById(id)
            .map(this::map);
    }

    private UserDto map(User user) {
        return UserDto.builder()
            .id(user.getId())
            .fullName(user.getFullName())
            .shortName(user.getShortName())
            .phoneNumber(user.getPhoneNumber())
            .telegram(user.getTelegram())
            .build();
    }
}
