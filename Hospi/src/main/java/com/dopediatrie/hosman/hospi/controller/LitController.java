package com.dopediatrie.hosman.hospi.controller;

import com.dopediatrie.hosman.hospi.entity.Lit;
import com.dopediatrie.hosman.hospi.payload.request.LitRequest;
import com.dopediatrie.hosman.hospi.payload.response.LitResponse;
import com.dopediatrie.hosman.hospi.service.LitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/lits")
@RequiredArgsConstructor
@Log4j2
public class LitController {

    private final LitService litService;

    @GetMapping
    public ResponseEntity<List<Lit>> getAllLits(@RequestParam(value = "vue", required = false) String vue) {
        log.info("LitController | getAllLits is called");
        List<Lit> lits = litService.getAllLits();
        if((vue!=null) && !vue.isBlank() && vue.equals("UNTAKEN")){
            lits = litService.getAllLitsUntaken();
        }
        return new ResponseEntity<>(lits, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addLit(@RequestBody LitRequest litRequest) {

        log.info("LitController | addLit is called");

        log.info("LitController | addLit | litRequest : " + litRequest.toString());

        long litId = litService.addLit(litRequest);
        return new ResponseEntity<>(litId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LitResponse> getLitById(@PathVariable("id") long litId) {

        log.info("LitController | getLitById is called");

        log.info("LitController | getLitById | litId : " + litId);

        LitResponse litResponse
                = litService.getLitById(litId);
        return new ResponseEntity<>(litResponse, HttpStatus.OK);
    }

    @GetMapping("/chambre/{id}")
    public ResponseEntity<List<Lit>> getLitByChambreId(@PathVariable("id") long chambreId) {
        log.info("LitController | getLitBySearch is called");
        return new ResponseEntity<>(litService.getLitByChambreId(chambreId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Lit>> getLitBySearch(@RequestParam("nom") String nom) {
        log.info("LitController | getLitBySearch is called");
        List<Lit> lits = Collections.emptyList();
        if(nom != null && nom.isBlank())
            lits = litService.getLitByNom(nom);
        return new ResponseEntity<>(lits, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editLit(@RequestBody LitRequest litRequest,
            @PathVariable("id") long litId
    ) {

        log.info("LitController | editLit is called");

        log.info("LitController | editLit | litId : " + litId);

        litService.editLit(litRequest, litId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteLitById(@PathVariable("id") long litId) {
        litService.deleteLitById(litId);
    }
}