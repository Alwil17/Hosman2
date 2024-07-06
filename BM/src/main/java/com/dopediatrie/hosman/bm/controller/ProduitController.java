package com.dopediatrie.hosman.bm.controller;

import com.dopediatrie.hosman.bm.entity.Produit;
import com.dopediatrie.hosman.bm.payload.request.ProduitRequest;
import com.dopediatrie.hosman.bm.payload.response.ProduitResponse;
import com.dopediatrie.hosman.bm.service.ProduitService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/produits")
@RequiredArgsConstructor
@Log4j2
public class ProduitController {

    private final ProduitService produitService;

    @GetMapping
    public ResponseEntity<List<Produit>> getAllProduits() {

        log.info("ProduitController | getAllProduits is called");
        return new ResponseEntity<>(produitService.getAllProduits(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addProduit(@RequestBody ProduitRequest produitRequest) {

        log.info("ProduitController | addProduit is called");

        log.info("ProduitController | addProduit | produitRequest : " + produitRequest.toString());

        long produitId = produitService.addProduit(produitRequest);
        return new ResponseEntity<>(produitId, HttpStatus.CREATED);
    }

    @GetMapping("/entity/{id}")
    public ResponseEntity<List<Produit>> getProduitByEntityId(@Schema(type = "string", allowableValues = {"agence", "labo", "delegue", "classe"}) @RequestParam("type") String type, @PathVariable("id") long id, @RequestParam(value = "q", required = false) String q) {
        log.info("ProduitController | getProduitBySearch is called");
        List<Produit> produits = Collections.emptyList();

        switch (type) {
            case "agence":
                produits = produitService.getProduitByAgenceIdAndQueryString(id, q);
                break;
            case "labo":
                produits = produitService.getProduitByLaboIdAndQueryString(id, q);
                break;
            case "delegue":
                produits = produitService.getProduitByDelegueIdAndQueryString(id, q);
                break;
            case "classe":
                produits = produitService.getProduitByClasseIdAndQueryString(id, q);
                break;
            default:
                break;
        }

        return new ResponseEntity<>(produits, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Produit>> getProduitBySearch(@Schema(type = "string", allowableValues = {"all", "dci", "indication", "classe", "labo", "nom"}) @RequestParam("criteria") String searchCriteria, @RequestParam("q") String q) {
        log.info("ProduitController | getProduitBySearch is called");
        List<Produit> produits = Collections.emptyList();

        switch (searchCriteria) {
            case "dci":
                produits = produitService.getProduitByDciLike(q);
                break;
            case "indication":
                produits = produitService.getProduitByIndicationLike(q);
                break;
                case "classe":
                produits = produitService.getProduitByClasseLike(q);
                break;
                case "labo":
                produits = produitService.getProduitByLaboLike(q);
                break;
                case "nom":
                produits = produitService.getProduitByNomLike(q);
                break;
            default:
                produits = produitService.getProduitByLSearchString(q);
                break;
        }

        return new ResponseEntity<>(produits, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProduitResponse> getProduitById(@PathVariable("id") long produitId) {
        log.info("ProduitController | getProduitById is called");
        log.info("ProduitController | getProduitById | produitId : " + produitId);

        ProduitResponse produitResponse
                = produitService.getProduitById(produitId);
        return new ResponseEntity<>(produitResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editProduit(@RequestBody ProduitRequest produitRequest,
                                            @PathVariable("id") long produitId
    ) {

        log.info("ProduitController | editProduit is called");

        log.info("ProduitController | editProduit | produitId : " + produitId);

        produitService.editProduit(produitRequest, produitId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteProduitById(@PathVariable("id") long produitId) {
        produitService.deleteProduitById(produitId);
    }
}