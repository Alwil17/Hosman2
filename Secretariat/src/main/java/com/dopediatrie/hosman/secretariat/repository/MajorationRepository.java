package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Majoration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MajorationRepository extends JpaRepository<Majoration,Long> {
    @Query("SELECT p from Majoration p where p.facture.id = :factureId")
    Optional<Majoration> findByFactureId(@Param("factureId") long factureId);

    @Query("SELECT case when count(p)>0 then true else false end from Majoration p where p.facture.id = :factureId")
    Boolean existsByFactureId(@Param("factureId") long factureId);
}