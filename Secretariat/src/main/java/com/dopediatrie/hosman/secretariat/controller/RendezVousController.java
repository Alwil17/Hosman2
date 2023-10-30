package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.RendezVous;
import com.dopediatrie.hosman.secretariat.payload.request.RendezVousRequest;
import com.dopediatrie.hosman.secretariat.payload.response.RendezVousResponse;
import com.dopediatrie.hosman.secretariat.service.RendezVousService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rdvs")
@RequiredArgsConstructor
@Log4j2
public class RendezVousController {

    private final RendezVousService rdvService;

    @GetMapping
    public ResponseEntity<List<RendezVous>> getAllRendezVouss() {

        log.info("RendezVousController | getAllRendezVous is called");
        return new ResponseEntity<>(rdvService.getAllRendezVous(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addRendezVous(@RequestBody RendezVousRequest rdvRequest) {

        log.info("RendezVousController | addRendezVous is called");

        log.info("RendezVousController | addRendezVous | rdvRequest : " + rdvRequest.toString());

        long rdvId = rdvService.addRendezVous(rdvRequest);
        return new ResponseEntity<>(rdvId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RendezVousResponse> getRendezVousById(@PathVariable("id") long rdvId) {

        log.info("RendezVousController | getRendezVousById is called");

        log.info("RendezVousController | getRendezVousById | rdvId : " + rdvId);

        RendezVousResponse rdvResponse
                = rdvService.getRendezVousById(rdvId);
        return new ResponseEntity<>(rdvResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editRendezVous(@RequestBody RendezVousRequest rdvRequest,
            @PathVariable("id") long rdvId
    ) {

        log.info("RendezVousController | editRendezVous is called");

        log.info("RendezVousController | editRendezVous | rdvId : " + rdvId);

        rdvService.editRendezVous(rdvRequest, rdvId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelRendezVous(@PathVariable("id") long rdvId) {

        log.info("RendezVousController | cancelRendezVous is called");
        log.info("RendezVousController | cancelRendezVous | rdvId : " + rdvId);

        rdvService.cancelRendezVous(rdvId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteRendezVousById(@PathVariable("id") long rdvId) {
        rdvService.deleteRendezVousById(rdvId);
    }
}