package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Etat;
import com.dopediatrie.hosman.secretariat.payload.request.EtatRequest;
import com.dopediatrie.hosman.secretariat.payload.response.EtatResponse;

import java.util.List;

public interface EtatService {
    List<Etat> getAllEtats();

    long addEtat(EtatRequest etatRequest);

    void addEtat(List<EtatRequest> etatRequests);

    EtatResponse getEtatById(long etatId);

    void editEtat(EtatRequest etatRequest, long etatId);

    public void deleteEtatById(long etatId);
}
