package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Majoration;
import com.dopediatrie.hosman.secretariat.payload.request.MajorationRequest;
import com.dopediatrie.hosman.secretariat.payload.response.MajorationResponse;

import java.util.List;

public interface MajorationService {
    List<Majoration> getAllMajorations();

    long addMajoration(MajorationRequest majorationRequest);

    MajorationResponse getMajorationById(long majorationId);

    void editMajoration(MajorationRequest majorationRequest, long majorationId);

    public void deleteMajorationById(long majorationId);
}
