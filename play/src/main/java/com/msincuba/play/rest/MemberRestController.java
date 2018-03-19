
package com.msincuba.play.rest;

import com.msincuba.play.domain.Member;
import com.msincuba.play.repository.MemberRepository;
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
@RequestMapping("/api/members")
public class MemberRestController {

    private final MemberRepository repository;

    public MemberRestController(MemberRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Member>> list(@QuerydslPredicate(root = Member.class) Predicate predicate, Pageable pageable) {
        Page<Member> pages = repository.findAll(predicate, pageable);
        return new ResponseEntity<>(pages.getContent(), OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Member> get(@PathVariable Long id) {
        Member benefit = repository.findById(id).orElse(null);
        return new ResponseEntity<>(benefit, OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Member> put(@PathVariable Long id, @RequestBody Member input) {
        Optional<Member> benefit = repository.findById(id);
        if (benefit.isPresent()) {
            repository.save(benefit.get());
        } else {
            return new ResponseEntity<>(input, NOT_MODIFIED);
        }
        return new ResponseEntity<>(input, OK);
    }

    @PostMapping
    public ResponseEntity<Member> post(@RequestBody Member input) {
        repository.save(input);
        return new ResponseEntity<>(input, OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Member> benefit = repository.findById(id);
        if (benefit.isPresent()) {
            repository.delete(benefit.get());
        } else {
            return new ResponseEntity<>(NOT_MODIFIED);
        }
        return new ResponseEntity<>(OK);
    }

}
