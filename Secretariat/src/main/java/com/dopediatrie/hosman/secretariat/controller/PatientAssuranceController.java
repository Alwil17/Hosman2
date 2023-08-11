package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.PatientAssurance;
import com.dopediatrie.hosman.secretariat.entity.PatientAssurancePK;
import com.dopediatrie.hosman.secretariat.payload.request.PatientAssuranceRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PatientAssuranceResponse;
import com.dopediatrie.hosman.secretariat.service.PatientAssuranceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patientAssurances")
@RequiredArgsConstructor
@Log4j2
public class PatientAssuranceController {

    private final PatientAssuranceService patientAssuranceService;

    @GetMapping
    public ResponseEntity<List<PatientAssurance>> getAllPatientAssurances() {

        log.info("PatientAssuranceController | getAllPatientAssurances is called");
        return new ResponseEntity<>(patientAssuranceService.getAllPatientAssurances(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PatientAssurancePK> addPatientAssurance(@RequestBody PatientAssuranceRequest patientAssuranceRequest) {

        log.info("PatientAssuranceController | addPatientAssurance is called");

        log.info("PatientAssuranceController | addPatientAssurance | patientAssuranceRequest : " + patientAssuranceRequest.toString());

        PatientAssurancePK patientAssuranceId = patientAssuranceService.addPatientAssurance(patientAssuranceRequest);
        return new ResponseEntity<>(patientAssuranceId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientAssuranceResponse> getPatientAssuranceById(@PathVariable("id") long patientAssuranceId) {

        log.info("PatientAssuranceController | getPatientAssuranceById is called");

        log.info("PatientAssuranceController | getPatientAssuranceById | patientAssuranceId : " + patientAssuranceId);

        PatientAssuranceResponse patientAssuranceResponse
                = patientAssuranceService.getPatientAssuranceById(patientAssuranceId);
        return new ResponseEntity<>(patientAssuranceResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editPatientAssurance(@RequestBody PatientAssuranceRequest patientAssuranceRequest,
            @PathVariable("id") long patientAssuranceId
    ) {

        log.info("PatientAssuranceController | editPatientAssurance is called");

        log.info("PatientAssuranceController | editPatientAssurance | patientAssuranceId : " + patientAssuranceId);

        patientAssuranceService.editPatientAssurance(patientAssuranceRequest, patientAssuranceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deletePatientAssuranceById(@PathVariable("id") long patientAssuranceId) {
        patientAssuranceService.deletePatientAssuranceById(patientAssuranceId);
    }
}