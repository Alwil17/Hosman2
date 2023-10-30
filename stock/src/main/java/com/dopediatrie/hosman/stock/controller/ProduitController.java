package com.dopediatrie.hosman.stock.controller;

import com.dopediatrie.hosman.stock.entity.Produit;
import com.dopediatrie.hosman.stock.payload.request.ProduitRequest;
import com.dopediatrie.hosman.stock.payload.response.ProduitResponse;
import com.dopediatrie.hosman.stock.service.ProduitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @GetMapping("/search")
    public ResponseEntity<List<Produit>> getProduitBySearch(@RequestParam(value = "nom", required = false) String nom, @RequestParam(value = "dci", required = false) String dci, @RequestParam(value = "indication", required = false) String indication, @RequestParam(value = "classe", required = false) String classe, @RequestParam(value = "code_acte", required = false) String code_acte) {

        log.info("ProduitController | getProduitBySearch is called");
        List<Produit> produitResponse = Collections.emptyList();

        if(nom != null && !nom.isBlank())
            produitResponse = produitService.getProduitByNom(nom);
        if(dci != null && !dci.isBlank())
            produitResponse = produitService.getProduitByDci(dci);
        if(code_acte != null && !code_acte.isBlank())
            produitResponse = produitService.getProduitByCodeActe(code_acte);
        if(indication != null && !indication.isBlank())
            produitResponse = produitService.getProduitByIndication(indication);
        if(classe != null && !classe.isBlank())
            produitResponse = produitService.getProduitByClasse(classe);

        return new ResponseEntity<>(produitResponse, HttpStatus.OK);
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