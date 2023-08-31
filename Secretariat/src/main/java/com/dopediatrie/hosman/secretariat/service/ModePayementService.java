package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.ModePayement;
import com.dopediatrie.hosman.secretariat.payload.request.NameRequest;
import com.dopediatrie.hosman.secretariat.payload.response.NameResponse;

import java.util.List;

public interface ModePayementService {
    List<ModePayement> getAllModePayements();

    long addModePayement(NameRequest villeRequest);

    NameResponse getModePayementById(long villeId);

    void editModePayement(NameRequest villeRequest, long villeId);

    public void deleteModePayementById(long villeId);
}
