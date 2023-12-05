package com.dopediatrie.hosman.auth.repository;

import com.dopediatrie.hosman.auth.entity.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartementRepository extends JpaRepository<Departement,Long> {
    boolean existsByCode(String code);
    Optional<Departement> findByCodeEquals(String code);
}
