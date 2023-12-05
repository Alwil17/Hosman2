package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.payload.request.SecteurRequest;
import com.dopediatrie.hosman.secretariat.payload.response.SecteurResponse;

import java.util.List;

public interface SecteurService {
    List<SecteurResponse> getAllSecteurs();

    long addSecteur(SecteurRequest secteurRequest);

    void addSecteur(List<SecteurRequest> secteurRequests);

    SecteurResponse getSecteurById(long secteurId);

    void editSecteur(SecteurRequest secteurRequest, long secteurId);

    public void deleteSecteurById(long secteurId);

    SecteurResponse getSecteurForUser(long userId);
}
