package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.ContreIndication;
import com.dopediatrie.hosman.bm.payload.request.NameRequest;
import com.dopediatrie.hosman.bm.payload.response.NameResponse;

import java.util.List;

public interface ContreIndicationService {
    List<ContreIndication> getAllContreIndications();

    long addContreIndication(NameRequest nameRequest);

    void addContreIndication(List<NameRequest> nameRequests);

    NameResponse getContreIndicationById(long nameId);

    void editContreIndication(NameRequest nameRequest, long nameId);

    public void deleteContreIndicationById(long nameId);

    List<String> getContreIndicationLike(String q);
}
