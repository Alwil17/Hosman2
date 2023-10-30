package com.dopediatrie.hosman.stock.service;

import com.dopediatrie.hosman.stock.entity.Produit;
import com.dopediatrie.hosman.stock.payload.request.ProduitRequest;
import com.dopediatrie.hosman.stock.payload.response.ProduitResponse;

import java.util.List;

public interface ProduitService {
    List<Produit> getAllProduits();

    long addProduit(ProduitRequest produitRequest);

    void addProduit(List<ProduitRequest> produitRequests);

    ProduitResponse getProduitById(long produitId);

    void editProduit(ProduitRequest produitRequest, long produitId);

    public void deleteProduitById(long produitId);

    List<Produit> getProduitByNom(String nom);

    List<Produit> getProduitByDci(String dci);

    List<Produit> getProduitByIndication(String indication);

    List<Produit> getProduitByClasse(String classe);

    List<Produit> getProduitByCodeActe(String code_acte);
}
