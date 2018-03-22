package com.msincuba.play.security.rest;

import com.msincuba.play.exceptions.AuthenticationException;
import com.msincuba.play.security.LoginRequest;
import com.msincuba.play.security.LoginResponse;
import com.msincuba.play.security.TokenProvider;
import com.msincuba.play.security.config.AuthenticationProperties;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class AuthenticationController {

    @Autowired
    private AuthenticationProperties authenticationProperties;
    
    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    public AuthenticationController(
            final AuthenticationManager authenticationManager, 
            @Qualifier("jwtUserDetailsService") final UserDetailsService userDetailsService,
            final TokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("${app.security.authentication.path:/login}")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        // Reload password post-security so we can generate the token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = tokenProvider.createToken(userDetails);

        // Return the token
        return ResponseEntity.ok(new LoginResponse(token));
    }
    
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    /**
     * Authenticates the user. If something is wrong, an
     * {@link AuthenticationException} will be thrown
     */
    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

}
