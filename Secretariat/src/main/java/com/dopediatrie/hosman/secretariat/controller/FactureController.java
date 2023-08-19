package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Facture;
import com.dopediatrie.hosman.secretariat.payload.request.FactureRequest;
import com.dopediatrie.hosman.secretariat.payload.response.FactureResponse;
import com.dopediatrie.hosman.secretariat.service.FactureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/factures")
@RequiredArgsConstructor
@Log4j2
public class FactureController {

    private final FactureService factureService;

    @GetMapping
    public ResponseEntity<List<Facture>> getAllFactures() {

        log.info("FactureController | getAllFactures is called");
        return new ResponseEntity<>(factureService.getAllFactures(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addFacture(@RequestBody FactureRequest factureRequest) {

        log.info("FactureController | addFacture is called");

        log.info("FactureController | addFacture | factureRequest : " + factureRequest.toString());

        long factureId = factureService.addFacture(factureRequest);
        return new ResponseEntity<>(factureId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FactureResponse> getFactureById(@PathVariable("id") long factureId) {

        log.info("FactureController | getFactureById is called");

        log.info("FactureController | getFactureById | factureId : " + factureId);

        FactureResponse factureResponse
                = factureService.getFactureById(factureId);
        return new ResponseEntity<>(factureResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editFacture(@RequestBody FactureRequest factureRequest,
            @PathVariable("id") long factureId
    ) {

        log.info("FactureController | editFacture is called");

        log.info("FactureController | editFacture | factureId : " + factureId);

        factureService.editFacture(factureRequest, factureId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteFactureById(@PathVariable("id") long factureId) {
        factureService.deleteFactureById(factureId);
    }
}