
package com.msincuba.play.rest;

import com.msincuba.play.domain.Banking;
import com.msincuba.play.domain.Member;
import com.msincuba.play.dto.BankingDto;
import com.msincuba.play.mapper.PlayMapper;
import com.msincuba.play.repository.BankingRepository;
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
@RequestMapping("/api/banking")
public class BankingRestController {

    private final BankingRepository repository;
    private final MemberRepository memberRepository;
    private final PlayMapper mapper;

    public BankingRestController(BankingRepository repository, MemberRepository memberRepository, PlayMapper mapper) {
        this.repository = repository;
        this.memberRepository = memberRepository;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<Banking>> list(@QuerydslPredicate(root = Banking.class) Predicate predicate, Pageable pageable) {
        Page<Banking> pages = repository.findAll(predicate, pageable);
        return new ResponseEntity<>(pages.getContent(), OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Banking> get(@PathVariable Long id) {
        Banking benefit = repository.findById(id).orElse(null);
        return new ResponseEntity<>(benefit, OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BankingDto> put(final @PathVariable Long id, final @RequestBody BankingDto input) {
        Optional<Banking> optBanking = repository.findById(id);
        if (optBanking.isPresent()) {
            Banking banking = mapper.convert(input);
            banking.setMainMember(optBanking.get().getMainMember());
            repository.save(banking);
        } else {
            return new ResponseEntity<>(input, NOT_MODIFIED);
        }
        return new ResponseEntity<>(input, OK);
    }

    @PostMapping
    public ResponseEntity<BankingDto> post(final @RequestBody BankingDto input) {
        final Long memberId = input.getMemberId();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> {
            return new RuntimeException("Member not found for id" + memberId); 
        });
        Banking banking = mapper.convert(input);
        banking.setMainMember(member);
        repository.save(banking);
        return new ResponseEntity<>(input, OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Banking> benefit = repository.findById(id);
        if (benefit.isPresent()) {
            repository.delete(benefit.get());
        } else {
            return new ResponseEntity<>(NOT_MODIFIED);
        }
        return new ResponseEntity<>(OK);
    }

}
