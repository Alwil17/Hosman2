package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.ConsultationDiagnostic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ConsultationDiagnosticRepository extends JpaRepository<ConsultationDiagnostic,Long> {
    @Query("select case when count(lit)>0 then true else false end from ConsultationDiagnostic lit JOIN lit.consultation ch where ch.id = :consultationId")
    boolean existsByConsultationId(@Param("consultationId") long consultation_id);

    @Query("select lit from ConsultationDiagnostic lit JOIN lit.consultation ch where ch.id = :consultationId")
    List<ConsultationDiagnostic> findByConsultationId(@Param("consultationId") long consultation_id);

    @Transactional
    @Modifying
    @Query("DELETE FROM ConsultationDiagnostic em WHERE em.consultation.id = :consultationId")
    void deleteByConsultationId(@Param("consultationId") long consultationId);
}