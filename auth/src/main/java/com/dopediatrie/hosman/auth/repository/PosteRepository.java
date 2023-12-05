package com.dopediatrie.hosman.auth.repository;

import com.dopediatrie.hosman.auth.entity.Poste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PosteRepository extends JpaRepository<Poste,Long> {

    boolean existsByCode(String code);
    Optional<Poste> findByCodeEquals(String code);
}
