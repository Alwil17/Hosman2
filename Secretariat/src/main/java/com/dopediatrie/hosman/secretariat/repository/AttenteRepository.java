package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Attente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AttenteRepository extends JpaRepository<Attente,Long> {
    @Query("SELECT MAX(a.num_attente) FROM Attente a")
    Long getMaxNumAttente();
}