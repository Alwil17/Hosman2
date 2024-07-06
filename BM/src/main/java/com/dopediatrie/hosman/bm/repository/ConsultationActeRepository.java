package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.ConsultationActe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ConsultationActeRepository extends JpaRepository<ConsultationActe,Long> {
    @Query("select case when count(lit)>0 then true else false end from ConsultationActe lit JOIN lit.consultation ch where ch.id = :consultationId")
    boolean existsByConsultationId(@Param("consultationId") long consultation_id);

    @Query("select lit from ConsultationActe lit JOIN lit.consultation ch where ch.id = :consultationId")
    List<ConsultationActe> findByConsultationId(@Param("consultationId") long consultation_id);

    @Transactional
    @Modifying
    @Query("DELETE FROM ConsultationActe em WHERE em.consultation.id = :consultationId")
    void deleteByConsultationId(@Param("consultationId") long consultationId);

    @Query("select c from ConsultationActe c JOIN c.consultation ch where ch.id = :consultationId")
    List<ConsultationActe> findAllByConsultation(@Param("consultationId") long id);
}