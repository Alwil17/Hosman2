package com.dopediatrie.hosman.hospi.controller;

import com.dopediatrie.hosman.hospi.payload.request.AttenteRequest;
import com.dopediatrie.hosman.hospi.payload.response.AttenteResponse;
import com.dopediatrie.hosman.hospi.service.AttenteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/attentes")
@RequiredArgsConstructor
@Log4j2
public class AttenteController {

    private final AttenteService attenteService;

    @GetMapping
    public ResponseEntity<List<AttenteResponse>> getAllAttentes() {

        log.info("AttenteController | getAllAttentes is called");
        return new ResponseEntity<>(attenteService.getAllAttentes(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AttenteResponse>> getAttenteBySearch(@RequestHeader(value = "user_id", required = false) Long user_id, @RequestParam(value = "vue") String vue, @RequestParam(value = "medecin", required = false) String medecin) {
        log.info("AttenteController | getAttenteById is called");
        List<AttenteResponse> attentes = Collections.emptyList();
        long userId = (user_id != null && user_id != 0) ? user_id : 1;
        //System.out.println(userId);
        attentes = attenteService.getAttenteFor(vue, medecin);
        System.out.println(attentes.size());
        return new ResponseEntity<>(attentes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttenteResponse> getAttenteById(@PathVariable("id") long attenteId) {
        log.info("AttenteController | getAttenteById is called");
        log.info("AttenteController | getAttenteById | attenteId : " + attenteId);

        AttenteResponse attenteResponse
                = attenteService.getAttenteById(attenteId);
        return new ResponseEntity<>(attenteResponse, HttpStatus.OK);
    }

    @GetMapping("/num/{id}")
    public ResponseEntity<AttenteResponse> getAttenteByNum(@PathVariable("id") long attenteNum) {
        log.info("AttenteController | getAttenteByNum is called");
        log.info("AttenteController | getAttenteByNum | attenteNum : " + attenteNum);

        AttenteResponse attenteResponse
                = attenteService.getAttenteByNum(attenteNum);
        return new ResponseEntity<>(attenteResponse, HttpStatus.OK);
    }

}