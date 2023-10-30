package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Etat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EtatRepository extends JpaRepository<Etat,Long> {
    Optional<Etat> findBySlug(String slug);
    Boolean existsBySlug(String slug);
}