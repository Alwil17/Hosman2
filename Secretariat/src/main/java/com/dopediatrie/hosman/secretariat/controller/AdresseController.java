package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Adresse;
import com.dopediatrie.hosman.secretariat.payload.request.AdresseRequest;
import com.dopediatrie.hosman.secretariat.payload.response.AdresseResponse;
import com.dopediatrie.hosman.secretariat.service.AdresseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adresses")
@RequiredArgsConstructor
@Log4j2
public class AdresseController {

    private final AdresseService adresseService;

    @GetMapping
    public ResponseEntity<List<Adresse>> getAllAdresses() {

        log.info("AdresseController | getAllAdresses is called");
        return new ResponseEntity<>(adresseService.getAllAdresses(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addAdresse(@RequestBody AdresseRequest adresseRequest) {

        log.info("AdresseController | addAdresse is called");

        log.info("AdresseController | addAdresse | adresseRequest : " + adresseRequest.toString());

        long adresseId = adresseService.addAdresse(adresseRequest);
        return new ResponseEntity<>(adresseId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdresseResponse> getAdresseById(@PathVariable("id") long adresseId) {

        log.info("AdresseController | getAdresseById is called");

        log.info("AdresseController | getAdresseById | adresseId : " + adresseId);

        AdresseResponse adresseResponse
                = adresseService.getAdresseById(adresseId);
        return new ResponseEntity<>(adresseResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editAdresse(@RequestBody AdresseRequest adresseRequest,
            @PathVariable("id") long adresseId
    ) {

        log.info("AdresseController | editAdresse is called");

        log.info("AdresseController | editAdresse | adresseId : " + adresseId);

        adresseService.editAdresse(adresseRequest, adresseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteAdresseById(@PathVariable("id") long adresseId) {
        adresseService.deleteAdresseById(adresseId);
    }
}