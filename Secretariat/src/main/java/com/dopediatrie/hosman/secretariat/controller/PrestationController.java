package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Prestation;
import com.dopediatrie.hosman.secretariat.payload.request.PrestationRequest;
import com.dopediatrie.hosman.secretariat.payload.request.PrestationTempRequest;
import com.dopediatrie.hosman.secretariat.payload.response.FactureResponse;
import com.dopediatrie.hosman.secretariat.payload.response.PrestationResponse;
import com.dopediatrie.hosman.secretariat.service.PrestationService;
import com.dopediatrie.hosman.secretariat.service.PrestationTempService;
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
    private final PrestationTempService prestationTempService;

    @GetMapping
    public ResponseEntity<List<Prestation>> getAllPrestations() {

        log.info("PrestationController | getAllPrestations is called");
        return new ResponseEntity<>(prestationService.getAllPrestations(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FactureResponse> addPrestation(@RequestBody PrestationTempRequest prestationRequest) {

        log.info("PrestationController | addPrestationTemp is called");
        log.info("PrestationController | addPrestationTemp | prestationRequest : " + prestationRequest.toString());

        FactureResponse response = prestationTempService.addPrestationTemp(prestationRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
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