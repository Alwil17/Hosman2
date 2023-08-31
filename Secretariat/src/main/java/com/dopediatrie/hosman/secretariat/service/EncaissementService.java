package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Encaissement;
import com.dopediatrie.hosman.secretariat.payload.request.EncaissementRequest;
import com.dopediatrie.hosman.secretariat.payload.response.EncaissementResponse;

import java.util.List;

public interface EncaissementService {
    List<Encaissement> getAllEncaissements();

    long addEncaissement(EncaissementRequest assuranceRequest);

    EncaissementResponse getEncaissementById(long assuranceId);

    void editEncaissement(EncaissementRequest assuranceRequest, long assuranceId);

    public void deleteEncaissementById(long assuranceId);
}
