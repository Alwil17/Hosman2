package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.PatientMaladie;
import com.dopediatrie.hosman.secretariat.entity.PatientMaladiePK;
import com.dopediatrie.hosman.secretariat.payload.request.PatientMaladieRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PatientMaladieResponse;

import java.util.List;

public interface PatientMaladieService {
    List<PatientMaladie> getAllPatientMaladies();

    PatientMaladiePK addPatientMaladie(PatientMaladieRequest maladieRequest);

    void addPatientMaladie(List<PatientMaladieRequest> maladieRequests);

    PatientMaladieResponse getPatientMaladieById(long maladieId);

    void editPatientMaladie(PatientMaladieRequest maladieRequest, long maladieId);

    public void deletePatientMaladieById(long maladieId);

    void deleteAllForPatientId(long patientId);
}
