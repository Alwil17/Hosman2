package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.Laboratoire;
import com.dopediatrie.hosman.bm.payload.request.LaboratoireRequest;
import com.dopediatrie.hosman.bm.payload.response.LaboratoireResponse;

import java.util.List;

public interface LaboratoireService {
    List<Laboratoire> getAllLaboratoires();

    long addLaboratoire(LaboratoireRequest laboratoireRequest);

    void addLaboratoire(List<LaboratoireRequest> laboratoireRequests);

    LaboratoireResponse getLaboratoireById(long laboratoireId);

    void editLaboratoire(LaboratoireRequest laboratoireRequest, long laboratoireId);

    public void deleteLaboratoireById(long laboratoireId);

    List<Laboratoire> getLaboratoireByAgenceIdAndQ(long agenceId, String q);

    List<Laboratoire> getLaboratoireByAgenceId(long agenceId);

    List<Laboratoire> getLaboratoireByQueryString(String q);
}
