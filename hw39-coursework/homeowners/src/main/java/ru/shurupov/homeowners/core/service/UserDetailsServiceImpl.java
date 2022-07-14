package ru.shurupov.homeowners.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shurupov.homeowners.core.domain.ApartmentUser;
import ru.shurupov.homeowners.core.repository.ApartmentUserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ApartmentUserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApartmentUser user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(
                String.format("User %s not found", username)
            ));
        return UserDetailsImpl.build(user);
    }
}
