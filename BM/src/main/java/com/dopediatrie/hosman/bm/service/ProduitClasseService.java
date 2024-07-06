package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.ProduitClasse;
import com.dopediatrie.hosman.bm.entity.ProduitClassePK;
import com.dopediatrie.hosman.bm.payload.request.ProduitClasseRequest;
import com.dopediatrie.hosman.bm.payload.response.ProduitClasseResponse;

import java.util.List;

public interface ProduitClasseService {
    List<ProduitClasse> getAllProduitClasses();

    ProduitClassePK addProduitClasse(ProduitClasseRequest produitClasseRequest);

    ProduitClasseResponse getProduitClasseById(long produitClasseId);

    void editProduitClasse(ProduitClasseRequest produitClasseRequest, long produitClasseId);

    public void deleteProduitClasseById(long produitClasseId);

    void deleteAllForProduitId(long produitId);
}
