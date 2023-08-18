package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Medecin;
import com.dopediatrie.hosman.secretariat.payload.request.MedecinRequest;
import com.dopediatrie.hosman.secretariat.payload.response.MedecinResponse;

import java.util.List;

public interface MedecinService {
    List<Medecin> getAllMedecins();

    long addMedecin(MedecinRequest medecinRequest);

    MedecinResponse getMedecinById(long medecinId);

    MedecinResponse getMedecinByType(String typeMedecin);

    void editMedecin(MedecinRequest medecinRequest, long medecinId);

    public void deleteMedecinById(long medecinId);
}
