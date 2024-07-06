package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.Refused;
import com.dopediatrie.hosman.bm.payload.request.RefusedRequest;
import com.dopediatrie.hosman.bm.payload.response.RefusedResponse;

import java.util.List;

public interface RefusedService {
    List<Refused> getAllRefuseds();

    long addRefused(RefusedRequest refusedRequest);

    void addRefused(List<RefusedRequest> refusedRequests);

    RefusedResponse getRefusedById(long refusedId);

    RefusedResponse getRefusedByConsultationId(long consultationId);

    void editRefused(RefusedRequest refusedRequest, long refusedId);

    public void deleteRefusedById(long refusedId);
}
