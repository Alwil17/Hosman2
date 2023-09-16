package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Quartier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuartierRepository extends JpaRepository<Quartier,Long> {
    Boolean existsByNom(String nom);
    Optional<Quartier> findByNom(String nom);
}