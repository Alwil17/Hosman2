package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Filiation;
import com.dopediatrie.hosman.secretariat.payload.request.FiliationRequest;
import com.dopediatrie.hosman.secretariat.payload.response.FiliationResponse;

import java.util.List;

public interface FiliationService {
    List<Filiation> getAllFiliations();

    long addFiliation(FiliationRequest filiationRequest);

    FiliationResponse getFiliationById(long filiationId);

    void editFiliation(FiliationRequest filiationRequest, long filiationId);

    public void deleteFiliationById(long filiationId);
}
