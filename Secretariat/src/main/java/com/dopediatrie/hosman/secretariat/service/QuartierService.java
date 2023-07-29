package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Quartier;
import com.dopediatrie.hosman.secretariat.payload.request.NameRequest;
import com.dopediatrie.hosman.secretariat.payload.response.NameResponse;

import java.util.List;

public interface QuartierService {
    List<Quartier> getAllQuartiers();

    long addQuartier(NameRequest quartierRequest);

    NameResponse getQuartierById(long quartierId);

    void editQuartier(NameRequest quartierRequest, long quartierId);

    public void deleteQuartierById(long quartierId);
}
