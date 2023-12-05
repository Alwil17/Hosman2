package com.dopediatrie.hosman.auth.service;

import com.dopediatrie.hosman.auth.entity.Secteur;
import com.dopediatrie.hosman.auth.payload.request.SecteurRequest;
import com.dopediatrie.hosman.auth.payload.response.SecteurResponse;

import java.util.List;

public interface SecteurService {
    List<Secteur> getAllSecteurs();

    long addSecteur(SecteurRequest secteurRequest);

    void addSecteur(List<SecteurRequest> secteurRequests);

    SecteurResponse getSecteurById(long secteurId);

    SecteurResponse getSecteurByUserId(long userId);

    List<Secteur> getSecteurByDepartement(String departement);

    List<Secteur> getSecteurByDepartementAndCode(String departement, String code);

    void editSecteur(SecteurRequest secteurRequest, long secteurId);

    public void deleteSecteurById(long secteurId);
}
