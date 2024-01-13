package com.dopediatrie.hosman.hospi.controller;

import com.dopediatrie.hosman.hospi.entity.MedExterne;
import com.dopediatrie.hosman.hospi.payload.request.MedExterneRequest;
import com.dopediatrie.hosman.hospi.payload.response.MedExterneResponse;
import com.dopediatrie.hosman.hospi.service.MedExterneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/med-externes")
@RequiredArgsConstructor
@Log4j2
public class MedExterneController {

    private final MedExterneService medExterneService;

    @GetMapping
    public ResponseEntity<List<MedExterne>> getAllMedExternes() {

        log.info("MedExterneController | getAllMedExternes is called");
        return new ResponseEntity<>(medExterneService.getAllMedExternes(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addMedExterne(@RequestBody MedExterneRequest medExterneRequest) {
        log.info("MedExterneController | addMedExterne is called");
        log.info("MedExterneController | addMedExterne | medExterneRequest : " + medExterneRequest.toString());

        long medExterneId = medExterneService.addMedExterne(medExterneRequest);
        return new ResponseEntity<>(medExterneId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedExterneResponse> getMedExterneById(@PathVariable("id") long medExterneId) {

        log.info("MedExterneController | getMedExterneById is called");

        log.info("MedExterneController | getMedExterneById | medExterneId : " + medExterneId);

        MedExterneResponse medExterneResponse
                = medExterneService.getMedExterneById(medExterneId);
        return new ResponseEntity<>(medExterneResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editMedExterne(@RequestBody MedExterneRequest medExterneRequest,
            @PathVariable("id") long medExterneId
    ) {

        log.info("MedExterneController | editMedExterne is called");

        log.info("MedExterneController | editMedExterne | medExterneId : " + medExterneId);

        medExterneService.editMedExterne(medExterneRequest, medExterneId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteMedExterneById(@PathVariable("id") long medExterneId) {
        medExterneService.deleteMedExterneById(medExterneId);
    }
}