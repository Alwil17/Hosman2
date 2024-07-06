package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.Produit;
import com.dopediatrie.hosman.bm.payload.request.ProduitRequest;
import com.dopediatrie.hosman.bm.payload.response.ProduitResponse;

import java.util.List;

public interface ProduitService {
    List<Produit> getAllProduits();

    long addProduit(ProduitRequest produitRequest);

    void addProduit(List<ProduitRequest> produitRequests);

    ProduitResponse getProduitById(long produitId);

    void editProduit(ProduitRequest produitRequest, long produitId);

    public void deleteProduitById(long produitId);

    List<Produit> getProduitByNomLike(String nom);

    List<Produit> getProduitByDciLike(String dci);

    List<Produit> getProduitByIndicationLike(String indication);

    List<Produit> getProduitByClasseLike(String classe);

    List<Produit> getProduitByLSearchString(String q);

    List<Produit> getProduitByLaboLike(String q);

    List<Produit> getProduitByAgenceIdAndQueryString(long id, String q);

    List<Produit> getProduitByLaboIdAndQueryString(long id, String q);

    List<Produit> getProduitByDelegueIdAndQueryString(long id, String q);

    List<Produit> getProduitByClasseIdAndQueryString(long id, String q);
}
