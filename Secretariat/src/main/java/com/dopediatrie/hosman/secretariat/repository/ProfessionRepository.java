package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Profession;
import com.dopediatrie.hosman.secretariat.entity.Quartier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessionRepository extends JpaRepository<Profession,Long> {
    Boolean existsByDenomination(String nom);
    Optional<Profession> findByDenomination(String nom);
}