package com.dopediatrie.hosman.stock.controller;

import com.dopediatrie.hosman.stock.entity.Forme;
import com.dopediatrie.hosman.stock.payload.request.FormeRequest;
import com.dopediatrie.hosman.stock.payload.response.FormeResponse;
import com.dopediatrie.hosman.stock.service.FormeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/formes")
@RequiredArgsConstructor
@Log4j2
public class FormeController {

    private final FormeService formeService;

    @GetMapping
    public ResponseEntity<List<Forme>> getAllFormes() {

        log.info("FormeController | getAllFormes is called");
        return new ResponseEntity<>(formeService.getAllFormes(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addForme(@RequestBody FormeRequest formeRequest) {

        log.info("FormeController | addForme is called");

        log.info("FormeController | addForme | formeRequest : " + formeRequest.toString());

        long formeId = formeService.addForme(formeRequest);
        return new ResponseEntity<>(formeId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormeResponse> getFormeById(@PathVariable("id") long formeId) {

        log.info("FormeController | getFormeById is called");

        log.info("FormeController | getFormeById | formeId : " + formeId);

        FormeResponse formeResponse
                = formeService.getFormeById(formeId);
        return new ResponseEntity<>(formeResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editForme(@RequestBody FormeRequest formeRequest,
            @PathVariable("id") long formeId
    ) {

        log.info("FormeController | editForme is called");

        log.info("FormeController | editForme | formeId : " + formeId);

        formeService.editForme(formeRequest, formeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteFormeById(@PathVariable("id") long formeId) {
        formeService.deleteFormeById(formeId);
    }
}