package com.dopediatrie.hosman.hospi.service;

import com.dopediatrie.hosman.hospi.entity.Deceded;
import com.dopediatrie.hosman.hospi.payload.request.DecededRequest;
import com.dopediatrie.hosman.hospi.payload.response.DecededResponse;

import java.util.List;

public interface DecededService {
    List<Deceded> getAllDecededs();

    long addDeceded(DecededRequest medExterneRequest);

    void addDeceded(List<DecededRequest> medExterneRequests);

    DecededResponse getDecededById(long medExterneId);

    List<DecededResponse> getDecededByHospitId(long hospitId);

    void editDeceded(DecededRequest medExterneRequest, long medExterneId);

    public void deleteDecededById(long medExterneId);
}
