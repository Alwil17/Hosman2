package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.Constante;
import com.dopediatrie.hosman.bm.payload.request.ConstanteRequest;
import com.dopediatrie.hosman.bm.payload.response.ConstanteResponse;

import java.util.List;

public interface ConstanteService {
    List<Constante> getAllConstantes();

    long addConstante(ConstanteRequest constanteRequest);

    void addConstante(List<ConstanteRequest> constanteRequests);

    ConstanteResponse getConstanteById(long constanteId);

    void editConstante(ConstanteRequest constanteRequest, long constanteId);

    public void deleteConstanteById(long constanteId);
}
