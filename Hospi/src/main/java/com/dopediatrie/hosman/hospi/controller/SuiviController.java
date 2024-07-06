package com.dopediatrie.hosman.hospi.controller;

import com.dopediatrie.hosman.hospi.entity.Suivi;
import com.dopediatrie.hosman.hospi.payload.request.SuiviRequest;
import com.dopediatrie.hosman.hospi.payload.response.SuiviResponse;
import com.dopediatrie.hosman.hospi.service.SuiviService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suivis")
@RequiredArgsConstructor
@Log4j2
public class SuiviController {

    private final SuiviService suiviService;

    @GetMapping
    public ResponseEntity<List<Suivi>> getAllSuivis() {

        log.info("SuiviController | getAllSuivis is called");
        return new ResponseEntity<>(suiviService.getAllSuivis(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addSuivi(@RequestBody SuiviRequest suiviRequest) {

        log.info("SuiviController | addSuivi is called");

        log.info("SuiviController | addSuivi | suiviRequest : " + suiviRequest.toString());

        long suiviId = suiviService.addSuivi(suiviRequest);
        return new ResponseEntity<>(suiviId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuiviResponse> getSuiviById(@PathVariable("id") long suiviId) {

        log.info("SuiviController | getSuiviById is called");

        log.info("SuiviController | getSuiviById | suiviId : " + suiviId);

        SuiviResponse suiviResponse
                = suiviService.getSuiviById(suiviId);
        return new ResponseEntity<>(suiviResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editSuivi(@RequestBody SuiviRequest suiviRequest,
            @PathVariable("id") long suiviId) {

        log.info("SuiviController | editSuivi is called");
        log.info("SuiviController | editSuivi | suiviId : " + suiviId);

        suiviService.editSuivi(suiviRequest, suiviId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteSuiviById(@PathVariable("id") long suiviId) {
        suiviService.deleteSuiviById(suiviId);
    }
}