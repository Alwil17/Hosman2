package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Filiation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FiliationRepository extends JpaRepository<Filiation,Long> {

    @Query("select case when count(a)>0 then true else false end from Filiation a JOIN a.patient p where p.id = :patId and a.type = :type")
    boolean existsByFiliationByPatientIdAndType(@Param("patId") long patient_id, @Param("type") String type);

    @Query("select a from Filiation a JOIN a.patient p where p.id = :patId and a.type = :type")
    Optional<Filiation> findByFiliationByPatientIdAndType(@Param("patId") long patient_id, @Param("type") String type);

    @Query("select a from Filiation a JOIN a.patient p where p.id = :patId")
    List<Filiation> getFiliationByPatientId(@Param("patId") long patient_id);

    @Query("select a from Filiation a JOIN a.patient p where p.reference = :patRef")
    List<Filiation> getFiliationByPatientReference(@Param("patRef") long patient_ref);
}