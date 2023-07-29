package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.InterventionTarif;
import com.dopediatrie.hosman.secretariat.payload.request.InterventionTarifRequest;
import com.dopediatrie.hosman.secretariat.payload.response.InterventionTarifResponse;

import java.util.List;

public interface InterventionTarifService {
    List<InterventionTarif> getAllInterventionTarifs();

    long addInterventionTarif(InterventionTarifRequest interventionTarifRequest);

    InterventionTarifResponse getInterventionTarifById(long interventionTarifId);

    void editInterventionTarif(InterventionTarifRequest interventionTarifRequest, long interventionTarifId);

    public void deleteInterventionTarifById(long interventionTarifId);
}
