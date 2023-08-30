package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Etat;
import com.dopediatrie.hosman.secretariat.payload.request.EtatRequest;
import com.dopediatrie.hosman.secretariat.payload.response.EtatResponse;
import com.dopediatrie.hosman.secretariat.service.EtatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/etats")
@RequiredArgsConstructor
@Log4j2
public class EtatController {

    private final EtatService etatService;

    @GetMapping
    public ResponseEntity<List<Etat>> getAllEtats() {

        log.info("EtatController | getAllEtats is called");
        return new ResponseEntity<>(etatService.getAllEtats(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addEtat(@RequestBody EtatRequest etatRequest) {

        log.info("EtatController | addEtat is called");

        log.info("EtatController | addEtat | etatRequest : " + etatRequest.toString());

        long etatId = etatService.addEtat(etatRequest);
        return new ResponseEntity<>(etatId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EtatResponse> getEtatById(@PathVariable("id") long etatId) {

        log.info("EtatController | getEtatById is called");

        log.info("EtatController | getEtatById | etatId : " + etatId);

        EtatResponse etatResponse
                = etatService.getEtatById(etatId);
        return new ResponseEntity<>(etatResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editEtat(@RequestBody EtatRequest etatRequest,
            @PathVariable("id") long etatId
    ) {

        log.info("EtatController | editEtat is called");

        log.info("EtatController | editEtat | etatId : " + etatId);

        etatService.editEtat(etatRequest, etatId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteEtatById(@PathVariable("id") long etatId) {
        etatService.deleteEtatById(etatId);
    }
}