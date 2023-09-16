package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Ville;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VilleRepository extends JpaRepository<Ville,Long> {
    Boolean existsByNom(String nom);
    Optional<Ville> findByNom(String nom);
}