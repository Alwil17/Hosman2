package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.Motif;
import com.dopediatrie.hosman.bm.payload.request.MotifRequest;
import com.dopediatrie.hosman.bm.payload.response.MotifResponse;

import java.util.List;

public interface MotifService {
    List<Motif> getAllMotifs();

    long addMotif(MotifRequest agenceRequest);

    void addMotif(List<MotifRequest> agenceRequests);

    MotifResponse getMotifById(long agenceId);

    List<Motif> getMotifByLibelle(String libelle);

    void editMotif(MotifRequest agenceRequest, long agenceId);

    public void deleteMotifById(long agenceId);
}
