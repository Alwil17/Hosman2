package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Reduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReductionRepository extends JpaRepository<Reduction,Long> {
    @Query("SELECT p from Reduction p where p.facture.id = :factureId")
    Optional<Reduction> findByFactureId(@Param("factureId") long factureId);

    @Query("SELECT case when count(p)>0 then true else false end from Reduction p where p.facture.id = :factureId")
    Boolean existsByFactureId(@Param("factureId") long factureId);
}