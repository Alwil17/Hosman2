package com.dopediatrie.hosman.hospi.controller;

import com.dopediatrie.hosman.hospi.entity.Scam;
import com.dopediatrie.hosman.hospi.payload.request.ScamRequest;
import com.dopediatrie.hosman.hospi.payload.response.ScamResponse;
import com.dopediatrie.hosman.hospi.service.ScamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scams")
@RequiredArgsConstructor
@Log4j2
public class ScamController {

    private final ScamService scamService;

    @GetMapping
    public ResponseEntity<List<Scam>> getAllScams() {

        log.info("ScamController | getAllScams is called");
        return new ResponseEntity<>(scamService.getAllScams(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addScam(@RequestBody ScamRequest scamRequest) {

        log.info("ScamController | addScam is called");

        log.info("ScamController | addScam | scamRequest : " + scamRequest.toString());

        long scamId = scamService.addScam(scamRequest);
        return new ResponseEntity<>(scamId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScamResponse> getScamById(@PathVariable("id") long scamId) {

        log.info("ScamController | getScamById is called");

        log.info("ScamController | getScamById | scamId : " + scamId);

        ScamResponse scamResponse
                = scamService.getScamById(scamId);
        return new ResponseEntity<>(scamResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editScam(@RequestBody ScamRequest scamRequest,
            @PathVariable("id") long scamId
    ) {

        log.info("ScamController | editScam is called");

        log.info("ScamController | editScam | scamId : " + scamId);

        scamService.editScam(scamRequest, scamId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteScamById(@PathVariable("id") long scamId) {
        scamService.deleteScamById(scamId);
    }
}