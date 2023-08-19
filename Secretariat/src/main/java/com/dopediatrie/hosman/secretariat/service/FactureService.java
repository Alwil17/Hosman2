package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Facture;
import com.dopediatrie.hosman.secretariat.payload.request.FactureRequest;
import com.dopediatrie.hosman.secretariat.payload.response.FactureResponse;

import java.util.List;

public interface FactureService {
    List<Facture> getAllFactures();

    long addFacture(FactureRequest patientRequest);

    FactureResponse getFactureById(long patientId);

    void editFacture(FactureRequest patientRequest, long patientId);

    public void deleteFactureById(long patientId);
}
