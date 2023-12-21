package com.dopediatrie.hosman.hospi.service;

import com.dopediatrie.hosman.hospi.payload.response.PatientResponse;

import java.util.List;

public interface PatientService {
    List<PatientResponse> getAllPatients();

    PatientResponse getPatientById(long patientId);

    public void deletePatientById(long patientId);
}
