package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.Indication;
import com.dopediatrie.hosman.bm.payload.request.NameRequest;
import com.dopediatrie.hosman.bm.payload.response.NameResponse;

import java.util.List;

public interface IndicationService {
    List<Indication> getAllIndications();

    long addIndication(NameRequest nameRequest);

    void addIndication(List<NameRequest> nameRequests);

    NameResponse getIndicationById(long nameId);

    void editIndication(NameRequest nameRequest, long nameId);

    public void deleteIndicationById(long nameId);

    List<String> getIndicationLike(String q);
}
