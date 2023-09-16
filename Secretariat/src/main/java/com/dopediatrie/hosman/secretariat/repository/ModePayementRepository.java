package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.ModePayement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModePayementRepository extends JpaRepository<ModePayement,Long> {
    Optional<ModePayement> findByNom(String nom);
    boolean existsByNom(String nom);
}