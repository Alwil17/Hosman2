package com.dopediatrie.hosman.hospi.service;

import com.dopediatrie.hosman.hospi.payload.request.MedecinRequest;
import com.dopediatrie.hosman.hospi.payload.response.MedecinResponse;

import java.util.List;

public interface MedecinService {
    List<MedecinResponse> getAllMedecins();

    String addMedecin(MedecinRequest medecinRequest);

    void addMedecin(List<MedecinRequest> medecinRequests);

    List<MedecinResponse> getMedecinByType(String typeMedecin);

    MedecinResponse getMedecinByMatricule(String demandeur);

    MedecinResponse getMedecinForUser(long userId);
}
