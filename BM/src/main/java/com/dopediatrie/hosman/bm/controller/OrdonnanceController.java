package com.dopediatrie.hosman.bm.controller;

import com.dopediatrie.hosman.bm.entity.Ordonnance;
import com.dopediatrie.hosman.bm.payload.request.OrdonnanceRequest;
import com.dopediatrie.hosman.bm.payload.response.OrdonnanceResponse;
import com.dopediatrie.hosman.bm.service.OrdonnanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ordonnances")
@RequiredArgsConstructor
@Log4j2
public class OrdonnanceController {

    private final OrdonnanceService ordonnanceService;

    @GetMapping
    public ResponseEntity<List<Ordonnance>> getAllOrdonnances() {

        log.info("OrdonnanceController | getAllOrdonnances is called");
        return new ResponseEntity<>(ordonnanceService.getAllOrdonnances(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addOrdonnance(@RequestBody OrdonnanceRequest ordonnanceRequest) {

        log.info("OrdonnanceController | addOrdonnance is called");
        log.info("OrdonnanceController | addOrdonnance | ordonnanceRequest : " + ordonnanceRequest.toString());

        long ordonnanceId = ordonnanceService.addOrdonnance(ordonnanceRequest);
        return new ResponseEntity<>(ordonnanceId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdonnanceResponse> getOrdonnanceById(@PathVariable("id") long ordonnanceId) {

        log.info("OrdonnanceController | getOrdonnanceById is called");

        log.info("OrdonnanceController | getOrdonnanceById | ordonnanceId : " + ordonnanceId);

        OrdonnanceResponse ordonnanceResponse
                = ordonnanceService.getOrdonnanceById(ordonnanceId);
        return new ResponseEntity<>(ordonnanceResponse, HttpStatus.OK);
    }

    @GetMapping("/ref/{id}")
    public ResponseEntity<OrdonnanceResponse> getOrdonnanceByRef(@PathVariable("id") String ordonnanceRef) {

        log.info("OrdonnanceController | getOrdonnanceByRef is called");
        log.info("OrdonnanceController | getOrdonnanceByRef | ordonnanceId : " + ordonnanceRef);

        OrdonnanceResponse ordonnanceResponse
                = ordonnanceService.getOrdonnanceByRef(ordonnanceRef);
        return new ResponseEntity<>(ordonnanceResponse, HttpStatus.OK);
    }

    @GetMapping("/diagnostics/get")
    public ResponseEntity<List<String>> getOrdonnanceDiagnostics(@RequestParam("q") String queryString) {
        log.info("OrdonnanceController | getOrdonnanceDiagnostics is called");
        return new ResponseEntity<>(ordonnanceService.getOrdonnanceDiagnostics(queryString), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Ordonnance>> getOrdonnancesBySearch(@RequestParam("type") String typeOrd, @RequestParam(value = "diagnostic", required = false) String diagnostic, @RequestParam(value = "patient_ref", required = false) String patient_ref) {
        log.info("OrdonnanceController | getOrdonnancesBySearch is called");
        List<Ordonnance> ordonnances = new ArrayList<>();
        if(typeOrd.equals("prep")){
            ordonnances = ordonnanceService.getOrdonnancesForDiagnostic(diagnostic);
        }else {
            ordonnances = ordonnanceService.getOrdonnanceByPatientRef(patient_ref);
        }
        return new ResponseEntity<>(ordonnances, HttpStatus.OK);
    }

    @GetMapping("/consultation/{id}")
    public ResponseEntity<OrdonnanceResponse> getOrdonnanceByConsultationId(@PathVariable("id") long consultation_id) {
        log.info("OrdonnanceController | getOrdonnanceByPatientRef is called");

        OrdonnanceResponse ordonnanceResponses
                = ordonnanceService.getOrdonnanceByConsultationId(consultation_id);

        return new ResponseEntity<>(ordonnanceResponses, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editOrdonnance(@RequestBody OrdonnanceRequest ordonnanceRequest,
            @PathVariable("id") long ordonnanceId) {

        log.info("OrdonnanceController | editOrdonnance is called");

        log.info("OrdonnanceController | editOrdonnance | ordonnanceId : " + ordonnanceId);

        ordonnanceService.editOrdonnance(ordonnanceRequest, ordonnanceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteOrdonnanceById(@PathVariable("id") long ordonnanceId) {
        ordonnanceService.deleteOrdonnanceById(ordonnanceId);
    }
}