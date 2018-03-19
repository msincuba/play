package com.msincuba.play.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.Collection;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class JwtUser implements UserDetails {

    @JsonIgnore
    private final Long id;
    private final String username;
    private final String firstname;
    private final String lastname;
    @JsonIgnore
    private final String password;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;
    @JsonIgnore
    private final LocalDateTime lastPasswordResetDate;

    public JwtUser(
            Long id,
            String username,
            String firstname,
            String lastname,
            String email,
            String password, Collection<? extends GrantedAuthority> authorities,
            boolean enabled,
            LocalDateTime lastPasswordResetDate
    ) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
