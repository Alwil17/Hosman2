package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.PrestationTarif;
import com.dopediatrie.hosman.secretariat.entity.PrestationTarifPK;
import com.dopediatrie.hosman.secretariat.payload.request.PrestationTarifRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PrestationTarifResponse;

import java.util.List;

public interface PrestationTarifService {
    List<PrestationTarif> getAllPrestationTarifs();

    PrestationTarifPK addPrestationTarif(PrestationTarifRequest prestationTarifRequest);

    PrestationTarifResponse getPrestationTarifById(long prestationTarifId);

    void editPrestationTarif(PrestationTarifRequest prestationTarifRequest, long prestationTarifId);

    public void deletePrestationTarifById(long prestationTarifId);
}
