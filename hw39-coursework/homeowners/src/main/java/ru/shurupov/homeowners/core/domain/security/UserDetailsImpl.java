package ru.shurupov.homeowners.core.domain.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.shurupov.homeowners.core.domain.User;

import java.util.Collection;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private Integer id;

    private String fullName;
    private String shortName;
    private String phoneNumber;
    private String telegram;
    private String username;
    private String password;

    private List<? extends GrantedAuthority> authorities;

    private List<UserBuilding> userBuildings;

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
