package com.dopediatrie.hosman.bm.controller;

import com.dopediatrie.hosman.bm.entity.Forme;
import com.dopediatrie.hosman.bm.payload.request.FormeRequest;
import com.dopediatrie.hosman.bm.payload.response.FormeResponse;
import com.dopediatrie.hosman.bm.service.FormeService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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

    @GetMapping("/search")
    public ResponseEntity<List<String>> getFormeBySearch(@Schema(type = "string", allowableValues = {"presentation", "dosage", "condition"}) @RequestParam("criteria") String criteria, @RequestParam("q") String q) {
        log.info("FormeController | getFormeBySearch is called");
        List<String> formes = Collections.emptyList();
        switch (criteria){
            case "presentation":
                formes = formeService.getPresentationLike(q);
                break;
            case "dosage":
                formes = formeService.getDosageLike(q);
                break;
            case "condition":
                formes = formeService.getConditionnementLike(q);
                break;
            default:
                break;
        }
        return new ResponseEntity<>(formes, HttpStatus.OK);
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