package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.payload.request.ConsultationDiagnosticRequest;
import com.dopediatrie.hosman.bm.payload.response.ConsultationDiagnosticResponse;
import com.dopediatrie.hosman.bm.payload.response.DiagnosticResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface DiagnosticService {

    List<DiagnosticResponse> getDiagnosticByLibelle(String libelle);

    DiagnosticResponse getDiagnosticByCode(String diagnostic);

    String getToken();

    List<ConsultationDiagnosticResponse> getDiagnosticCountByDateRange(LocalDateTime datemin, LocalDateTime datemax);
}
