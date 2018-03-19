package com.msincuba.play.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.project", ignoreUnknownFields = false)
public class ProjectProperties {
    
    private String name;
    private String description;
    private String artifact;
    private String version;
}
