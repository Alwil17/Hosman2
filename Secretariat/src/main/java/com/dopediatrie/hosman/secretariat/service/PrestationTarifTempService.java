package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.PrestationTarifPK;
import com.dopediatrie.hosman.secretariat.entity.PrestationTarifTemp;
import com.dopediatrie.hosman.secretariat.entity.PrestationTarifTempPK;
import com.dopediatrie.hosman.secretariat.payload.request.PrestationTarifTempRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PrestationTarifTempResponse;

import java.util.List;

public interface PrestationTarifTempService {
    List<PrestationTarifTemp> getAllPrestationTarifTemps();

    PrestationTarifTempPK addPrestationTarifTemp(PrestationTarifTempRequest prestationTarifTempRequest);

    PrestationTarifTempResponse getPrestationTarifTempById(long prestationTarifTempId);

    void editPrestationTarifTemp(PrestationTarifTempRequest prestationTarifTempRequest, long prestationTarifTempId);

    public void deletePrestationTarifTempById(long prestationTarifId);

    public void deletePrestationTarifTempById(PrestationTarifPK prestationTarifId);
}
