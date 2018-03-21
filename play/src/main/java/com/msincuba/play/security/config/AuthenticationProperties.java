package com.msincuba.play.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.security.authentication")
public class AuthenticationProperties {

    private String tokenHeader;
    private String path;
    private String refreshPath;
    private String secret;
    private long expiration;
}
