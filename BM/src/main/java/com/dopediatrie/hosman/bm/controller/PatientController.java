package com.dopediatrie.hosman.bm.controller;

import com.dopediatrie.hosman.bm.payload.request.AttenteRequest;
import com.dopediatrie.hosman.bm.payload.request.PatientRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Log4j2
public class PatientController {
    @Autowired
    private RestTemplate restTemplate;

    private String baseUrl = "http://hosman-apps.com:81/patients";

    @PutMapping("/{ref}/bm")
    public ResponseEntity<Void> editPatientCaracs(@RequestBody PatientRequest patientRequest,
                                            @PathVariable("ref") String reference
    ) {
        log.info("PatientController | editPatientCaracs is called");

        HttpEntity<PatientRequest> request = new HttpEntity<>(patientRequest);

        restTemplate.put(baseUrl+"/ref/"+reference+"/bm", request);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}