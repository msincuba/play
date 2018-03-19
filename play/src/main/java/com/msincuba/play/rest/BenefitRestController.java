
package com.msincuba.play.rest;

import com.msincuba.play.domain.Benefit;
import com.msincuba.play.dto.BenefitDto;
import com.msincuba.play.mapper.PlayMapper;
import com.msincuba.play.repository.BenefitRepository;
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
@RequestMapping("/api/benefits")
public class BenefitRestController {

    private final BenefitRepository benefitRepository;
    private final PlayMapper mapper;

    public BenefitRestController(BenefitRepository benefitRepository, PlayMapper mapper) {
        this.benefitRepository = benefitRepository;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<Benefit>> list(final @QuerydslPredicate(root = Benefit.class) Predicate predicate, final Pageable pageable) {
        Page<Benefit> pages = benefitRepository.findAll(predicate, pageable);
        return new ResponseEntity<>(pages.getContent(), OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Benefit> get(final @PathVariable Long id) {
        Benefit benefit = benefitRepository.findById(id).orElse(null);
        return new ResponseEntity<>(benefit, OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> put(final @PathVariable Long id, final @RequestBody BenefitDto input) {
        Optional<Benefit> benefitOpt = benefitRepository.findById(id);
        if (benefitOpt.isPresent()) {
            Benefit benefit = mapper.convert(input);
            benefitRepository.save(benefit);
        } else {
            return new ResponseEntity<>(NOT_MODIFIED);
        }
        return new ResponseEntity<>(OK);
    }

    @PostMapping
    public ResponseEntity<BenefitDto> post(final @RequestBody BenefitDto input) {
        Benefit benefit = mapper.convert(input);
        benefitRepository.save(benefit);
        return new ResponseEntity<>(input, OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(final @PathVariable Long id) {
        Optional<Benefit> benefit = benefitRepository.findById(id);
        if (benefit.isPresent()) {
            benefitRepository.delete(benefit.get());
        } else {
            return new ResponseEntity<>(NOT_MODIFIED);
        }
        return new ResponseEntity<>(OK);
    }

}
