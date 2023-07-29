package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.AssuranceTarif;
import com.dopediatrie.hosman.secretariat.payload.request.AssuranceTarifRequest;
import com.dopediatrie.hosman.secretariat.payload.response.AssuranceTarifResponse;
import com.dopediatrie.hosman.secretariat.service.AssuranceTarifService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assuranceTarifs")
@RequiredArgsConstructor
@Log4j2
public class AssuranceTarifController {

    private final AssuranceTarifService assuranceTarifService;

    @GetMapping
    public ResponseEntity<List<AssuranceTarif>> getAllAssuranceTarifs() {

        log.info("AssuranceTarifController | getAllAssuranceTarifs is called");
        return new ResponseEntity<>(assuranceTarifService.getAllAssuranceTarifs(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addAssuranceTarif(@RequestBody AssuranceTarifRequest assuranceTarifRequest) {

        log.info("AssuranceTarifController | addAssuranceTarif is called");

        log.info("AssuranceTarifController | addAssuranceTarif | assuranceTarifRequest : " + assuranceTarifRequest.toString());

        long assuranceTarifId = assuranceTarifService.addAssuranceTarif(assuranceTarifRequest);
        return new ResponseEntity<>(assuranceTarifId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssuranceTarifResponse> getAssuranceTarifById(@PathVariable("id") long assuranceTarifId) {

        log.info("AssuranceTarifController | getAssuranceTarifById is called");

        log.info("AssuranceTarifController | getAssuranceTarifById | assuranceTarifId : " + assuranceTarifId);

        AssuranceTarifResponse assuranceTarifResponse
                = assuranceTarifService.getAssuranceTarifById(assuranceTarifId);
        return new ResponseEntity<>(assuranceTarifResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editAssuranceTarif(@RequestBody AssuranceTarifRequest assuranceTarifRequest,
            @PathVariable("id") long assuranceTarifId
    ) {

        log.info("AssuranceTarifController | editAssuranceTarif is called");

        log.info("AssuranceTarifController | editAssuranceTarif | assuranceTarifId : " + assuranceTarifId);

        assuranceTarifService.editAssuranceTarif(assuranceTarifRequest, assuranceTarifId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteAssuranceTarifById(@PathVariable("id") long assuranceTarifId) {
        assuranceTarifService.deleteAssuranceTarifById(assuranceTarifId);
    }
}