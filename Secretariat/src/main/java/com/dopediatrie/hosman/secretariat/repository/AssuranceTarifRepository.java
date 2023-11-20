package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.AssuranceTarif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AssuranceTarifRepository extends JpaRepository<AssuranceTarif,Long> {
    @Query("select at from AssuranceTarif at where at.tarif.id = :tid and at.assurance.id = :aid")
    Optional<AssuranceTarif> findByAssuranceAndTarifId(@Param("aid") long assur_id, @Param("tid") long tarif_id);

    @Query("select case when count(at)>0 then true else false end from AssuranceTarif at where at.tarif.id = :tid and at.assurance.id = :aid")
    Boolean existsByAssuranceAndTarifId(@Param("aid") long assur_id, @Param("tid") long tarif_id);
}