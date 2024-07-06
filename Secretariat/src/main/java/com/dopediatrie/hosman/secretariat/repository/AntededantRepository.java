package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Antecedant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AntededantRepository extends JpaRepository<Antecedant,Long> {

    @Query("select case when count(a)>0 then true else false end from Antecedant a JOIN a.patient p where p.id = :patId and a.type = :type")
    boolean existsByAntecedantByPatientIdAndType(@Param("patId") long patient_id, @Param("type") String type);

    @Query("select a from Antecedant a JOIN a.patient p where p.id = :patId and a.type = :type")
    Optional<Antecedant> findByAntecedantByPatientIdAndType(@Param("patId") long patient_id, @Param("type") String type);

    @Query("select case when count(a)>0 then true else false end from Antecedant a JOIN a.patient p where p.id = :patId")
    boolean existsByPatientId(@Param("patId") long patient_id);

    @Query("select a from Antecedant a JOIN a.patient p where p.id = :patId")
    Optional<Antecedant> findByPatientId(@Param("patId") long patient_id);

    @Query("select a from Antecedant a JOIN a.patient p where p.reference = :patRef")
    Optional<Antecedant> getAntecedantByPatientReference(@Param("patRef") long patient_ref);
}