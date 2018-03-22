package com.msincuba.play.security.rest;

import com.msincuba.play.exceptions.EmailAlreadyUsedException;
import com.msincuba.play.exceptions.UsernameAlreadyUsedException;
import com.msincuba.play.security.UserDto;
import com.msincuba.play.security.repository.UserRepository;
import com.msincuba.play.security.service.JwtUserDetailsService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/signup")
public final class SignupController {

    private final UserRepository repository;
    private final JwtUserDetailsService userDetailsService;

    public SignupController(
            final UserRepository repository,
            final JwtUserDetailsService userDetailsService) {
        this.repository = repository;
        this.userDetailsService = userDetailsService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void signup(final @Valid @RequestBody UserDto input) {
        repository.findByUsername(input.getUsername()).ifPresent(user -> {
            throw new UsernameAlreadyUsedException(user.getUsername());
        });
        repository.findByEmail(input.getEmail()).ifPresent(user -> {
            throw new EmailAlreadyUsedException(user.getEmail());
        });
        userDetailsService.signUp(input);
    }

}
