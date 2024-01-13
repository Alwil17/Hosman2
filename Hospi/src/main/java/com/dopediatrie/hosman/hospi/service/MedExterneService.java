package com.dopediatrie.hosman.hospi.service;

import com.dopediatrie.hosman.hospi.entity.MedExterne;
import com.dopediatrie.hosman.hospi.payload.request.MedExterneRequest;
import com.dopediatrie.hosman.hospi.payload.response.MedExterneResponse;

import java.util.List;

public interface MedExterneService {
    List<MedExterne> getAllMedExternes();

    long addMedExterne(MedExterneRequest medExterneRequest);

    void addMedExterne(List<MedExterneRequest> medExterneRequests);

    MedExterneResponse getMedExterneById(long medExterneId);

    List<MedExterneResponse> getMedExterneByHospitId(long hospitId);

    void editMedExterne(MedExterneRequest medExterneRequest, long medExterneId);

    public void deleteMedExterneById(long medExterneId);
}
