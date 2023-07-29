package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.PatientAdresse;
import com.dopediatrie.hosman.secretariat.payload.request.PatientAdresseRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PatientAdresseResponse;
import com.dopediatrie.hosman.secretariat.service.PatientAdresseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patientAdresses")
@RequiredArgsConstructor
@Log4j2
public class PatientAdresseController {

    private final PatientAdresseService patientAdresseService;

    @GetMapping
    public ResponseEntity<List<PatientAdresse>> getAllPatientAdresses() {

        log.info("PatientAdresseController | getAllPatientAdresses is called");
        return new ResponseEntity<>(patientAdresseService.getAllPatientAdresses(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addPatientAdresse(@RequestBody PatientAdresseRequest patientAdresseRequest) {

        log.info("PatientAdresseController | addPatientAdresse is called");

        log.info("PatientAdresseController | addPatientAdresse | patientAdresseRequest : " + patientAdresseRequest.toString());

        long patientAdresseId = patientAdresseService.addPatientAdresse(patientAdresseRequest);
        return new ResponseEntity<>(patientAdresseId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientAdresseResponse> getPatientAdresseById(@PathVariable("id") long patientAdresseId) {

        log.info("PatientAdresseController | getPatientAdresseById is called");

        log.info("PatientAdresseController | getPatientAdresseById | patientAdresseId : " + patientAdresseId);

        PatientAdresseResponse patientAdresseResponse
                = patientAdresseService.getPatientAdresseById(patientAdresseId);
        return new ResponseEntity<>(patientAdresseResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editPatientAdresse(@RequestBody PatientAdresseRequest patientAdresseRequest,
            @PathVariable("id") long patientAdresseId
    ) {

        log.info("PatientAdresseController | editPatientAdresse is called");

        log.info("PatientAdresseController | editPatientAdresse | patientAdresseId : " + patientAdresseId);

        patientAdresseService.editPatientAdresse(patientAdresseRequest, patientAdresseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deletePatientAdresseById(@PathVariable("id") long patientAdresseId) {
        patientAdresseService.deletePatientAdresseById(patientAdresseId);
    }
}