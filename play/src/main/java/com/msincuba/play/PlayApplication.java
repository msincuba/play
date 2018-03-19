package com.msincuba.play;

import com.msincuba.play.config.ProjectProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PlayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlayApplication.class, args);
    }
    @Bean
    public ProjectProperties projectProperties() {
        return new ProjectProperties();
    }
}
