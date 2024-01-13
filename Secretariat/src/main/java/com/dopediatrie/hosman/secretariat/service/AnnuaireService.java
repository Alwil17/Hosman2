package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Annuaire;
import com.dopediatrie.hosman.secretariat.payload.request.AnnuaireRequest;
import com.dopediatrie.hosman.secretariat.payload.response.AnnuaireResponse;

import java.util.List;
import java.util.Map;

public interface AnnuaireService {
    List<Annuaire> getAllAnnuaires();

    long addAnnuaire(AnnuaireRequest annuaireRequest);

    void addAnnuaire(List<AnnuaireRequest> annuaireRequests);

    AnnuaireResponse getAnnuaireById(long annuaireId);

    List<Annuaire> getAnnuaireByCategorie(String categorie);

    void editAnnuaire(AnnuaireRequest annuaireRequest, long annuaireId);

    public void deleteAnnuaireById(long annuaireId);

    List<Annuaire> getAnnuaireByCategorieAndQueryString(String categorie, String searchString);

    List<Annuaire> getAnnuaireByQueryString(String searchString);


    Map<String, List<AnnuaireResponse>> getAnnuaireBips();
}
