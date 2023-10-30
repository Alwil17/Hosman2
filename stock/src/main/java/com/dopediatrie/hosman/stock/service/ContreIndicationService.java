package com.dopediatrie.hosman.stock.service;

import com.dopediatrie.hosman.stock.entity.ContreIndication;
import com.dopediatrie.hosman.stock.payload.request.NameRequest;
import com.dopediatrie.hosman.stock.payload.response.NameResponse;

import java.util.List;

public interface ContreIndicationService {
    List<ContreIndication> getAllContreIndications();

    long addContreIndication(NameRequest nameRequest);

    void addContreIndication(List<NameRequest> nameRequests);

    NameResponse getContreIndicationById(long nameId);

    void editContreIndication(NameRequest nameRequest, long nameId);

    public void deleteContreIndicationById(long nameId);
}
