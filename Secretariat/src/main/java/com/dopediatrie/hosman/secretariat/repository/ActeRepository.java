package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Acte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActeRepository extends JpaRepository<Acte,Long> {
    Optional<Acte> findByCodeEquals(String code);
}