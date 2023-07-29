package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.InterventionTarif;
import com.dopediatrie.hosman.secretariat.payload.request.InterventionTarifRequest;
import com.dopediatrie.hosman.secretariat.payload.response.InterventionTarifResponse;
import com.dopediatrie.hosman.secretariat.service.InterventionTarifService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interventionTarifs")
@RequiredArgsConstructor
@Log4j2
public class InterventionTarifController {

    private final InterventionTarifService interventionTarifService;

    @GetMapping
    public ResponseEntity<List<InterventionTarif>> getAllInterventionTarifs() {

        log.info("InterventionTarifController | getAllInterventionTarifs is called");
        return new ResponseEntity<>(interventionTarifService.getAllInterventionTarifs(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addInterventionTarif(@RequestBody InterventionTarifRequest interventionTarifRequest) {

        log.info("InterventionTarifController | addInterventionTarif is called");

        log.info("InterventionTarifController | addInterventionTarif | interventionTarifRequest : " + interventionTarifRequest.toString());

        long interventionTarifId = interventionTarifService.addInterventionTarif(interventionTarifRequest);
        return new ResponseEntity<>(interventionTarifId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterventionTarifResponse> getInterventionTarifById(@PathVariable("id") long interventionTarifId) {

        log.info("InterventionTarifController | getInterventionTarifById is called");

        log.info("InterventionTarifController | getInterventionTarifById | interventionTarifId : " + interventionTarifId);

        InterventionTarifResponse interventionTarifResponse
                = interventionTarifService.getInterventionTarifById(interventionTarifId);
        return new ResponseEntity<>(interventionTarifResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editInterventionTarif(@RequestBody InterventionTarifRequest interventionTarifRequest,
            @PathVariable("id") long interventionTarifId
    ) {

        log.info("InterventionTarifController | editInterventionTarif is called");

        log.info("InterventionTarifController | editInterventionTarif | interventionTarifId : " + interventionTarifId);

        interventionTarifService.editInterventionTarif(interventionTarifRequest, interventionTarifId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteInterventionTarifById(@PathVariable("id") long interventionTarifId) {
        interventionTarifService.deleteInterventionTarifById(interventionTarifId);
    }
}