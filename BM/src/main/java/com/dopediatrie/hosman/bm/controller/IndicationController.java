package com.dopediatrie.hosman.bm.controller;

import com.dopediatrie.hosman.bm.entity.Indication;
import com.dopediatrie.hosman.bm.payload.request.NameRequest;
import com.dopediatrie.hosman.bm.payload.response.NameResponse;
import com.dopediatrie.hosman.bm.service.IndicationService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/indications")
@RequiredArgsConstructor
@Log4j2
public class IndicationController {

    private final IndicationService indicationService;

    @GetMapping
    public ResponseEntity<List<Indication>> getAllIndications() {

        log.info("IndicationController | getAllIndications is called");
        return new ResponseEntity<>(indicationService.getAllIndications(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addIndication(@RequestBody NameRequest indicationRequest) {

        log.info("IndicationController | addIndication is called");

        log.info("IndicationController | addIndication | indicationRequest : " + indicationRequest.toString());

        long indicationId = indicationService.addIndication(indicationRequest);
        return new ResponseEntity<>(indicationId, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<String>> getIndicationBySearch(@RequestParam("q") String q) {
        log.info("IndicationController | getIndicationBySearch is called");
        List<String> indications = indicationService.getIndicationLike(q);
        return new ResponseEntity<>(indications, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NameResponse> getIndicationById(@PathVariable("id") long indicationId) {
        log.info("IndicationController | getIndicationById is called");
        log.info("IndicationController | getIndicationById | indicationId : " + indicationId);

        NameResponse indicationResponse
                = indicationService.getIndicationById(indicationId);
        return new ResponseEntity<>(indicationResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editIndication(@RequestBody NameRequest indicationRequest,
            @PathVariable("id") long indicationId
    ) {

        log.info("IndicationController | editIndication is called");

        log.info("IndicationController | editIndication | indicationId : " + indicationId);

        indicationService.editIndication(indicationRequest, indicationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteIndicationById(@PathVariable("id") long indicationId) {
        indicationService.deleteIndicationById(indicationId);
    }
}