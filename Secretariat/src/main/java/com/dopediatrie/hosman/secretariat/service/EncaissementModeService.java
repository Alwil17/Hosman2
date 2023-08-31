package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.EncaissementMode;
import com.dopediatrie.hosman.secretariat.entity.EncaissementModePK;
import com.dopediatrie.hosman.secretariat.payload.request.EncaissementModeRequest;
import com.dopediatrie.hosman.secretariat.payload.response.EncaissementModeResponse;

import java.util.List;

public interface EncaissementModeService {
    List<EncaissementMode> getAllEncaissementModes();

    EncaissementModePK addEncaissementMode(EncaissementModeRequest encaissementModeRequest);

    EncaissementModeResponse getEncaissementModeById(long encaissementModeId);

    void editEncaissementMode(EncaissementModeRequest encaissementModeRequest, long encaissementModeId);

    public void deleteEncaissementModeById(long encaissementModeId);
}
