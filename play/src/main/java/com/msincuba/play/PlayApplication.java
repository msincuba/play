package com.msincuba.play;

import com.msincuba.play.config.ProjectProperties;
import com.msincuba.play.security.config.AuthenticationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ProjectProperties.class, AuthenticationProperties.class})
public class PlayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlayApplication.class, args);
    }
}
