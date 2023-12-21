package com.dopediatrie.hosman.hospi.controller;

import com.dopediatrie.hosman.hospi.entity.Hospit;
import com.dopediatrie.hosman.hospi.payload.request.HospitRequest;
import com.dopediatrie.hosman.hospi.payload.response.HospitResponse;
import com.dopediatrie.hosman.hospi.service.HospitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hospits")
@RequiredArgsConstructor
@Log4j2
public class HospitController {

    private final HospitService hospitService;

    @GetMapping
    public ResponseEntity<List<Hospit>> getAllHospits() {

        log.info("HospitController | getAllHospits is called");
        return new ResponseEntity<>(hospitService.getAllHospits(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addHospit(@RequestBody HospitRequest hospitRequest) {

        log.info("HospitController | addHospit is called");

        log.info("HospitController | addHospit | hospitRequest : " + hospitRequest.toString());

        long hospitId = hospitService.addHospit(hospitRequest);
        return new ResponseEntity<>(hospitId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospitResponse> getHospitById(@PathVariable("id") long hospitId) {

        log.info("HospitController | getHospitById is called");

        log.info("HospitController | getHospitById | hospitId : " + hospitId);

        HospitResponse hospitResponse
                = hospitService.getHospitById(hospitId);
        return new ResponseEntity<>(hospitResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editHospit(@RequestBody HospitRequest hospitRequest,
            @PathVariable("id") long hospitId
    ) {

        log.info("HospitController | editHospit is called");

        log.info("HospitController | editHospit | hospitId : " + hospitId);

        hospitService.editHospit(hospitRequest, hospitId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteHospitById(@PathVariable("id") long hospitId) {
        hospitService.deleteHospitById(hospitId);
    }
}