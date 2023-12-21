package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.ConsultationMotif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ConsultationMotifRepository extends JpaRepository<ConsultationMotif,Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM ConsultationMotif em WHERE em.consultation.id = :consultationId")
    void deleteByConsultationId(@Param("consultationId") long consultationId);
}