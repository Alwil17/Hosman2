package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Creance;
import com.dopediatrie.hosman.secretariat.payload.request.CreanceRequest;
import com.dopediatrie.hosman.secretariat.payload.response.CreanceResponse;

import java.util.List;

public interface CreanceService {
    List<Creance> getAllCreances();

    long addCreance(CreanceRequest creanceRequest);

    CreanceResponse getCreanceById(long creanceId);

    void editCreance(CreanceRequest creanceRequest, long creanceId);

    public void deleteCreanceById(long creanceId);
}
