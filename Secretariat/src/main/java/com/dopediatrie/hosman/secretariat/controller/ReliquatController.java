package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Reliquat;
import com.dopediatrie.hosman.secretariat.payload.request.ReliquatRequest;
import com.dopediatrie.hosman.secretariat.payload.response.ReliquatResponse;
import com.dopediatrie.hosman.secretariat.service.ReliquatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/reliquats")
@RequiredArgsConstructor
@Log4j2
public class ReliquatController {

    private final ReliquatService reliquatService;

    @GetMapping
    public ResponseEntity<List<Reliquat>> getAllReliquats() {

        log.info("ReliquatController | getAllReliquats is called");
        return new ResponseEntity<>(reliquatService.getAllReliquats(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addReliquat(@RequestBody ReliquatRequest reliquatRequest) {

        log.info("ReliquatController | addReliquat is called");

        log.info("ReliquatController | addReliquat | reliquatRequest : " + reliquatRequest.toString());

        long reliquatId = reliquatService.addReliquat(reliquatRequest);
        return new ResponseEntity<>(reliquatId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReliquatResponse> getReliquatById(@PathVariable("id") long reliquatId) {

        log.info("ReliquatController | getReliquatById is called");

        log.info("ReliquatController | getReliquatById | reliquatId : " + reliquatId);

        ReliquatResponse reliquatResponse
                = reliquatService.getReliquatById(reliquatId);
        return new ResponseEntity<>(reliquatResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Reliquat>> getReliquatBySearch(@RequestParam(value = "datemin") String datemin, @RequestParam(value = "datemax", required = false) String datemax, @RequestParam(value = "nom", required = false) String nom, @RequestParam(value = "reference", required = false) String reference) {
        log.info("ReliquatController | getReliquatBySearch is called");
        List<Reliquat> reliquats = Collections.emptyList();
        String dD = datemin+"T00:00:00";
        String dF;
        if(datemax == null){
            dF = datemin + "T23:59:59";
        }else{
            dF = datemax + "T23:59:59";
        }
        LocalDateTime dateDebut = LocalDateTime.parse(dD);
        LocalDateTime dateFin = LocalDateTime.parse(dF);
        if(nom != null && !nom.isBlank())
            reliquats = reliquatService.getReliquatByDateMinAndMaxAndNom(dateDebut, dateFin, nom);
        if(reference != null && !reference.isBlank())
            reliquats = reliquatService.getReliquatByDateMinAndMaxAndReference(dateDebut, dateFin, reference);
        if((nom == null || (nom != null && nom.isBlank())) && (reference == null || (reference != null && reference.isBlank()))){
            reliquats = reliquatService.getReliquatByDateMinAndMax(dateDebut, dateFin);
        }
        return new ResponseEntity<>(reliquats, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editReliquat(@RequestBody ReliquatRequest reliquatRequest,
            @PathVariable("id") long reliquatId
    ) {

        log.info("ReliquatController | editReliquat is called");

        log.info("ReliquatController | editReliquat | reliquatId : " + reliquatId);

        reliquatService.editReliquat(reliquatRequest, reliquatId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteReliquatById(@PathVariable("id") long reliquatId) {
        reliquatService.deleteReliquatById(reliquatId);
    }
}