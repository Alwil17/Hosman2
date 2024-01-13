package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConsultationRepository extends JpaRepository<Consultation,Long> {

    @Override
    @Query("select c from Consultation c order by c.date_consultation desc")
    List<Consultation> findAll();

    @Query("select c from Consultation c where c.patient_ref = :patient order by c.date_consultation desc")
    List<Consultation> findAllByPatient_ref(@Param("patient") String patient);

    @Query("select c from Consultation c where c.reference = :ref")
    Optional<Consultation> findByRef(@Param("ref") String consultationRef);
}