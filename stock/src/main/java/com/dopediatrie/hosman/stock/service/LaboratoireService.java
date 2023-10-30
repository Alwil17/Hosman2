package com.dopediatrie.hosman.stock.service;

import com.dopediatrie.hosman.stock.entity.Laboratoire;
import com.dopediatrie.hosman.stock.payload.request.LaboratoireRequest;
import com.dopediatrie.hosman.stock.payload.response.LaboratoireResponse;

import java.util.List;

public interface LaboratoireService {
    List<Laboratoire> getAllLaboratoires();

    long addLaboratoire(LaboratoireRequest laboratoireRequest);

    void addLaboratoire(List<LaboratoireRequest> laboratoireRequests);

    LaboratoireResponse getLaboratoireById(long laboratoireId);

    void editLaboratoire(LaboratoireRequest laboratoireRequest, long laboratoireId);

    public void deleteLaboratoireById(long laboratoireId);
}
