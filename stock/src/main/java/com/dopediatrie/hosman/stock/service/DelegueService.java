package com.dopediatrie.hosman.stock.service;

import com.dopediatrie.hosman.stock.entity.Delegue;
import com.dopediatrie.hosman.stock.payload.request.DelegueRequest;
import com.dopediatrie.hosman.stock.payload.response.DelegueResponse;

import java.util.List;

public interface DelegueService {
    List<Delegue> getAllDelegues();

    long addDelegue(DelegueRequest delegueRequest);

    void addDelegue(List<DelegueRequest> delegueRequests);

    DelegueResponse getDelegueById(long delegueId);

    void editDelegue(DelegueRequest delegueRequest, long delegueId);

    public void deleteDelegueById(long delegueId);
}
