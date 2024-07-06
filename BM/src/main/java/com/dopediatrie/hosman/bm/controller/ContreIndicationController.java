package com.dopediatrie.hosman.bm.controller;

import com.dopediatrie.hosman.bm.entity.ContreIndication;
import com.dopediatrie.hosman.bm.payload.request.NameRequest;
import com.dopediatrie.hosman.bm.payload.response.NameResponse;
import com.dopediatrie.hosman.bm.service.ContreIndicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contreIndications")
@RequiredArgsConstructor
@Log4j2
public class ContreIndicationController {

    private final ContreIndicationService contreIndicationService;

    @GetMapping
    public ResponseEntity<List<ContreIndication>> getAllContreIndications() {

        log.info("ContreIndicationController | getAllContreIndications is called");
        return new ResponseEntity<>(contreIndicationService.getAllContreIndications(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addContreIndication(@RequestBody NameRequest contreIndicationRequest) {

        log.info("ContreIndicationController | addContreIndication is called");

        log.info("ContreIndicationController | addContreIndication | contreIndicationRequest : " + contreIndicationRequest.toString());

        long contreIndicationId = contreIndicationService.addContreIndication(contreIndicationRequest);
        return new ResponseEntity<>(contreIndicationId, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<String>> getContreIndicationBySearch(@RequestParam("q") String q) {
        log.info("ContreIndicationController | getContreIndicationBySearch is called");
        List<String> contreIndications = contreIndicationService.getContreIndicationLike(q);
        return new ResponseEntity<>(contreIndications, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NameResponse> getContreIndicationById(@PathVariable("id") long contreIndicationId) {
        log.info("ContreIndicationController | getContreIndicationById is called");
        log.info("ContreIndicationController | getContreIndicationById | contreIndicationId : " + contreIndicationId);

        NameResponse contreIndicationResponse
                = contreIndicationService.getContreIndicationById(contreIndicationId);
        return new ResponseEntity<>(contreIndicationResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editContreIndication(@RequestBody NameRequest contreIndicationRequest,
            @PathVariable("id") long contreIndicationId
    ) {

        log.info("ContreIndicationController | editContreIndication is called");

        log.info("ContreIndicationController | editContreIndication | contreIndicationId : " + contreIndicationId);

        contreIndicationService.editContreIndication(contreIndicationRequest, contreIndicationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteContreIndicationById(@PathVariable("id") long contreIndicationId) {
        contreIndicationService.deleteContreIndicationById(contreIndicationId);
    }
}