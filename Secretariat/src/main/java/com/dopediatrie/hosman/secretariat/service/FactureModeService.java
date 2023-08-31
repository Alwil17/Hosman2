package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.FactureMode;
import com.dopediatrie.hosman.secretariat.entity.FactureModePK;
import com.dopediatrie.hosman.secretariat.payload.request.FactureModeRequest;
import com.dopediatrie.hosman.secretariat.payload.response.FactureModeResponse;

import java.util.List;

public interface FactureModeService {
    List<FactureMode> getAllFactureModes();

    FactureModePK addFactureMode(FactureModeRequest factureModeRequest);

    FactureModeResponse getFactureModeById(long factureModeId);

    void editFactureMode(FactureModeRequest factureModeRequest, long factureModeId);

    public void deleteFactureModeById(long factureModeId);
}
