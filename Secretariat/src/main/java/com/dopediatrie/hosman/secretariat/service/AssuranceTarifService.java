package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.AssuranceTarif;
import com.dopediatrie.hosman.secretariat.payload.request.AssuranceTarifRequest;
import com.dopediatrie.hosman.secretariat.payload.response.AssuranceTarifResponse;

import java.util.List;

public interface AssuranceTarifService {
    List<AssuranceTarif> getAllAssuranceTarifs();

    long addAssuranceTarif(AssuranceTarifRequest assuranceTarifRequest);

    AssuranceTarifResponse getAssuranceTarifById(long assuranceTarifId);

    void editAssuranceTarif(AssuranceTarifRequest assuranceTarifRequest, long assuranceTarifId);

    public void deleteAssuranceTarifById(long assuranceTarifId);
}
