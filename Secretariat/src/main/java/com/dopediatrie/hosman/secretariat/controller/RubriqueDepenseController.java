package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.RubriqueDepense;
import com.dopediatrie.hosman.secretariat.payload.request.NameRequest;
import com.dopediatrie.hosman.secretariat.payload.response.NameResponse;
import com.dopediatrie.hosman.secretariat.service.RubriqueDepenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rubrique-depenses")
@RequiredArgsConstructor
@Log4j2
public class RubriqueDepenseController {

    private final RubriqueDepenseService rubriqueDepenseService;

    @GetMapping
    public ResponseEntity<List<RubriqueDepense>> getAllRubriqueDepenses() {

        log.info("RubriqueDepenseController | getAllRubriqueDepenses is called");
        return new ResponseEntity<>(rubriqueDepenseService.getAllRubriqueDepenses(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addRubriqueDepense(@RequestBody NameRequest rubriqueDepenseRequest) {

        log.info("RubriqueDepenseController | addRubriqueDepense is called");

        log.info("RubriqueDepenseController | addRubriqueDepense | rubriqueDepenseRequest : " + rubriqueDepenseRequest.toString());

        long rubriqueDepenseId = rubriqueDepenseService.addRubriqueDepense(rubriqueDepenseRequest);
        return new ResponseEntity<>(rubriqueDepenseId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NameResponse> getRubriqueDepenseById(@PathVariable("id") long rubriqueDepenseId) {

        log.info("RubriqueDepenseController | getRubriqueDepenseById is called");

        log.info("RubriqueDepenseController | getRubriqueDepenseById | rubriqueDepenseId : " + rubriqueDepenseId);

        NameResponse rubriqueDepenseResponse
                = rubriqueDepenseService.getRubriqueDepenseById(rubriqueDepenseId);
        return new ResponseEntity<>(rubriqueDepenseResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editRubriqueDepense(@RequestBody NameRequest rubriqueDepenseRequest,
            @PathVariable("id") long rubriqueDepenseId
    ) {

        log.info("RubriqueDepenseController | editRubriqueDepense is called");

        log.info("RubriqueDepenseController | editRubriqueDepense | rubriqueDepenseId : " + rubriqueDepenseId);

        rubriqueDepenseService.editRubriqueDepense(rubriqueDepenseRequest, rubriqueDepenseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteRubriqueDepenseById(@PathVariable("id") long rubriqueDepenseId) {
        rubriqueDepenseService.deleteRubriqueDepenseById(rubriqueDepenseId);
    }
}