package com.dopediatrie.hosman.hospi.service;

import com.dopediatrie.hosman.hospi.entity.Scam;
import com.dopediatrie.hosman.hospi.payload.request.ScamRequest;
import com.dopediatrie.hosman.hospi.payload.response.ScamResponse;

import java.util.List;

public interface ScamService {
    List<Scam> getAllScams();

    long addScam(ScamRequest medExterneRequest);

    void addScam(List<ScamRequest> medExterneRequests);

    ScamResponse getScamById(long medExterneId);

    List<ScamResponse> getScamByHospitId(long hospitId);

    void editScam(ScamRequest medExterneRequest, long medExterneId);

    public void deleteScamById(long medExterneId);
}
