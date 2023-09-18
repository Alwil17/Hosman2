package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Medecin;
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
    public ResponseEntity<List<Medecin>> getAllMedecins() {

        log.info("MedecinController | getAllMedecins is called");
        return new ResponseEntity<>(medecinService.getAllMedecins(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addMedecin(@RequestBody MedecinRequest medecinRequest) {

        log.info("MedecinController | addMedecin is called");

        log.info("MedecinController | addMedecin | medecinRequest : " + medecinRequest.toString());

        long medecinId = medecinService.addMedecin(medecinRequest);
        return new ResponseEntity<>(medecinId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedecinResponse> getMedecinById(@PathVariable("id") long medecinId) {

        log.info("MedecinController | getMedecinById is called");

        log.info("MedecinController | getMedecinById | medecinId : " + medecinId);

        MedecinResponse medecinResponse
                = medecinService.getMedecinById(medecinId);
        return new ResponseEntity<>(medecinResponse, HttpStatus.OK);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Medecin>> getMedecinByType(@PathVariable("type") String typeMedecin) {

        log.info("MedecinController | getMedecinByType is called");
        log.info("MedecinController | getMedecinBytype | medecinType : " + typeMedecin);

        List<Medecin> medecins = medecinService.getMedecinByType(typeMedecin);

        return new ResponseEntity<>(medecins, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editMedecin(@RequestBody MedecinRequest medecinRequest,
            @PathVariable("id") long medecinId
    ) {

        log.info("MedecinController | editMedecin is called");

        log.info("MedecinController | editMedecin | medecinId : " + medecinId);

        medecinService.editMedecin(medecinRequest, medecinId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteMedecinById(@PathVariable("id") long medecinId) {
        medecinService.deleteMedecinById(medecinId);
    }
}