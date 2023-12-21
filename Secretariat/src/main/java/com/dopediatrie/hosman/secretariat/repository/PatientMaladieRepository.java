package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.PatientMaladie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PatientMaladieRepository extends JpaRepository<PatientMaladie,Long> {

    @Query("SELECT em from PatientMaladie em where em.patient.id = :patientId and em.maladie.nom = :maladie")
    Optional<PatientMaladie> findByPatient_IdAndMaladie(@Param("patientId") long patient_id, @Param("maladie") String maladie);

    @Query("SELECT case when count(em)>0 then true else false end from PatientMaladie em where em.patient.id = :patientId and em.maladie.nom = :maladie")
    boolean existsByPatient_IdAndMaladie(@Param("patientId") long patient_id, @Param("maladie") String maladie);

    @Query("SELECT em from PatientMaladie em where em.patient.id = :patientId")
    List<PatientMaladie> findByPatient_Id(@Param("patientId") long patient_id);

    @Transactional
    @Modifying
    @Query("DELETE FROM PatientMaladie em WHERE em.patient.id = :patientId")
    void deleteByPatientId(@Param("patientId") long patientId);

}