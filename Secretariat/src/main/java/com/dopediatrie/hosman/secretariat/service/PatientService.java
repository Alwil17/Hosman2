package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Patient;
import com.dopediatrie.hosman.secretariat.payload.request.PatientRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PatientResponse;

import java.util.List;

public interface PatientService {
    List<Patient> getAllPatients();

    long addPatient(PatientRequest patientRequest);

    PatientResponse getPatientById(long patientId);

    void editPatient(PatientRequest patientRequest, long patientId);

    public void deletePatientById(long patientId);
}
