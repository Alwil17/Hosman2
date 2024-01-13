package com.dopediatrie.hosman.bm.controller;

import com.dopediatrie.hosman.bm.entity.Motif;
import com.dopediatrie.hosman.bm.payload.request.MotifRequest;
import com.dopediatrie.hosman.bm.payload.response.MotifResponse;
import com.dopediatrie.hosman.bm.service.MotifService;
import com.dopediatrie.hosman.bm.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/motifs")
@RequiredArgsConstructor
@Log4j2
public class MotifController {

    private final MotifService motifService;

    @GetMapping
    public ResponseEntity<List<Motif>> getAllMotifs() {

        log.info("MotifController | getAllMotifs is called");
        return new ResponseEntity<>(motifService.getAllMotifs(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addMotif(@RequestBody MotifRequest motifRequest) {

        log.info("MotifController | addMotif is called");
        log.info("MotifController | addMotif | motifRequest : " + motifRequest.toString());

        long motifId = motifService.addMotif(motifRequest);
        return new ResponseEntity<>(motifId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotifResponse> getMotifById(@PathVariable("id") long motifId) {

        log.info("MotifController | getMotifById is called");

        log.info("MotifController | getMotifById | motifId : " + motifId);

        MotifResponse motifResponse
                = motifService.getMotifById(motifId);
        return new ResponseEntity<>(motifResponse, HttpStatus.OK);
    }

    @GetMapping("/libelle/{libelle}")
    public ResponseEntity<MotifResponse> getMotifByLibelle(@PathVariable("libelle") String libelle) {

        log.info("MotifController | getMotifById is called");

        MotifResponse motifResponse
                = motifService.getMotifByLibelle(libelle);
        return new ResponseEntity<>(motifResponse, HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity<List<Motif>> getMotifBySearch(@RequestParam(value = "libelle") String libelle) {

        log.info("MotifController | getMotifBySearch is called");
        List<Motif> motifResponse = Collections.emptyList();

        if(libelle != null && !libelle.isBlank())
            motifResponse = motifService.getMotifByLibelleLike(libelle);

        return new ResponseEntity<>(motifResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editMotif(@RequestBody MotifRequest motifRequest,
            @PathVariable("id") long motifId
    ) {

        log.info("MotifController | editMotif is called");

        log.info("MotifController | editMotif | motifId : " + motifId);

        motifService.editMotif(motifRequest, motifId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteMotifById(@PathVariable("id") long motifId) {
        motifService.deleteMotifById(motifId);
    }
}