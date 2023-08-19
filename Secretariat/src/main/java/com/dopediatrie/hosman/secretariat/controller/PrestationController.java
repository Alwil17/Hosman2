package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Prestation;
import com.dopediatrie.hosman.secretariat.payload.request.PrestationRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PrestationResponse;
import com.dopediatrie.hosman.secretariat.service.PrestationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestations")
@RequiredArgsConstructor
@Log4j2
public class PrestationController {

    private final PrestationService prestationService;

    @GetMapping
    public ResponseEntity<List<Prestation>> getAllPrestations() {

        log.info("PrestationController | getAllPrestations is called");
        return new ResponseEntity<>(prestationService.getAllPrestations(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addPrestation(@RequestBody PrestationRequest prestationRequest) {

        log.info("PrestationController | addPrestation is called");

        log.info("PrestationController | addPrestation | prestationRequest : " + prestationRequest.toString());

        long prestationId = prestationService.addPrestation(prestationRequest);
        return new ResponseEntity<>(prestationId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrestationResponse> getPrestationById(@PathVariable("id") long prestationId) {

        log.info("PrestationController | getPrestationById is called");

        log.info("PrestationController | getPrestationById | prestationId : " + prestationId);

        PrestationResponse prestationResponse
                = prestationService.getPrestationById(prestationId);
        return new ResponseEntity<>(prestationResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editPrestation(@RequestBody PrestationRequest prestationRequest,
            @PathVariable("id") long prestationId
    ) {

        log.info("PrestationController | editPrestation is called");

        log.info("PrestationController | editPrestation | prestationId : " + prestationId);

        prestationService.editPrestation(prestationRequest, prestationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deletePrestationById(@PathVariable("id") long prestationId) {
        prestationService.deletePrestationById(prestationId);
    }
}