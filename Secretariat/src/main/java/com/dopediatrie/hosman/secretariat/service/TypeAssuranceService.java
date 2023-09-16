package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.TypeAssurance;
import com.dopediatrie.hosman.secretariat.payload.request.NameRequest;
import com.dopediatrie.hosman.secretariat.payload.response.NameResponse;

import java.util.List;

public interface TypeAssuranceService {
    List<TypeAssurance> getAllTypeAssurances();

    long addTypeAssurance(NameRequest typeAssuranceRequest);

    void addTypeAssurance(List<NameRequest> typeAssuranceRequests);

    NameResponse getTypeAssuranceById(long typeAssuranceId);

    void editTypeAssurance(NameRequest typeAssuranceRequest, long typeAssuranceId);

    public void deleteTypeAssuranceById(long typeAssuranceId);
}
