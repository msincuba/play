package com.msincuba.play.rest;

import com.msincuba.play.domain.Contact;
import com.msincuba.play.domain.Member;
import com.msincuba.play.dto.ContactDto;
import com.msincuba.play.mapper.PlayMapper;
import com.msincuba.play.repository.ContactRepository;
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
@RequestMapping("/api/contacts")
public final class ContactRestController {

    private final ContactRepository repository;
    private final PlayMapper mapper;
    private final MemberRepository memberRepository;

    public ContactRestController(ContactRepository repository, PlayMapper mapper, MemberRepository memberRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.memberRepository = memberRepository;
    }

    @GetMapping
    public ResponseEntity<List<Contact>> list(final @QuerydslPredicate(root = Contact.class) Predicate predicate, final Pageable pageable) {
        Page<Contact> pages = repository.findAll(predicate, pageable);
        return new ResponseEntity<>(pages.getContent(), OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Contact> get(final @PathVariable Long id) {
        final Contact benefit = repository.findById(id).orElse(null);
        return new ResponseEntity<>(benefit, OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> put(final @PathVariable Long id, final @RequestBody ContactDto input) {
        Optional<Contact> contactOpt = repository.findById(id);
        if (contactOpt.isPresent()) {
            Contact contact = mapper.convert(input);
            Optional<Member> member = memberRepository.findById(input.getMemberId());
            contact.setMainMember(member.orElseThrow(() -> {
                return new RuntimeException("Member not supplied");
            }));
            repository.save(contact);
        } else {
            return new ResponseEntity<>(NOT_MODIFIED);
        }
        return new ResponseEntity<>(OK);
    }

    @PostMapping
    public ResponseEntity<ContactDto> post(final @RequestBody ContactDto input) {
        Contact contact = mapper.convert(input);
        Optional<Member> member = memberRepository.findById(input.getMemberId());
        contact.setMainMember(member.orElseThrow(() -> {
            return new RuntimeException("Member not supplied");
        }));
        repository.save(contact);
        return new ResponseEntity<>(input, OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Contact> benefit = repository.findById(id);
        if (benefit.isPresent()) {
            repository.delete(benefit.get());
        } else {
            return new ResponseEntity<>(NOT_MODIFIED);
        }
        return new ResponseEntity<>(OK);
    }

}
