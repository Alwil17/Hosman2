package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.Delegue;
import com.dopediatrie.hosman.bm.payload.request.DelegueRequest;
import com.dopediatrie.hosman.bm.payload.response.DelegueResponse;

import java.util.List;

public interface DelegueService {
    List<Delegue> getAllDelegues();

    long addDelegue(DelegueRequest delegueRequest);

    void addDelegue(List<DelegueRequest> delegueRequests);

    DelegueResponse getDelegueById(long delegueId);

    void editDelegue(DelegueRequest delegueRequest, long delegueId);

    public void deleteDelegueById(long delegueId);

    List<Delegue> getDelegueByAgenceId(long agenceId);

    List<Delegue> getDelegueByAgenceIdAndQueryString(long agenceId, String q);

    List<Delegue> getDelegueByLaboIdAndQueryString(long laboId, String q);

    List<Delegue> getDelegueByLaboId(long laboId);

    List<Delegue> getDelegueByQueryString(String q);
}
