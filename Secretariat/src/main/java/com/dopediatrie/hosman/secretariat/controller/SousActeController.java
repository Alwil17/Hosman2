package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.SousActe;
import com.dopediatrie.hosman.secretariat.payload.request.SousActeRequest;
import com.dopediatrie.hosman.secretariat.payload.response.SousActeResponse;
import com.dopediatrie.hosman.secretariat.service.SousActeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sous-actes")
@RequiredArgsConstructor
@Log4j2
public class SousActeController {

    private final SousActeService sousActeService;

    @GetMapping
    public ResponseEntity<List<SousActe>> getAllSousActes() {
        log.info("SousActeController | getAllSousActes is called");
        return new ResponseEntity<>(sousActeService.getAllSousActes(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addSousActe(@RequestBody SousActeRequest sousActeRequest) {

        log.info("SousActeController | addSousActe is called");

        log.info("SousActeController | addSousActe | sousActeRequest : " + sousActeRequest.toString());

        long sousActeId = sousActeService.addSousActe(sousActeRequest);
        return new ResponseEntity<>(sousActeId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SousActeResponse> getSousActeById(@PathVariable("id") long sousActeId) {

        log.info("SousActeController | getSousActeById is called");

        log.info("SousActeController | getSousActeById | sousActeId : " + sousActeId);

        SousActeResponse acteResponse
                = sousActeService.getSousActeById(sousActeId);
        return new ResponseEntity<>(acteResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editSousActe(@RequestBody SousActeRequest sousActeRequest,
            @PathVariable("id") long sousActeId) {

        log.info("SousActeController | editSousActe is called");

        log.info("SousActeController | editSousActe | sousActeId : " + sousActeId);

        sousActeService.editSousActe(sousActeRequest, sousActeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteSousActeById(@PathVariable("id") long sousActeId) {
        sousActeService.deleteSousActeById(sousActeId);
    }
}