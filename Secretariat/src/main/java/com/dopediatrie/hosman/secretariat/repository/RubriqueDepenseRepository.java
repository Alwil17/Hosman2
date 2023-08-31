package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.RubriqueDepense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RubriqueDepenseRepository extends JpaRepository<RubriqueDepense,Long> {
    Optional<RubriqueDepense> findByNom(String nom);

    boolean existsByNom(String nom);
}