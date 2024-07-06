package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.Helpers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HelpersRepository extends JpaRepository<Helpers,Long> {
    boolean existsByType(String type);
    List<Helpers> findByType(String type);
    boolean existsBySlug(String slug);
    Optional<Helpers> findBySlug(String slug);
}