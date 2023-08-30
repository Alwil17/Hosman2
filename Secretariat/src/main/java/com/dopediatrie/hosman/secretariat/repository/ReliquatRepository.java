package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Reliquat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReliquatRepository extends JpaRepository<Reliquat,Long> {
    @Query("SELECT p from Reliquat p where p.facture.id = :factureId")
    Optional<Reliquat> findByFactureId(@Param("factureId") long factureId);

    @Query("SELECT p from Reliquat p where p.facture.id = :factureId")
    Boolean existsByFactureId(@Param("factureId") long factureId);
}