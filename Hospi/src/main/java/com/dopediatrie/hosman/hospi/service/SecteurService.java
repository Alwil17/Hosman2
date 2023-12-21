package com.dopediatrie.hosman.hospi.service;

import com.dopediatrie.hosman.hospi.payload.request.SecteurRequest;
import com.dopediatrie.hosman.hospi.payload.response.SecteurResponse;

import java.util.List;

public interface SecteurService {
    List<SecteurResponse> getAllSecteurs();

    long addSecteur(SecteurRequest secteurRequest);

    void addSecteur(List<SecteurRequest> secteurRequests);

    SecteurResponse getSecteurById(long secteurId);

    void editSecteur(SecteurRequest secteurRequest, long secteurId);

    public void deleteSecteurById(long secteurId);

    SecteurResponse getSecteurForUser(long userId);

    SecteurResponse getSecteurByCode(String secteur_code);
}
