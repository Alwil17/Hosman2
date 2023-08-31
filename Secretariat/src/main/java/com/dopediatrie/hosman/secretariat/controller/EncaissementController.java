package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Encaissement;
import com.dopediatrie.hosman.secretariat.payload.request.EncaissementRequest;
import com.dopediatrie.hosman.secretariat.payload.response.EncaissementResponse;
import com.dopediatrie.hosman.secretariat.service.EncaissementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/encaissements")
@RequiredArgsConstructor
@Log4j2
public class EncaissementController {

    private final EncaissementService encaissementService;

    @GetMapping
    public ResponseEntity<List<Encaissement>> getAllEncaissements() {

        log.info("EncaissementController | getAllEncaissements is called");
        return new ResponseEntity<>(encaissementService.getAllEncaissements(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addEncaissement(@RequestBody EncaissementRequest encaissementRequest) {

        log.info("EncaissementController | addEncaissement is called");

        log.info("EncaissementController | addEncaissement | encaissementRequest : " + encaissementRequest.toString());

        long encaissementId = encaissementService.addEncaissement(encaissementRequest);
        return new ResponseEntity<>(encaissementId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EncaissementResponse> getEncaissementById(@PathVariable("id") long encaissementId) {

        log.info("EncaissementController | getEncaissementById is called");

        log.info("EncaissementController | getEncaissementById | encaissementId : " + encaissementId);

        EncaissementResponse encaissementResponse
                = encaissementService.getEncaissementById(encaissementId);
        return new ResponseEntity<>(encaissementResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editEncaissement(@RequestBody EncaissementRequest encaissementRequest,
            @PathVariable("id") long encaissementId
    ) {

        log.info("EncaissementController | editEncaissement is called");

        log.info("EncaissementController | editEncaissement | encaissementId : " + encaissementId);

        encaissementService.editEncaissement(encaissementRequest, encaissementId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteEncaissementById(@PathVariable("id") long encaissementId) {
        encaissementService.deleteEncaissementById(encaissementId);
    }
}