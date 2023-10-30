package com.dopediatrie.hosman.stock.service;

import com.dopediatrie.hosman.stock.entity.ProduitClasse;
import com.dopediatrie.hosman.stock.entity.ProduitClassePK;
import com.dopediatrie.hosman.stock.payload.request.ProduitClasseRequest;
import com.dopediatrie.hosman.stock.payload.response.ProduitClasseResponse;

import java.util.List;

public interface ProduitClasseService {
    List<ProduitClasse> getAllProduitClasses();

    ProduitClassePK addProduitClasse(ProduitClasseRequest produitClasseRequest);

    ProduitClasseResponse getProduitClasseById(long produitClasseId);

    void editProduitClasse(ProduitClasseRequest produitClasseRequest, long produitClasseId);

    public void deleteProduitClasseById(long produitClasseId);
}
