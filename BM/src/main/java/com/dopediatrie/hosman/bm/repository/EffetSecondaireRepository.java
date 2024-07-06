package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.EffetSecondaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EffetSecondaireRepository extends JpaRepository<EffetSecondaire,Long> {
    boolean existsBySlug(String slug);
    Optional<EffetSecondaire> findBySlug(String slug);
}