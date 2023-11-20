package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.InterventionDiagnostic;
import com.dopediatrie.hosman.bm.entity.InterventionDiagnosticPK;
import com.dopediatrie.hosman.bm.payload.request.InterventionDiagnosticRequest;
import com.dopediatrie.hosman.bm.payload.response.InterventionDiagnosticResponse;

import java.util.List;

public interface InterventionDiagnosticService {
    List<InterventionDiagnostic> getAllInterventionDiagnostics();

    InterventionDiagnosticPK addInterventionDiagnostic(InterventionDiagnosticRequest interventionDiagnosticRequest);

    InterventionDiagnosticResponse getInterventionDiagnosticById(long interventionDiagnosticId);

    void editInterventionDiagnostic(InterventionDiagnosticRequest interventionDiagnosticRequest, long interventionDiagnosticId);

    public void deleteInterventionDiagnosticById(long interventionDiagnosticId);
}
