package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.payload.response.PatientResponse;

import java.util.List;

public interface PatientService {
    List<PatientResponse> getAllPatients();

    PatientResponse getPatientById(long patientId);

    PatientResponse getPatientByRef(String patientRef);

    public void deletePatientById(long patientId);
}
