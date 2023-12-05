package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.payload.request.MedecinRequest;
import com.dopediatrie.hosman.secretariat.payload.response.MedecinResponse;

import java.util.List;

public interface MedecinService {
    List<MedecinResponse> getAllMedecins();

    String addMedecin(MedecinRequest medecinRequest);

    void addMedecin(List<MedecinRequest> medecinRequests);

    List<MedecinResponse> getMedecinByType(String typeMedecin);

    MedecinResponse getMedecinByMatricule(String demandeur);

    MedecinResponse getMedecinForUser(long userId);
}
