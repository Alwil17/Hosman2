package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Prestation;
import com.dopediatrie.hosman.secretariat.payload.request.PrestationRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PrestationResponse;

import java.util.List;

public interface PrestationService {
    List<Prestation> getAllPrestations();

    long addPrestation(PrestationRequest patientRequest);

    PrestationResponse getPrestationById(long patientId);

    void editPrestation(PrestationRequest patientRequest, long patientId);

    public void deletePrestationById(long patientId);
}
