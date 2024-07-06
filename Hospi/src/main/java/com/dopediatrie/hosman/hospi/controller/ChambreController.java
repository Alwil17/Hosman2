package com.dopediatrie.hosman.hospi.controller;

import com.dopediatrie.hosman.hospi.entity.Chambre;
import com.dopediatrie.hosman.hospi.payload.request.ChambreRequest;
import com.dopediatrie.hosman.hospi.payload.response.ChambreResponse;
import com.dopediatrie.hosman.hospi.payload.response.ChambreResponse;
import com.dopediatrie.hosman.hospi.service.ChambreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/chambres")
@RequiredArgsConstructor
@Log4j2
public class ChambreController {

    private final ChambreService chambreService;

    @GetMapping
    public ResponseEntity<List<Chambre>> getAllChambres(@RequestParam(value = "vue", required = false) String vue) {
        log.info("ChambreController | getAllChambres is called");
        List<Chambre> chambres = chambreService.getAllChambres();
        if((vue!=null) && !vue.isBlank() && vue.equals("UNTAKEN")){
            chambres = chambreService.getAllChambresUntaken();
        }
        return new ResponseEntity<>(chambres, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addChambre(@RequestBody ChambreRequest chambreRequest) {

        log.info("ChambreController | addChambre is called");

        log.info("ChambreController | addChambre | chambreRequest : " + chambreRequest.toString());

        long chambreId = chambreService.addChambre(chambreRequest);
        return new ResponseEntity<>(chambreId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChambreResponse> getChambreById(@PathVariable("id") long chambreId) {

        log.info("ChambreController | getChambreById is called");

        log.info("ChambreController | getChambreById | chambreId : " + chambreId);

        ChambreResponse chambreResponse
                = chambreService.getChambreById(chambreId);
        return new ResponseEntity<>(chambreResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Chambre>> getChambreBySearch(@RequestParam("nom") String nom) {
        log.info("ChambreController | getChambreBySearch is called");
        List<Chambre> chambres = Collections.emptyList();
        if(nom != null && nom.isBlank())
            chambres = chambreService.getChambreByNom(nom);
        return new ResponseEntity<>(chambres, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editChambre(@RequestBody ChambreRequest chambreRequest,
            @PathVariable("id") long chambreId
    ) {

        log.info("ChambreController | editChambre is called");

        log.info("ChambreController | editChambre | chambreId : " + chambreId);

        chambreService.editChambre(chambreRequest, chambreId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteChambreById(@PathVariable("id") long chambreId) {
        chambreService.deleteChambreById(chambreId);
    }
}