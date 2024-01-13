package com.dopediatrie.hosman.hospi.service;

import com.dopediatrie.hosman.hospi.entity.SortieDiagnostic;
import com.dopediatrie.hosman.hospi.payload.request.SortieDiagnosticRequest;
import com.dopediatrie.hosman.hospi.payload.response.SortieDiagnosticResponse;

import java.util.List;

public interface SortieDiagnosticService {
    List<SortieDiagnostic> getAllSortieDiagnostics();

    long addSortieDiagnostic(SortieDiagnosticRequest medExterneRequest);

    void addSortieDiagnostic(List<SortieDiagnosticRequest> medExterneRequests);

    SortieDiagnosticResponse getSortieDiagnosticById(long medExterneId);

    void editSortieDiagnostic(SortieDiagnosticRequest medExterneRequest, long medExterneId);

    public void deleteSortieDiagnosticById(long medExterneId);

    void deleteAllForSortieId(long id);
}
