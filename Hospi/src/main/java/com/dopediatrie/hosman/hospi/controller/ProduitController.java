package com.dopediatrie.hosman.hospi.controller;

import com.dopediatrie.hosman.hospi.payload.response.ProduitResponse;
import com.dopediatrie.hosman.hospi.service.ProduitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produits")
@RequiredArgsConstructor
@Log4j2
public class ProduitController {

    private final ProduitService produitService;

    @GetMapping
    public ResponseEntity<List<ProduitResponse>> getAllProduits() {
        log.info("ProduitController | getAllProduits is called");
        return new ResponseEntity<>(produitService.getAllProduits(), HttpStatus.OK);
    }

    @Operation(summary = "Return list of product for specified type.")
    @GetMapping("/{type}")
    public ResponseEntity<List<ProduitResponse>> getProduitByType(@Schema(type = "string", allowableValues = {"medic", "conso", "solu"}) @PathVariable("type") String type) {
        log.info("ProduitController | getProduitById is called");
        return new ResponseEntity<>(produitService.getProduitByType(type), HttpStatus.OK);
    }

}