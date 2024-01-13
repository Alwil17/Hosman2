package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Categorie;
import com.dopediatrie.hosman.secretariat.payload.request.NameRequest;
import com.dopediatrie.hosman.secretariat.payload.response.NameResponse;

import java.util.List;

public interface CategorieService {
    List<Categorie> getAllCategories();

    String addCategorieGetSlug(String nom);

    long addCategorie(NameRequest categorieRequest);

    void addCategorie(List<NameRequest> categorieRequests);

    NameResponse getCategorieById(long categorieId);

    NameResponse getCategorieBySlug(String slug);

    void editCategorie(NameRequest categorieRequest, long categorieId);

    public void deleteCategorieById(long categorieId);
}
