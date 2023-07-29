package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Quartier;
import com.dopediatrie.hosman.secretariat.payload.request.NameRequest;
import com.dopediatrie.hosman.secretariat.payload.response.NameResponse;
import com.dopediatrie.hosman.secretariat.service.QuartierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quartiers")
@RequiredArgsConstructor
@Log4j2
public class QuartierController {

    private final QuartierService quartierService;

    @GetMapping
    public ResponseEntity<List<Quartier>> getAllQuartiers() {

        log.info("QuartierController | getAllQuartiers is called");
        return new ResponseEntity<>(quartierService.getAllQuartiers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addQuartier(@RequestBody NameRequest quartierRequest) {

        log.info("QuartierController | addQuartier is called");

        log.info("QuartierController | addQuartier | quartierRequest : " + quartierRequest.toString());

        long quartierId = quartierService.addQuartier(quartierRequest);
        return new ResponseEntity<>(quartierId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NameResponse> getQuartierById(@PathVariable("id") long quartierId) {

        log.info("QuartierController | getQuartierById is called");

        log.info("QuartierController | getQuartierById | quartierId : " + quartierId);

        NameResponse quartierResponse
                = quartierService.getQuartierById(quartierId);
        return new ResponseEntity<>(quartierResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editQuartier(@RequestBody NameRequest quartierRequest,
            @PathVariable("id") long quartierId
    ) {

        log.info("QuartierController | editQuartier is called");

        log.info("QuartierController | editQuartier | quartierId : " + quartierId);

        quartierService.editQuartier(quartierRequest, quartierId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteQuartierById(@PathVariable("id") long quartierId) {
        quartierService.deleteQuartierById(quartierId);
    }
}