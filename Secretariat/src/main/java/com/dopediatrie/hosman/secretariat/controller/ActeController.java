package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Acte;
import com.dopediatrie.hosman.secretariat.payload.request.ActeRequest;
import com.dopediatrie.hosman.secretariat.payload.response.ActeResponse;
import com.dopediatrie.hosman.secretariat.service.ActeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actes")
@RequiredArgsConstructor
@Log4j2
public class ActeController {

    private final ActeService acteService;

    @GetMapping
    public ResponseEntity<List<Acte>> getAllActes() {

        log.info("ActeController | getAllActes is called");
        return new ResponseEntity<>(acteService.getAllActes(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addActe(@RequestBody ActeRequest acteRequest) {

        log.info("ActeController | addActe is called");

        log.info("ActeController | addActe | acteRequest : " + acteRequest.toString());

        long acteId = acteService.addActe(acteRequest);
        return new ResponseEntity<>(acteId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActeResponse> getActeById(@PathVariable("id") long acteId) {

        log.info("ActeController | getActeById is called");

        log.info("ActeController | getActeById | acteId : " + acteId);

        ActeResponse acteResponse
                = acteService.getActeById(acteId);
        return new ResponseEntity<>(acteResponse, HttpStatus.OK);
    }

    @GetMapping("/code/{id}")
    public ResponseEntity<ActeResponse> getActeByCode(@PathVariable("id") String acteCode) {
        log.info("ActeController | getActeByCode is called");
        log.info("ActeController | getActeByCode | acteCode : " + acteCode);

        ActeResponse acteResponse
                = acteService.getActeByCode(acteCode);
        return new ResponseEntity<>(acteResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editActe(@RequestBody ActeRequest acteRequest,
            @PathVariable("id") long acteId
    ) {

        log.info("ActeController | editActe is called");

        log.info("ActeController | editActe | acteId : " + acteId);

        acteService.editActe(acteRequest, acteId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteActeById(@PathVariable("id") long acteId) {
        acteService.deleteActeById(acteId);
    }
}