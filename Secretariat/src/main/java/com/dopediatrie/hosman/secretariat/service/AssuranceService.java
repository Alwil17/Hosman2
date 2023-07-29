package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Assurance;
import com.dopediatrie.hosman.secretariat.payload.request.AssuranceRequest;
import com.dopediatrie.hosman.secretariat.payload.response.AssuranceResponse;

import java.util.List;

public interface AssuranceService {
    List<Assurance> getAllAssurances();

    long addAssurance(AssuranceRequest assuranceRequest);

    AssuranceResponse getAssuranceById(long assuranceId);

    void editAssurance(AssuranceRequest assuranceRequest, long assuranceId);

    public void deleteAssuranceById(long assuranceId);
}
