package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.payload.request.MedecinRequest;
import com.dopediatrie.hosman.secretariat.payload.response.MedecinResponse;
import com.dopediatrie.hosman.secretariat.service.MedecinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medecins")
@RequiredArgsConstructor
@Log4j2
public class MedecinController {

    private final MedecinService medecinService;

    @GetMapping
    public ResponseEntity<List<MedecinResponse>> getAllMedecins() {
        log.info("MedecinController | getAllMedecins is called");
        return new ResponseEntity<>(medecinService.getAllMedecins(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addMedecin(@RequestBody MedecinRequest medecinRequest) {

        log.info("MedecinController | addMedecin is called");
        log.info("MedecinController | addMedecin | medecinRequest : " + medecinRequest.toString());

        String medecinId = medecinService.addMedecin(medecinRequest);
        return new ResponseEntity<>(medecinId, HttpStatus.CREATED);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<MedecinResponse>> getMedecinByType(@PathVariable("type") String typeMedecin) {
        log.info("MedecinController | getMedecinByType is called");
        log.info("MedecinController | getMedecinBytype | medecinType : " + typeMedecin);
        List<MedecinResponse> medecins = medecinService.getMedecinByType(typeMedecin);
        return new ResponseEntity<>(medecins, HttpStatus.OK);
    }
}