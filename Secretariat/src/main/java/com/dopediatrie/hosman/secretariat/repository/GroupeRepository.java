package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Acte;
import com.dopediatrie.hosman.secretariat.entity.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupeRepository extends JpaRepository<Groupe,Long> {
    Optional<Groupe> findByCodeEquals(String code);
}