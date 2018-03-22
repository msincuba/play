package com.msincuba.play.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.msincuba.play.security.domain.User;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements UserDetails{

    @JsonIgnore
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    @JsonIgnore
    private String password;
    private String email;
    private Set<? extends GrantedAuthority> authorities;
    private boolean enabled;
    @JsonIgnore
    private Instant lastPasswordResetDate;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.password = user.getPassword();
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = user.getAuthorities().stream().map(authority -> {
            return authority != null ? new SimpleGrantedAuthority(authority.getName()) : null;
        }).collect(Collectors.toSet());
        this.authorities = simpleGrantedAuthorities;
        this.enabled = user.getEnabled();
        this.lastPasswordResetDate = user.getLastPasswordResetDate();
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

}
