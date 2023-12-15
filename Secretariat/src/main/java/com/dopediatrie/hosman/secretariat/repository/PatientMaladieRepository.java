package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.PatientMaladie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PatientMaladieRepository extends JpaRepository<PatientMaladie,Long> {

    @Query("SELECT em from PatientMaladie em where em.patient.id = :patientId and em.maladie.id = :maladieId")
    Optional<PatientMaladie> findByEncaissement_IdAndMode_payement_Id(@Param("patientId") long patient_id, @Param("maladieId") long maladie_id);

    @Query("SELECT case when count(em)>0 then true else false end from PatientMaladie em where em.patient.id = :patientId and em.maladie.id = :maladieId")
    Boolean existsByEncaissement_IdAndMode_payement_Id(@Param("patientId") long patient_id, @Param("maladieId") long maladie_id);

    @Query("SELECT em from PatientMaladie em where em.patient.id = :patientId")
    List<PatientMaladie> findByEncaissement_Id(@Param("patientId") long patient_id);

    @Modifying
    @Query("DELETE FROM PatientMaladie em WHERE em.patient.id = :patientId")
    void deleteByEncaissementId(@Param("patientId") long patientId);

}