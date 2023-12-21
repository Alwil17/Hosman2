package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Patient;
import com.dopediatrie.hosman.secretariat.payload.request.PatientRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PatientResponse;
import com.dopediatrie.hosman.secretariat.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Log4j2
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {

        log.info("PatientController | getAllPatients is called");
        return new ResponseEntity<>(patientService.getAllPatients(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addPatient(@RequestBody PatientRequest patientRequest) {

        log.info("PatientController | addPatient is called");

        log.info("PatientController | addPatient | patientRequest : " + patientRequest.toString());

        long patientId = patientService.addPatient(patientRequest);
        return new ResponseEntity<>(patientId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable("id") long patientId) {

        log.info("PatientController | getPatientById is called");

        log.info("PatientController | getPatientById | patientId : " + patientId);

        PatientResponse patientResponse
                = patientService.getPatientById(patientId);
        return new ResponseEntity<>(patientResponse, HttpStatus.OK);
    }

    @GetMapping("/ref/{ref}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable("ref") String patientRef) {

        log.info("PatientController | getPatientById is called");

        PatientResponse patientResponse
                = patientService.getPatientByReferenceUnique(patientRef);
        return new ResponseEntity<>(patientResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Patient>> getPatientBySearch(@RequestParam(value = "nom", required = false) String nom, @RequestParam(value = "prenoms", required = false) String prenoms, @RequestParam(value = "reference", required = false) String reference, @RequestParam(value = "naissance", required = false) String dateNaiss, @RequestParam(value = "entree", required = false) String dateEntr) {

        log.info("PatientController | getPatientBySearch is called");
        List<Patient> patientResponse = Collections.emptyList();

        if(nom != null && !nom.isBlank())
            patientResponse = patientService.getPatientByNomAndPrenoms(nom);
        if(prenoms != null && !prenoms.isBlank())
            patientResponse = patientService.getPatientByPrenoms(prenoms);
        if(reference != null && !reference.isBlank())
            patientResponse = patientService.getPatientByReference(reference);
        if(dateNaiss != null){
            String dN = dateNaiss+"T00:00:00";
            String dNF = dateNaiss+"T23:59:59";
            LocalDateTime dateNaissance = LocalDateTime.parse(dN);
            LocalDateTime dateNaissanceLimit = LocalDateTime.parse(dNF);
            patientResponse = patientService.getPatientByDateNaissance(dateNaissance, dateNaissanceLimit);
        }
        if(dateEntr != null){
            String dE = dateEntr+"T00:00:00";
            String dEF = dateEntr+"T23:59:59";
            LocalDateTime dateEntree = LocalDateTime.parse(dE);
            LocalDateTime dateEntreeLimit = LocalDateTime.parse(dEF);
            patientResponse = patientService.getPatientByDateEntree(dateEntree, dateEntreeLimit);
        }

        return new ResponseEntity<>(patientResponse, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> editPatient(@RequestBody PatientRequest patientRequest,
            @PathVariable("id") long patientId
    ) {

        log.info("PatientController | editPatient is called");

        log.info("PatientController | editPatient | patientId : " + patientId);

        patientService.editPatient(patientRequest, patientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/bm")
    public ResponseEntity<Void> editPatientCaracs(@RequestBody PatientRequest patientRequest,
                                            @PathVariable("id") long patientId
    ) {

        log.info("PatientController | editPatientCaracs is called");
        log.info("PatientController | editPatientCaracs | patientId : " + patientId);

        patientService.editPatientCaracs(patientRequest, patientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deletePatientById(@PathVariable("id") long patientId) {
        patientService.deletePatientById(patientId);
    }
}