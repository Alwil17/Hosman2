package com.dopediatrie.hosman.hospi.service;

import com.dopediatrie.hosman.hospi.payload.response.GlobalProduitResponse;
import com.dopediatrie.hosman.hospi.payload.response.ProduitResponse;

import java.util.List;

public interface ProduitService {
    List<GlobalProduitResponse> getAllProduits();

    ProduitResponse getProduitById(long produitId);

    ProduitResponse getProduitByCode(String produit_code);

    List<ProduitResponse> getProduitByType(String type);
}
