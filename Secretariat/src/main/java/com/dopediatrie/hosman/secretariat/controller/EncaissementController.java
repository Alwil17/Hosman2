package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Assurance;
import com.dopediatrie.hosman.secretariat.payload.request.AssuranceRequest;
import com.dopediatrie.hosman.secretariat.payload.response.AssuranceResponse;
import com.dopediatrie.hosman.secretariat.service.AssuranceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assurances")
@RequiredArgsConstructor
@Log4j2
public class AssuranceController {

    private final AssuranceService assuranceService;

    @GetMapping
    public ResponseEntity<List<Assurance>> getAllAssurances() {

        log.info("AssuranceController | getAllAssurances is called");
        return new ResponseEntity<>(assuranceService.getAllAssurances(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addAssurance(@RequestBody AssuranceRequest assuranceRequest) {

        log.info("AssuranceController | addAssurance is called");

        log.info("AssuranceController | addAssurance | assuranceRequest : " + assuranceRequest.toString());

        long assuranceId = assuranceService.addAssurance(assuranceRequest);
        return new ResponseEntity<>(assuranceId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssuranceResponse> getAssuranceById(@PathVariable("id") long assuranceId) {

        log.info("AssuranceController | getAssuranceById is called");

        log.info("AssuranceController | getAssuranceById | assuranceId : " + assuranceId);

        AssuranceResponse assuranceResponse
                = assuranceService.getAssuranceById(assuranceId);
        return new ResponseEntity<>(assuranceResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editAssurance(@RequestBody AssuranceRequest assuranceRequest,
            @PathVariable("id") long assuranceId
    ) {

        log.info("AssuranceController | editAssurance is called");

        log.info("AssuranceController | editAssurance | assuranceId : " + assuranceId);

        assuranceService.editAssurance(assuranceRequest, assuranceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteAssuranceById(@PathVariable("id") long assuranceId) {
        assuranceService.deleteAssuranceById(assuranceId);
    }
}