package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.payload.response.MedecinResponse;

import java.util.List;

public interface MedecinService {
    List<MedecinResponse> getAllMedecins();

    List<MedecinResponse> getMedecinByType(String typeMedecin);

    MedecinResponse getMedecinByMatricule(String demandeur);

    MedecinResponse getMedecinForUser(long userId);
}
