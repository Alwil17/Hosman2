package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.OrdonnancePrescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface OrdonnancePrescriptionRepository extends JpaRepository<OrdonnancePrescription,Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM OrdonnancePrescription em WHERE em.ordonnance.id = :ordonnanceId")
    void deleteByOrdonnanceId(@Param("ordonnanceId") long ordonnanceId);
}