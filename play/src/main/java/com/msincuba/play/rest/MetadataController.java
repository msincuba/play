package com.msincuba.play.rest;

import com.msincuba.play.config.ProjectProperties;
import javax.inject.Inject;
import static org.springframework.http.HttpStatus.OK;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/metadata")
public class MetadataController {

    @Inject
    private ProjectProperties projectProperties;

    @GetMapping("/project-info")
    public ResponseEntity<ProjectProperties> projectInformation() {
        return new ResponseEntity<>(projectProperties, OK);
    }

}
