package com.dopediatrie.hosman.stock.service;

import com.dopediatrie.hosman.stock.entity.Indication;
import com.dopediatrie.hosman.stock.payload.request.NameRequest;
import com.dopediatrie.hosman.stock.payload.response.NameResponse;

import java.util.List;

public interface IndicationService {
    List<Indication> getAllIndications();

    long addIndication(NameRequest nameRequest);

    void addIndication(List<NameRequest> nameRequests);

    NameResponse getIndicationById(long nameId);

    void editIndication(NameRequest nameRequest, long nameId);

    public void deleteIndicationById(long nameId);
}
