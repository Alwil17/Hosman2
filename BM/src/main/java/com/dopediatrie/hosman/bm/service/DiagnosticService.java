package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.Diagnostic;
import com.dopediatrie.hosman.bm.payload.request.DiagnosticRequest;
import com.dopediatrie.hosman.bm.payload.response.DiagnosticResponse;

import java.util.List;

public interface DiagnosticService {
    List<Diagnostic> getAllDiagnostics();

    long addDiagnostic(DiagnosticRequest agenceRequest);

    void addDiagnostic(List<DiagnosticRequest> agenceRequests);

    DiagnosticResponse getDiagnosticById(long agenceId);

    void editDiagnostic(DiagnosticRequest agenceRequest, long agenceId);

    public void deleteDiagnosticById(long agenceId);
}
