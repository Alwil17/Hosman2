package com.dopediatrie.hosman.hospi.controller;

import com.dopediatrie.hosman.hospi.payload.response.PatientResponse;
import com.dopediatrie.hosman.hospi.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Log4j2
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientResponse>> getAllPatients() {

        log.info("PatientController | getAllPatients is called");
        return new ResponseEntity<>(patientService.getAllPatients(), HttpStatus.OK);
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
    public ResponseEntity<PatientResponse> getPatientByRef(@PathVariable("ref") String patientRef) {

        log.info("PatientController | getPatientByRef is called");

        PatientResponse patientResponse
                = patientService.getPatientByRef(patientRef);
        return new ResponseEntity<>(patientResponse, HttpStatus.OK);
    }
}