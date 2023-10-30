package com.dopediatrie.hosman.stock.service;

import com.dopediatrie.hosman.stock.entity.Posologie;
import com.dopediatrie.hosman.stock.payload.request.PosologieRequest;
import com.dopediatrie.hosman.stock.payload.response.PosologieResponse;

import java.util.List;

public interface PosologieService {
    List<Posologie> getAllPosologies();

    long addPosologie(PosologieRequest posologieRequest);

    void addPosologie(List<PosologieRequest> posologieRequests);

    PosologieResponse getPosologieById(long posologieId);

    void editPosologie(PosologieRequest posologieRequest, long posologieId);

    public void deletePosologieById(long posologieId);
}
