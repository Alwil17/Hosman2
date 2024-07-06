package com.dopediatrie.hosman.bm.controller;

import com.dopediatrie.hosman.bm.entity.Posologie;
import com.dopediatrie.hosman.bm.payload.request.PosologieRequest;
import com.dopediatrie.hosman.bm.payload.response.PosologieResponse;
import com.dopediatrie.hosman.bm.service.PosologieService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/posologies")
@RequiredArgsConstructor
@Log4j2
public class PosologieController {

    private final PosologieService posologieService;

    @GetMapping
    public ResponseEntity<List<Posologie>> getAllPosologies() {

        log.info("PosologieController | getAllPosologies is called");
        return new ResponseEntity<>(posologieService.getAllPosologies(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addPosologie(@RequestBody PosologieRequest posologieRequest) {

        log.info("PosologieController | addPosologie is called");

        log.info("PosologieController | addPosologie | posologieRequest : " + posologieRequest.toString());

        long posologieId = posologieService.addPosologie(posologieRequest);
        return new ResponseEntity<>(posologieId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PosologieResponse> getPosologieById(@PathVariable("id") long posologieId) {

        log.info("PosologieController | getPosologieById is called");

        log.info("PosologieController | getPosologieById | posologieId : " + posologieId);

        PosologieResponse posologieResponse
                = posologieService.getPosologieById(posologieId);
        return new ResponseEntity<>(posologieResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Posologie>> getPosologieBySearch(@Schema(type = "string", allowableValues = {"enfant", "adulte"}) @RequestParam("type") String type, @RequestParam(required = false, value = "q") String q) {
        log.info("PosologieController | getPosologieById is called");
        List<Posologie> posologies = Collections.emptyList();

       if(q != null && !q.isBlank()){
           posologies = posologieService.getPosologiesByTypeAndQueryString(type, q);
       }else {
           posologies = posologieService.getPosologiesByType(type);
       }

        return new ResponseEntity<>(posologies, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editPosologie(@RequestBody PosologieRequest posologieRequest,
            @PathVariable("id") long posologieId
    ) {

        log.info("PosologieController | editPosologie is called");

        log.info("PosologieController | editPosologie | posologieId : " + posologieId);

        posologieService.editPosologie(posologieRequest, posologieId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deletePosologieById(@PathVariable("id") long posologieId) {
        posologieService.deletePosologieById(posologieId);
    }
}