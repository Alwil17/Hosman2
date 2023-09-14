package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.SousActe;
import com.dopediatrie.hosman.secretariat.payload.request.SousActeRequest;
import com.dopediatrie.hosman.secretariat.payload.response.SousActeResponse;

import java.util.List;

public interface SousActeService {
    List<SousActe> getAllSousActes();

    long addSousActe(SousActeRequest sousActeRequest);

    void addSousActe(List<SousActeRequest> sousActeRequests);

    SousActeResponse getSousActeById(long sousActeId);

    void editSousActe(SousActeRequest sousActeRequest, long sousActeId);

    public void deleteSousActeById(long sousActeId);
}
