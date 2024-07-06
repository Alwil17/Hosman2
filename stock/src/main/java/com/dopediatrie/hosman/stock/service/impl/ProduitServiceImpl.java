package com.dopediatrie.hosman.stock.service.impl;

import com.dopediatrie.hosman.stock.entity.Produit;
import com.dopediatrie.hosman.stock.exception.StockCustomException;
import com.dopediatrie.hosman.stock.payload.request.*;
import com.dopediatrie.hosman.stock.payload.response.ProduitResponse;
import com.dopediatrie.hosman.stock.repository.ProduitRepository;
import com.dopediatrie.hosman.stock.service.*;
import com.dopediatrie.hosman.stock.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProduitServiceImpl implements ProduitService {
    private final String NOT_FOUND = "PRODUIT_NOT_FOUND";
    private final ProduitRepository produitRepository;

    @Override
    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    @Override
    public long addProduit(ProduitRequest produitRequest) {
        log.info("ProduitServiceImpl | addProduit is called");

        Produit produit
                = Produit.builder()
                .nom(produitRequest.getNom())
                .code(Str.slug(produitRequest.getCode()))
                .code_acte(Str.slug(produitRequest.getCode_acte()))
                .nom_officiel(produitRequest.getNom_officiel())
                .dci(produitRequest.getDci())
                .autre(produitRequest.getAutre())
                .prix(produitRequest.getPrix())
                .build();

        produit = produitRepository.save(produit);



        log.info("ProduitServiceImpl | addProduit | Produit Created");
        log.info("ProduitServiceImpl | addProduit | Produit Id : " + produit.getId());
        return produit.getId();
    }

    @Override
    public void addProduit(List<ProduitRequest> produitRequests) {
        log.info("ProduitServiceImpl | addProduit is called");

        for (ProduitRequest produitRequest: produitRequests) {

            Produit produit
                    = Produit.builder()
                    .nom(produitRequest.getNom())
                    .code(Str.slug(produitRequest.getCode()))
                    .code_acte(Str.slug(produitRequest.getCode_acte()))
                    .nom_officiel(produitRequest.getNom_officiel())
                    .dci(produitRequest.getDci())
                    .prix(produitRequest.getPrix())
                    .autre(produitRequest.getAutre())
                    .build();
            produitRepository.save(produit);

        }

        log.info("ProduitServiceImpl | addProduit | Produits Created");
    }

    @Override
    public List<Produit> getProduitByNom(String nom) {
        log.info("ProduitServiceImpl | getProduitByNom is called");

        return produitRepository.findByNomLike(nom);
    }

    @Override
    public List<Produit> getProduitByDci(String dci) {
        log.info("ProduitServiceImpl | getProduitByDci is called");

        return produitRepository.findByDciLike(dci);
    }


    @Override
    public List<Produit> getProduitByCodeActe(String code_acte) {
        log.info("ProduitServiceImpl | getProduitByCodeActe is called");

        return produitRepository.findByCodeActeLike(code_acte);
    }

    @Override
    public ProduitResponse getProduitById(long produitId) {
        log.info("ProduitServiceImpl | getProduitById is called");
        log.info("ProduitServiceImpl | getProduitById | Get the produit for produitId: {}", produitId);

        Produit produit
                = produitRepository.findById(produitId)
                .orElseThrow(
                        () -> new StockCustomException("Produit with given Id not found", NOT_FOUND));

        ProduitResponse produitResponse = new ProduitResponse();

        copyProperties(produit, produitResponse);

        log.info("ProduitServiceImpl | getProduitById | produitResponse :" + produitResponse.toString());

        return produitResponse;
    }

    @Override
    public void editProduit(ProduitRequest produitRequest, long produitId) {
        log.info("ProduitServiceImpl | editProduit is called");

        Produit produit
                = produitRepository.findById(produitId)
                .orElseThrow(() -> new StockCustomException(
                        "Produit with given Id not found",
                        NOT_FOUND
                ));
        produit.setNom(produitRequest.getNom());
        produit.setCode(Str.slug(produitRequest.getCode()));
        produit.setCode_acte(Str.slug(produitRequest.getCode_acte()));
        produit.setNom_officiel(produitRequest.getNom_officiel());
        produit.setDci(produitRequest.getDci());
        produit.setAutre(produitRequest.getAutre());
        produitRepository.save(produit);

        log.info("ProduitServiceImpl | editProduit | Produit Updated");
        log.info("ProduitServiceImpl | editProduit | Produit Id : " + produit.getId());
    }

    @Override
    public void deleteProduitById(long produitId) {
        log.info("Produit id: {}", produitId);

        if (!produitRepository.existsById(produitId)) {
            log.info("Im in this loop {}", !produitRepository.existsById(produitId));
            throw new StockCustomException(
                    "Produit with given with Id: " + produitId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Produit with id: {}", produitId);
        produitRepository.deleteById(produitId);
    }
}
