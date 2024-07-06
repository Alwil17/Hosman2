package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.ContreIndication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContreIndicationRepository extends JpaRepository<ContreIndication,Long> {
    boolean existsBySlug(String slug);
    Optional<ContreIndication> findBySlug(String slug);
}