package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Maladie;
import com.dopediatrie.hosman.secretariat.payload.request.NameRequest;
import com.dopediatrie.hosman.secretariat.payload.response.NameResponse;

import java.util.List;

public interface MaladieService {
    List<Maladie> getAllMaladies();

    long addMaladie(NameRequest maladieRequest);

    void addMaladie(List<NameRequest> maladieRequests);

    NameResponse getMaladieById(long maladieId);

    void editMaladie(NameRequest maladieRequest, long maladieId);

    public void deleteMaladieById(long maladieId);
}
