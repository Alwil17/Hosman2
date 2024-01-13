package com.dopediatrie.hosman.hospi.controller;

import com.dopediatrie.hosman.hospi.entity.Chirurgie;
import com.dopediatrie.hosman.hospi.payload.request.ChirurgieRequest;
import com.dopediatrie.hosman.hospi.payload.response.ChirurgieResponse;
import com.dopediatrie.hosman.hospi.service.ChirurgieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chirurgies")
@RequiredArgsConstructor
@Log4j2
public class ChirurgieController {

    private final ChirurgieService chirurgieService;

    @GetMapping
    public ResponseEntity<List<Chirurgie>> getAllChirurgies() {

        log.info("ChirurgieController | getAllChirurgies is called");
        return new ResponseEntity<>(chirurgieService.getAllChirurgies(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addChirurgie(@RequestBody ChirurgieRequest chirurgieRequest) {

        log.info("ChirurgieController | addChirurgie is called");

        log.info("ChirurgieController | addChirurgie | chirurgieRequest : " + chirurgieRequest.toString());

        long chirurgieId = chirurgieService.addChirurgie(chirurgieRequest);
        return new ResponseEntity<>(chirurgieId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChirurgieResponse> getChirurgieById(@PathVariable("id") long chirurgieId) {

        log.info("ChirurgieController | getChirurgieById is called");

        log.info("ChirurgieController | getChirurgieById | chirurgieId : " + chirurgieId);

        ChirurgieResponse chirurgieResponse
                = chirurgieService.getChirurgieById(chirurgieId);
        return new ResponseEntity<>(chirurgieResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editChirurgie(@RequestBody ChirurgieRequest chirurgieRequest,
            @PathVariable("id") long chirurgieId
    ) {

        log.info("ChirurgieController | editChirurgie is called");

        log.info("ChirurgieController | editChirurgie | chirurgieId : " + chirurgieId);

        chirurgieService.editChirurgie(chirurgieRequest, chirurgieId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteChirurgieById(@PathVariable("id") long chirurgieId) {
        chirurgieService.deleteChirurgieById(chirurgieId);
    }
}