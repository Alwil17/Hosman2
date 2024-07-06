package com.dopediatrie.hosman.bm.controller;

import com.dopediatrie.hosman.bm.entity.Agence;
import com.dopediatrie.hosman.bm.payload.request.AgenceRequest;
import com.dopediatrie.hosman.bm.payload.response.AgenceResponse;
import com.dopediatrie.hosman.bm.service.AgenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
@Log4j2
public class AgenceController {

    private final AgenceService agenceService;

    @GetMapping
    public ResponseEntity<List<Agence>> getAllAgences() {

        log.info("AgenceController | getAllAgences is called");
        return new ResponseEntity<>(agenceService.getAllAgences(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addAgence(@RequestBody AgenceRequest agenceRequest) {

        log.info("AgenceController | addAgence is called");

        log.info("AgenceController | addAgence | agenceRequest : " + agenceRequest.toString());

        long agenceId = agenceService.addAgence(agenceRequest);
        return new ResponseEntity<>(agenceId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgenceResponse> getAgenceById(@PathVariable("id") long agenceId) {

        log.info("AgenceController | getAgenceById is called");

        log.info("AgenceController | getAgenceById | agenceId : " + agenceId);

        AgenceResponse agenceResponse
                = agenceService.getAgenceById(agenceId);
        return new ResponseEntity<>(agenceResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Agence>> getAgenceBySearch(@RequestParam("q") String q) {
        log.info("AgenceController | getAgenceBySearch is called");

        List<Agence> agences = new ArrayList<>();
        if(q != null && !q.isBlank()){
            agences = agenceService.getAgenceByQueryString(q);
        }
        return new ResponseEntity<>(agences, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> editAgence(@RequestBody AgenceRequest agenceRequest,
            @PathVariable("id") long agenceId
    ) {

        log.info("AgenceController | editAgence is called");

        log.info("AgenceController | editAgence | agenceId : " + agenceId);

        agenceService.editAgence(agenceRequest, agenceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteAgenceById(@PathVariable("id") long agenceId) {
        agenceService.deleteAgenceById(agenceId);
    }
}