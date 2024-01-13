package com.dopediatrie.hosman.hospi.service;

import com.dopediatrie.hosman.hospi.entity.Chirurgie;
import com.dopediatrie.hosman.hospi.payload.request.ChirurgieRequest;
import com.dopediatrie.hosman.hospi.payload.response.ChirurgieResponse;

import java.util.List;

public interface ChirurgieService {
    List<Chirurgie> getAllChirurgies();

    long addChirurgie(ChirurgieRequest medExterneRequest);

    void addChirurgie(List<ChirurgieRequest> medExterneRequests);

    ChirurgieResponse getChirurgieById(long medExterneId);

    List<ChirurgieResponse> getChirurgieByHospitId(long hospitId);

    void editChirurgie(ChirurgieRequest medExterneRequest, long medExterneId);

    public void deleteChirurgieById(long medExterneId);
}
