package com.msincuba.play.security.repository;

import com.msincuba.play.security.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {

}
