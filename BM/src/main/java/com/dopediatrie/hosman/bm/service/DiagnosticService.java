package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.payload.response.DiagnosticResponse;

import java.util.List;

public interface DiagnosticService {

    List<DiagnosticResponse> getDiagnosticByLibelle(String libelle);

    DiagnosticResponse getDiagnosticByCode(String diagnostic);
}
