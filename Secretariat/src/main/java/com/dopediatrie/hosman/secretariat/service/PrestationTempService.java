package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.PrestationTemp;
import com.dopediatrie.hosman.secretariat.payload.request.PrestationTempRequest;
import com.dopediatrie.hosman.secretariat.payload.response.FactureResponse;
import com.dopediatrie.hosman.secretariat.payload.response.PrestationTempResponse;

import java.util.List;

public interface PrestationTempService {
    List<PrestationTemp> getAllPrestationTemps();

    FactureResponse addPrestationTemp(PrestationTempRequest patientRequest);

    PrestationTempResponse getPrestationTempById(long patientId);

    void editPrestationTemp(PrestationTempRequest patientRequest, long patientId);

    public void deletePrestationTempById(long patientId);
}
