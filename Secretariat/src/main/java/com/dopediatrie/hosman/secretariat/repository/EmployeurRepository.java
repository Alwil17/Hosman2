package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Employeur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeurRepository extends JpaRepository<Employeur,Long> {
    Boolean existsByNom(String nom);
    Optional<Employeur> findByNom(String nom);
}