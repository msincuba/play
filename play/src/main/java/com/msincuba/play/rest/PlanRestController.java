
package com.msincuba.play.rest;

import com.msincuba.play.domain.Plan;
import com.msincuba.play.repository.PlanRepository;
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
@RequestMapping("/api/plans")
public class PlanRestController {

    private final PlanRepository repository;

    public PlanRestController(PlanRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Plan>> list(@QuerydslPredicate(root = Plan.class) Predicate predicate, Pageable pageable) {
        Page<Plan> pages = repository.findAll(predicate, pageable);
        return new ResponseEntity<>(pages.getContent(), OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Plan> get(@PathVariable Long id) {
        Plan benefit = repository.findById(id).orElse(null);
        return new ResponseEntity<>(benefit, OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Plan> put(@PathVariable Long id, @RequestBody Plan input) {
        Optional<Plan> benefit = repository.findById(id);
        if (benefit.isPresent()) {
            repository.save(benefit.get());
        } else {
            return new ResponseEntity<>(input, NOT_MODIFIED);
        }
        return new ResponseEntity<>(input, OK);
    }

    @PostMapping
    public ResponseEntity<Plan> post(@RequestBody Plan input) {
        repository.save(input);
        return new ResponseEntity<>(input, OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Plan> benefit = repository.findById(id);
        if (benefit.isPresent()) {
            repository.delete(benefit.get());
        } else {
            return new ResponseEntity<>(NOT_MODIFIED);
        }
        return new ResponseEntity<>(OK);
    }

}
