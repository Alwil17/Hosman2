package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.Intervention;
import com.dopediatrie.hosman.bm.payload.request.InterventionRequest;
import com.dopediatrie.hosman.bm.payload.response.InterventionResponse;

import java.util.List;

public interface InterventionService {
    List<Intervention> getAllInterventions();

    long addIntervention(InterventionRequest interventionRequest);

    InterventionResponse getInterventionById(long interventionId);

    void editIntervention(InterventionRequest interventionRequest, long interventionId);

    public void deleteInterventionById(long interventionId);
}
