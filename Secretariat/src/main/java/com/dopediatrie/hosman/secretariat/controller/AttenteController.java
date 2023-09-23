package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Attente;
import com.dopediatrie.hosman.secretariat.payload.request.AttenteRequest;
import com.dopediatrie.hosman.secretariat.payload.response.AttenteResponse;
import com.dopediatrie.hosman.secretariat.service.AttenteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attentes")
@RequiredArgsConstructor
@Log4j2
public class AttenteController {

    private final AttenteService attenteService;

    @GetMapping
    public ResponseEntity<List<Attente>> getAllAttentes() {

        log.info("AttenteController | getAllAttentes is called");
        return new ResponseEntity<>(attenteService.getAllAttentes(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addAttente(@RequestBody AttenteRequest attenteRequest) {

        log.info("AttenteController | addAttente is called");

        log.info("AttenteController | addAttente | attenteRequest : " + attenteRequest.toString());

        long attenteId = attenteService.addAttente(attenteRequest);
        return new ResponseEntity<>(attenteId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttenteResponse> getAttenteById(@PathVariable("id") long attenteId) {

        log.info("AttenteController | getAttenteById is called");

        log.info("AttenteController | getAttenteById | attenteId : " + attenteId);

        AttenteResponse attenteResponse
                = attenteService.getAttenteById(attenteId);
        return new ResponseEntity<>(attenteResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editAttente(@RequestBody AttenteRequest attenteRequest,
            @PathVariable("id") long attenteId
    ) {

        log.info("AttenteController | editAttente is called");

        log.info("AttenteController | editAttente | attenteId : " + attenteId);

        attenteService.editAttente(attenteRequest, attenteId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteAttenteById(@PathVariable("id") long attenteId) {
        attenteService.deleteAttenteById(attenteId);
    }
}