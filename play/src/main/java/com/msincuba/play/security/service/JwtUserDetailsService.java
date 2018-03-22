package com.msincuba.play.security.service;

import com.msincuba.play.mapper.PlayMapper;
import com.msincuba.play.security.AuthoritiesConstants;
import com.msincuba.play.security.UserDto;
import com.msincuba.play.security.UserFactory;
import com.msincuba.play.security.domain.Authority;
import com.msincuba.play.security.domain.User;
import com.msincuba.play.security.repository.AuthorityRepository;
import com.msincuba.play.security.repository.UserRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final PlayMapper playMapper;

    public JwtUserDetailsService(
            final UserRepository userRepository,
            final AuthorityRepository authorityRepository,
            final PasswordEncoder passwordEncoder,
            final PlayMapper playMapper) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.playMapper = playMapper;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElseThrow(() -> {
            return new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        });
        return UserFactory.create(user);
    }

    public User signUp(UserDto userDto) {

        Authority authority = authorityRepository.getOne(AuthoritiesConstants.USER);
        Set<Authority> authorities = new HashSet<>(1);
        authorities.add(authority);
        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
        User newUser = playMapper.convert(userDto);
        newUser.setPassword(encryptedPassword);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        return newUser;
    }

}
