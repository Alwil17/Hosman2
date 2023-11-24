package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.CreanceMode;
import com.dopediatrie.hosman.secretariat.entity.CreanceModePK;
import com.dopediatrie.hosman.secretariat.payload.request.CreanceModeRequest;
import com.dopediatrie.hosman.secretariat.payload.response.CreanceModeResponse;

import java.util.List;

public interface CreanceModeService {
    List<CreanceMode> getAllCreanceModes();

    CreanceModePK addCreanceMode(CreanceModeRequest creanceModeRequest);

    CreanceModeResponse getCreanceModeById(long creanceModeId);

    void editCreanceMode(CreanceModeRequest creanceModeRequest, long creanceModeId);

    public void deleteCreanceModeById(long creanceModeId);
}
