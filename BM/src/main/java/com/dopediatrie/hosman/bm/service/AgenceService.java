package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.Agence;
import com.dopediatrie.hosman.bm.payload.request.AgenceRequest;
import com.dopediatrie.hosman.bm.payload.response.AgenceResponse;

import java.util.List;

public interface AgenceService {
    List<Agence> getAllAgences();

    long addAgence(AgenceRequest agenceRequest);

    void addAgence(List<AgenceRequest> agenceRequests);

    AgenceResponse getAgenceById(long agenceId);

    void editAgence(AgenceRequest agenceRequest, long agenceId);

    public void deleteAgenceById(long agenceId);

    List<Agence> getAgenceByQueryString(String q);
}
