package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Ville;
import com.dopediatrie.hosman.secretariat.payload.request.NameRequest;
import com.dopediatrie.hosman.secretariat.payload.response.NameResponse;

import java.util.List;

public interface VilleService {
    List<Ville> getAllVilles();

    long addVille(NameRequest villeRequest);

    void addVille(List<NameRequest> villeRequest);

    NameResponse getVilleById(long villeId);

    void editVille(NameRequest villeRequest, long villeId);

    public void deleteVilleById(long villeId);
}
