package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Assurance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssuranceRepository extends JpaRepository<Assurance,Long> {
    Optional<Assurance> findByNom(String nom);
    boolean existsByNom(String nom);
}