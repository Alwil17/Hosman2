package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Acte;
import com.dopediatrie.hosman.secretariat.payload.request.ActeRequest;
import com.dopediatrie.hosman.secretariat.payload.response.ActeResponse;

import java.util.List;

public interface ActeService {
    List<Acte> getAllActes();

    long addActe(ActeRequest acteRequest);

    void addActe(List<ActeRequest> acteRequests);

    ActeResponse getActeById(long acteId);

    ActeResponse getActeByCode(String acteCode);

    void editActe(ActeRequest acteRequest, long acteId);

    public void deleteActeById(long acteId);
}
