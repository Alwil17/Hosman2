package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Antecedant;
import com.dopediatrie.hosman.secretariat.payload.request.AntecedantRequest;
import com.dopediatrie.hosman.secretariat.payload.response.AntecedantResponse;

import java.util.List;

public interface AntecedantService {
    List<AntecedantResponse> getAllAntecedants();

    long addAntecedant(AntecedantRequest filiationRequest);

    AntecedantResponse getAntecedantById(long filiationId);

    void editAntecedant(AntecedantRequest filiationRequest, long filiationId);

    public void deleteAntecedantById(long filiationId);
}
