package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Secteur;
import com.dopediatrie.hosman.secretariat.payload.request.SecteurRequest;
import com.dopediatrie.hosman.secretariat.payload.response.SecteurResponse;

import java.util.List;

public interface SecteurService {
    List<Secteur> getAllSecteurs();

    long addSecteur(SecteurRequest acteRequest);

    SecteurResponse getSecteurById(long acteId);

    void editSecteur(SecteurRequest acteRequest, long acteId);

    public void deleteSecteurById(long acteId);
}
