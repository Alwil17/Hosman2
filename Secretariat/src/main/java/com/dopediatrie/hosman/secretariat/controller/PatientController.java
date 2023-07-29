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

    @PutMapping("/{id}")
    public ResponseEntity<Void> editPatient(@RequestBody PatientRequest patientRequest,
            @PathVariable("id") long patientId
    ) {

        log.info("PatientController | editPatient is called");

        log.info("PatientController | editPatient | patientId : " + patientId);

        patientService.editPatient(patientRequest, patientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deletePatientById(@PathVariable("id") long patientId) {
        patientService.deletePatientById(patientId);
    }
}