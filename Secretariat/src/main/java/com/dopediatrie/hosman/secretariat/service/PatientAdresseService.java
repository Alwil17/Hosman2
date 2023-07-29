package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.PatientAdresse;
import com.dopediatrie.hosman.secretariat.payload.request.PatientAdresseRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PatientAdresseResponse;

import java.util.List;

public interface PatientAdresseService {
    List<PatientAdresse> getAllPatientAdresses();

    long addPatientAdresse(PatientAdresseRequest patientAdresseRequest);

    PatientAdresseResponse getPatientAdresseById(long patientAdresseId);

    void editPatientAdresse(PatientAdresseRequest patientAdresseRequest, long patientAdresseId);

    public void deletePatientAdresseById(long patientAdresseId);
}
