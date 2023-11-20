package com.dopediatrie.hosman.bm.controller;

import com.dopediatrie.hosman.bm.entity.Intervention;
import com.dopediatrie.hosman.bm.payload.request.InterventionRequest;
import com.dopediatrie.hosman.bm.payload.response.InterventionResponse;
import com.dopediatrie.hosman.bm.service.InterventionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/interventions")
@RequiredArgsConstructor
@Log4j2
public class InterventionController {

    private final InterventionService interventionService;

    @GetMapping
    public ResponseEntity<List<Intervention>> getAllInterventions() {

        log.info("InterventionController | getAllInterventions is called");
        return new ResponseEntity<>(interventionService.getAllInterventions(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addIntervention(@RequestBody InterventionRequest interventionRequest) {

        log.info("InterventionController | addIntervention is called");
        log.info("InterventionController | addIntervention | interventionRequest : " + interventionRequest.toString());

        long interventionId = interventionService.addIntervention(interventionRequest);
        return new ResponseEntity<>(interventionId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterventionResponse> getInterventionById(@PathVariable("id") long interventionId) {

        log.info("InterventionController | getInterventionById is called");

        log.info("InterventionController | getInterventionById | interventionId : " + interventionId);

        InterventionResponse interventionResponse
                = interventionService.getInterventionById(interventionId);
        return new ResponseEntity<>(interventionResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editIntervention(@RequestBody InterventionRequest interventionRequest,
            @PathVariable("id") long interventionId
    ) {

        log.info("InterventionController | editIntervention is called");

        log.info("InterventionController | editIntervention | interventionId : " + interventionId);

        interventionService.editIntervention(interventionRequest, interventionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteInterventionById(@PathVariable("id") long interventionId) {
        interventionService.deleteInterventionById(interventionId);
    }
}