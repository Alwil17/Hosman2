package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Creance;
import com.dopediatrie.hosman.secretariat.payload.request.CreanceRequest;
import com.dopediatrie.hosman.secretariat.payload.response.CreanceResponse;
import com.dopediatrie.hosman.secretariat.service.CreanceService;
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
@RequestMapping("/creances")
@RequiredArgsConstructor
@Log4j2
public class CreanceController {

    private final CreanceService creanceService;

    @GetMapping
    public ResponseEntity<List<Creance>> getAllCreances() {

        log.info("CreanceController | getAllCreances is called");
        return new ResponseEntity<>(creanceService.getAllCreances(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addCreance(@RequestBody CreanceRequest creanceRequest) {

        log.info("CreanceController | addCreance is called");

        log.info("CreanceController | addCreance | creanceRequest : " + creanceRequest.toString());

        long creanceId = creanceService.addCreance(creanceRequest);
        return new ResponseEntity<>(creanceId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreanceResponse> getCreanceById(@PathVariable("id") long creanceId) {

        log.info("CreanceController | getCreanceById is called");

        log.info("CreanceController | getCreanceById | creanceId : " + creanceId);

        CreanceResponse creanceResponse
                = creanceService.getCreanceById(creanceId);
        return new ResponseEntity<>(creanceResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Creance>> getCreanceBySearch(@RequestParam(value = "datemin") String datemin, @RequestParam(value = "datemax", required = false) String datemax, @RequestParam(value = "nom", required = false) String nom, @RequestParam(value = "reference", required = false) String reference) {
        log.info("CreanceController | getCreanceBySearch is called");
        List<Creance> creances = Collections.emptyList();
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
            creances = creanceService.getCreanceByDateMinAndMaxAndNom(dateDebut, dateFin, nom);
        if(reference != null && !reference.isBlank())
            creances = creanceService.getCreanceByDateMinAndMaxAndReference(dateDebut, dateFin, reference);
        if((nom == null || (nom != null && nom.isBlank())) && (reference == null || (reference != null && reference.isBlank()))){
            creances = creanceService.getCreanceByDateMinAndMax(dateDebut, dateFin);
        }

        return new ResponseEntity<>(creances, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editCreance(@RequestBody CreanceRequest creanceRequest,
            @PathVariable("id") long creanceId
    ) {

        log.info("CreanceController | editCreance is called");

        log.info("CreanceController | editCreance | creanceId : " + creanceId);

        creanceService.editCreance(creanceRequest, creanceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/sold")
    public ResponseEntity<Void> soldCreance(@RequestBody CreanceRequest creanceRequest,
                                            @PathVariable("id") long creanceId
    ) {

        log.info("CreanceController | soldCreance is called");
        log.info("CreanceController | soldCreance | creanceId : " + creanceId);

        creanceService.soldCreance(creanceRequest, creanceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteCreanceById(@PathVariable("id") long creanceId) {
        creanceService.deleteCreanceById(creanceId);
    }
}