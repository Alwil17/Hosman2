package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Prestation;
import com.dopediatrie.hosman.secretariat.payload.request.InterventionRequest;
import com.dopediatrie.hosman.secretariat.payload.response.InterventionResponse;

import java.util.List;

public interface InterventionService {
    List<Prestation> getAllInterventions();

    long addIntervention(InterventionRequest interventionRequest);

    InterventionResponse getInterventionById(long interventionId);

    void editIntervention(InterventionRequest interventionRequest, long interventionId);

    public void deleteInterventionById(long interventionId);
}
