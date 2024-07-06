package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.Deceded;
import com.dopediatrie.hosman.bm.payload.request.DecededRequest;
import com.dopediatrie.hosman.bm.payload.response.DecededResponse;

import java.util.List;

public interface DecededService {
    List<Deceded> getAllDecededs();

    long addDeceded(DecededRequest medExterneRequest);

    void addDeceded(List<DecededRequest> medExterneRequests);

    DecededResponse getDecededById(long medExterneId);

    DecededResponse getDecededByConsultationId(long hospitId);

    void editDeceded(DecededRequest medExterneRequest, long medExterneId);

    public void deleteDecededById(long medExterneId);
}
