package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.TypeAssurance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeAssuranceRepository extends JpaRepository<TypeAssurance,Long> {
    Boolean existsByNom(String nom);
    Optional<TypeAssurance> findByNom(String nom);
    Optional<TypeAssurance> findBySlug(String slug);
}