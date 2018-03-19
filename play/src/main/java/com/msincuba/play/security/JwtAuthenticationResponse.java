package com.msincuba.play.security;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class JwtAuthenticationResponse implements Serializable {

    @Getter
    private final String token;
}
