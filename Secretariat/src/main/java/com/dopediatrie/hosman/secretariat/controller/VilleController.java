package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Ville;
import com.dopediatrie.hosman.secretariat.payload.request.NameRequest;
import com.dopediatrie.hosman.secretariat.payload.response.NameResponse;
import com.dopediatrie.hosman.secretariat.service.VilleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/villes")
@RequiredArgsConstructor
@Log4j2
public class VilleController {

    private final VilleService villeService;

    @GetMapping
    public ResponseEntity<List<Ville>> getAllVilles() {

        log.info("VilleController | getAllVilles is called");
        return new ResponseEntity<>(villeService.getAllVilles(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addVille(@RequestBody NameRequest villeRequest) {

        log.info("VilleController | addVille is called");

        log.info("VilleController | addVille | villeRequest : " + villeRequest.toString());

        long villeId = villeService.addVille(villeRequest);
        return new ResponseEntity<>(villeId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NameResponse> getVilleById(@PathVariable("id") long villeId) {

        log.info("VilleController | getVilleById is called");

        log.info("VilleController | getVilleById | villeId : " + villeId);

        NameResponse villeResponse
                = villeService.getVilleById(villeId);
        return new ResponseEntity<>(villeResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editVille(@RequestBody NameRequest villeRequest,
            @PathVariable("id") long villeId
    ) {

        log.info("VilleController | editVille is called");

        log.info("VilleController | editVille | villeId : " + villeId);

        villeService.editVille(villeRequest, villeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteVilleById(@PathVariable("id") long villeId) {
        villeService.deleteVilleById(villeId);
    }
}