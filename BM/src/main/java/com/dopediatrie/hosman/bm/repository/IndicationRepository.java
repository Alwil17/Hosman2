package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.Indication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IndicationRepository extends JpaRepository<Indication,Long> {
    boolean existsBySlug(String slug);
    Optional<Indication> findBySlug(String slug);
}