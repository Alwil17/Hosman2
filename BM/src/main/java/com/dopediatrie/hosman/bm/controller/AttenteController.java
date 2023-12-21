package com.dopediatrie.hosman.bm.controller;

import com.dopediatrie.hosman.bm.payload.request.AttenteRequest;
import com.dopediatrie.hosman.bm.payload.response.AttenteResponse;
import com.dopediatrie.hosman.bm.service.AttenteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attentes")
@RequiredArgsConstructor
@Log4j2
public class AttenteController {

    private final AttenteService attenteService;

    @PostMapping
    public ResponseEntity<Long> addAttente(@RequestBody AttenteRequest attenteRequest) {

        log.info("AttenteController | addAttente is called");

        log.info("AttenteController | addAttente | attenteRequest : " + attenteRequest.toString());

        long attenteId = attenteService.addAttente(attenteRequest);
        return new ResponseEntity<>(attenteId, HttpStatus.CREATED);
    }

    @GetMapping("/{ref}/update")
    public ResponseEntity<Void> updateAttenteStatus(@RequestHeader(value = "user_id", required = false) Long user_id, @PathVariable("ref") Long attenteRef, @RequestParam("start") boolean start) {
        log.info("AttenteController | updateAttenteStatus is called");
        long userId = (user_id != null && user_id != 0) ? user_id : 1;
        attenteService.updateAttenteStatus(attenteRef, userId, start);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttenteResponse> getAttenteById(@PathVariable("id") long attenteId) {
        log.info("AttenteController | getAttenteById is called");
        log.info("AttenteController | getAttenteById | attenteId : " + attenteId);

        AttenteResponse attenteResponse
                = attenteService.getAttenteById(attenteId);
        return new ResponseEntity<>(attenteResponse, HttpStatus.OK);
    }
}