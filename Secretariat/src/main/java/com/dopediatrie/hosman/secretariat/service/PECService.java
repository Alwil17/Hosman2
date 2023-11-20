package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.PEC;
import com.dopediatrie.hosman.secretariat.payload.request.PECRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PECResponse;

import java.util.List;

public interface PECService {
    List<PEC> getAllPECs();

    long addPEC(PECRequest pecRequest);

    void addPEC(List<PECRequest> pecRequests);

    PECResponse getPECById(long pecId);

    void editPEC(PECRequest pecRequest, long pecId);

    public void deletePECById(long pecId);
}
