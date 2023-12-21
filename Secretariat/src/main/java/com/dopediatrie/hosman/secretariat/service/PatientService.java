package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Patient;
import com.dopediatrie.hosman.secretariat.payload.request.PatientRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PatientResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface PatientService {
    List<Patient> getAllPatients();

    long addPatient(PatientRequest patientRequest);

    PatientResponse getPatientById(long patientId);

    List<Patient> getPatientByNomAndPrenoms(String nom);

    List<Patient> getPatientByReference(String reference);

    PatientResponse getPatientByReferenceUnique(String reference);

    List<Patient> getPatientByPrenoms(String prenoms);

    void editPatient(PatientRequest patientRequest, long patientId);

    void editPatientCaracs(PatientRequest patientRequest, long patientId);

    public void deletePatientById(long patientId);

    List<Patient> getPatientByDateNaissance(LocalDateTime dateNaissance, LocalDateTime dateNaissanceLimit);

    List<Patient> getPatientByDateEntree(LocalDateTime dateEntree, LocalDateTime dateEntreeLimit);
}
