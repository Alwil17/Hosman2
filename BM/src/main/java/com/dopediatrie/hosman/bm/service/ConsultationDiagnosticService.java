package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.ConsultationDiagnostic;
import com.dopediatrie.hosman.bm.payload.request.ConsultationDiagnosticRequest;
import com.dopediatrie.hosman.bm.payload.response.ConsultationDiagnosticResponse;

import java.util.List;

public interface ConsultationDiagnosticService {
    List<ConsultationDiagnostic> getAllConsultationDiagnostics();

    long addConsultationDiagnostic(ConsultationDiagnosticRequest consultationDiagnosticRequest);

    ConsultationDiagnosticResponse getConsultationDiagnosticById(long interventionDiagnosticId);

    void editConsultationDiagnostic(ConsultationDiagnosticRequest consultationDiagnosticRequest, long interventionDiagnosticId);

    public void deleteConsultationDiagnosticById(long interventionDiagnosticId);

    void deleteAllForConsultationId(long consultationId);
}
