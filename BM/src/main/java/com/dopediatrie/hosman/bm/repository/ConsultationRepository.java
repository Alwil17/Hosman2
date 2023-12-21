package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation,Long> {

    @Query("select c from Consultation c where c.patient_ref = :patient")
    List<Consultation> findAllByPatient_ref(@Param("patient") String patient);
}