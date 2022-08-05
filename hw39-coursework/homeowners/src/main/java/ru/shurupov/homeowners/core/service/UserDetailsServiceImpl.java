package ru.shurupov.homeowners.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shurupov.homeowners.core.domain.Apartment;
import ru.shurupov.homeowners.core.domain.Building;
import ru.shurupov.homeowners.core.domain.Ownership;
import ru.shurupov.homeowners.core.domain.User;
import ru.shurupov.homeowners.core.domain.security.UserApartment;
import ru.shurupov.homeowners.core.domain.security.UserBuilding;
import ru.shurupov.homeowners.core.domain.security.UserDetailsImpl;
import ru.shurupov.homeowners.core.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(
                String.format("User %s not found", username)
            ));

        UserDetails userDetails = map(user);

        return userDetails;
    }

    private UserDetails map(User user) {

        List<UserBuilding> userBuildings = new ArrayList<>();

        for (Ownership ownership : user.getOwnerships()) {
            Building building = ownership.getApartment().getBuilding();
            Optional<UserBuilding> userBuildingOptional = userBuildings.stream()
                .filter(ub -> ub.getId().equals(building.getId()))
                .findAny();
            UserBuilding userBuilding;
            if (userBuildingOptional.isEmpty()) {
                userBuilding = UserBuilding.builder()
                    .id(building.getId())
                    .address(building.getAddress())
                    .apartments(new ArrayList<>())
                    .build();
                userBuildings.add(userBuilding);
            } else {
                userBuilding = userBuildingOptional.get();
            }

            Apartment apartment = ownership.getApartment();

            userBuilding.getApartments().add(
                UserApartment.builder()
                    .id(apartment.getId())
                    .apartmentType(apartment.getApartmentType())
                    .number(apartment.getNumber())
                    .floor(apartment.getFloor())
                    .square(apartment.getSquare())
                    .ownershipType(ownership.getOwnershipType().getName())
                    .share(ownership.getShare())
                    .decisionMaker(ownership.getDecisionMaker())
                    .build()
            );
        }

        return UserDetailsImpl.builder()
            .id(user.getId())
            .username(user.getUsername())
            .password(user.getPassword())
            .fullName(user.getFullName())
            .shortName(user.getShortName())
            .phoneNumber(user.getPhoneNumber())
            .telegram(user.getTelegram())
            .authorities(
                user.getRoles().stream()
                    .map(r -> new SimpleGrantedAuthority(r.name()))
                    .collect(Collectors.toList())
            )
            .userBuildings(userBuildings)
            .build();
    }
}
