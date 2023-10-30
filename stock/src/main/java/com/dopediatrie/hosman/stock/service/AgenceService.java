package com.dopediatrie.hosman.stock.service;

import com.dopediatrie.hosman.stock.entity.Agence;
import com.dopediatrie.hosman.stock.payload.request.AgenceRequest;
import com.dopediatrie.hosman.stock.payload.response.AgenceResponse;

import java.util.List;

public interface AgenceService {
    List<Agence> getAllAgences();

    long addAgence(AgenceRequest agenceRequest);

    void addAgence(List<AgenceRequest> agenceRequests);

    AgenceResponse getAgenceById(long agenceId);

    void editAgence(AgenceRequest agenceRequest, long agenceId);

    public void deleteAgenceById(long agenceId);
}
