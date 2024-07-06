package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.Ordonnance;
import com.dopediatrie.hosman.bm.payload.request.OrdonnanceRequest;
import com.dopediatrie.hosman.bm.payload.response.OrdonnanceResponse;

import java.util.List;

public interface OrdonnanceService {
    List<Ordonnance> getAllOrdonnances();

    long addOrdonnance(OrdonnanceRequest ordonnanceRequest);

    OrdonnanceResponse getOrdonnanceById(long interventionId);

    OrdonnanceResponse getOrdonnanceByConsultationRef(String consultationRef);

    OrdonnanceResponse getOrdonnanceByConsultationId(long consultationId);

    void editOrdonnance(OrdonnanceRequest ordonnanceRequest, long interventionId);

    public void deleteOrdonnanceById(long interventionId);

    OrdonnanceResponse getOrdonnanceByRef(String ordonnanceRef);

    List<String> getOrdonnanceDiagnostics(String queryString);

    List<Ordonnance> getOrdonnancesForDiagnostic(String diagnostic);

    List<Ordonnance> getOrdonnanceByPatientRef(String patient_ref);
}
