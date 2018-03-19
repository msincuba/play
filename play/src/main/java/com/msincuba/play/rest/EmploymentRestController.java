
package com.msincuba.play.rest;

import com.msincuba.play.domain.Employment;
import com.msincuba.play.repository.EmploymentRepository;
import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employment")
public class EmploymentRestController {

    private final EmploymentRepository repository;

    public EmploymentRestController(EmploymentRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Employment>> list(@QuerydslPredicate(root = Employment.class) Predicate predicate, Pageable pageable) {
        Page<Employment> pages = repository.findAll(predicate, pageable);
        return new ResponseEntity<>(pages.getContent(), OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Employment> get(@PathVariable Long id) {
        Employment benefit = repository.findById(id).orElse(null);
        return new ResponseEntity<>(benefit, OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> put(@PathVariable Long id, @RequestBody Employment input) {
        Optional<Employment> benefit = repository.findById(id);
        if (benefit.isPresent()) {
            repository.save(benefit.get());
        } else {
            return new ResponseEntity<>(NOT_MODIFIED);
        }
        return new ResponseEntity<>(OK);
    }

    @PostMapping
    public ResponseEntity<Employment> post(@RequestBody Employment input) {
        repository.save(input);
        return new ResponseEntity<>(input, OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Employment> benefit = repository.findById(id);
        if (benefit.isPresent()) {
            repository.delete(benefit.get());
        } else {
            return new ResponseEntity<>(NOT_MODIFIED);
        }
        return new ResponseEntity<>(OK);
    }

}
