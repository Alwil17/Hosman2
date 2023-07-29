package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Pays;
import com.dopediatrie.hosman.secretariat.payload.request.PaysRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PaysResponse;

import java.util.List;

public interface PaysService {
    List<Pays> getAllPays();

    long addPays(PaysRequest patientRequest);

    PaysResponse getPaysById(long patientId);

    void editPays(PaysRequest patientRequest, long patientId);

    public void deletePaysById(long patientId);
}
