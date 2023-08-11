package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.PatientAssurance;
import com.dopediatrie.hosman.secretariat.entity.PatientAssurancePK;
import com.dopediatrie.hosman.secretariat.payload.request.PatientAssuranceRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PatientAssuranceResponse;

import java.util.List;

public interface PatientAssuranceService {
    List<PatientAssurance> getAllPatientAssurances();

    PatientAssurancePK addPatientAssurance(PatientAssuranceRequest patientAssuranceRequest);

    PatientAssuranceResponse getPatientAssuranceById(long patientAssuranceId);

    void editPatientAssurance(PatientAssuranceRequest patientAssuranceRequest, long patientAssuranceId);

    public void deletePatientAssuranceById(long patientAssuranceId);
}
