package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.Ordonnance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrdonnanceRepository extends JpaRepository<Ordonnance,Long> {

    @Query("select case when count(c)>0 then true else false end from Ordonnance c join c.consultation co where co.reference = :ref")
    boolean existsByConsultation_ref(@Param("ref") String ref);

    @Query("select c from Ordonnance c join c.consultation co where co.reference = :ref")
    Optional<Ordonnance> findByConsultation_ref(@Param("ref") String ref);

    boolean existsByReference(String reference);

    Optional<Ordonnance> findByReference(String reference);

    @Query("select o from Ordonnance o where o.prepositionned = true and o.diagnostic = :diagnostic")
    List<Ordonnance> findAllForDiagnostic(String diagnostic);

    @Query("select o from Ordonnance o where o.stocked = true and o.patient_ref = :patient_ref")
    List<Ordonnance> findAllForPatient(String patient_ref);
}