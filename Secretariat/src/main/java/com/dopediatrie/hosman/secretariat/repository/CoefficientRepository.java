package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Coefficient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CoefficientRepository extends JpaRepository<Coefficient,Long> {
    @Query("select case when count(a)>0 then true else false end from Coefficient a JOIN a.patient p where p.id = :patId")
    boolean existsByPatientId(@Param("patId") long patient_id);

    @Query("select a from Coefficient a JOIN a.patient p where p.id = :patId")
    Optional<Coefficient> findByPatientId(@Param("patId") long patient_id);

    @Query("select a from Coefficient a JOIN a.patient p where p.reference = :patRef")
    Optional<Coefficient> getCoefficientByPatientReference(@Param("patRef") long patient_ref);
}