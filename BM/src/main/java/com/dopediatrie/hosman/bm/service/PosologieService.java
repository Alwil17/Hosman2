package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.Posologie;
import com.dopediatrie.hosman.bm.payload.request.PosologieRequest;
import com.dopediatrie.hosman.bm.payload.response.PosologieResponse;

import java.util.List;

public interface PosologieService {
    List<Posologie> getAllPosologies();

    long addPosologie(PosologieRequest posologieRequest);

    void addPosologie(List<PosologieRequest> posologieRequests);

    PosologieResponse getPosologieById(long posologieId);

    void editPosologie(PosologieRequest posologieRequest, long posologieId);

    public void deletePosologieById(long posologieId);

    List<Posologie> getPosologiesByTypeAndQueryString(String type, String q);

    List<Posologie> getPosologiesByType(String type);
}
