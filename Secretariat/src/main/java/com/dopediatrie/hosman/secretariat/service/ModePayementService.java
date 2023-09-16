package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.ModePayement;
import com.dopediatrie.hosman.secretariat.payload.request.NameRequest;
import com.dopediatrie.hosman.secretariat.payload.response.NameResponse;

import java.util.List;

public interface ModePayementService {
    List<ModePayement> getAllModePayements();

    long addModePayement(NameRequest modePayementRequest);

    void addModePayement(List<NameRequest> modePayementRequests);

    NameResponse getModePayementById(long modePayementId);

    void editModePayement(NameRequest modePayementRequest, long modePayementId);

    public void deleteModePayementById(long modePayementId);
}
