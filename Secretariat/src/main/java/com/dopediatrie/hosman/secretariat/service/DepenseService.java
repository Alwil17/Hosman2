package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Depense;
import com.dopediatrie.hosman.secretariat.payload.request.DepenseRequest;
import com.dopediatrie.hosman.secretariat.payload.response.DepenseResponse;

import java.util.List;

public interface DepenseService {
    List<Depense> getAllDepenses();

    long addDepense(DepenseRequest quartierRequest);

    DepenseResponse getDepenseById(long quartierId);

    void editDepense(DepenseRequest quartierRequest, long quartierId);

    public void deleteDepenseById(long quartierId);
}
