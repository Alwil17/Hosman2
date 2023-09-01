package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Depense;
import com.dopediatrie.hosman.secretariat.payload.request.DepenseRequest;
import com.dopediatrie.hosman.secretariat.payload.response.DepenseResponse;
import com.dopediatrie.hosman.secretariat.service.DepenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/depenses")
@RequiredArgsConstructor
@Log4j2
public class DepenseController {

    private final DepenseService depenseService;

    @GetMapping
    public ResponseEntity<List<Depense>> getAllDepenses() {

        log.info("DepenseController | getAllDepenses is called");
        return new ResponseEntity<>(depenseService.getAllDepenses(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addDepense(@RequestBody DepenseRequest depenseRequest) {

        log.info("DepenseController | addDepense is called");

        log.info("DepenseController | addDepense | depenseRequest : " + depenseRequest.toString());

        long depenseId = depenseService.addDepense(depenseRequest);
        return new ResponseEntity<>(depenseId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepenseResponse> getDepenseById(@PathVariable("id") long depenseId) {

        log.info("DepenseController | getDepenseById is called");

        log.info("DepenseController | getDepenseById | depenseId : " + depenseId);

        DepenseResponse depenseResponse
                = depenseService.getDepenseById(depenseId);
        return new ResponseEntity<>(depenseResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editDepense(@RequestBody DepenseRequest depenseRequest,
            @PathVariable("id") long depenseId
    ) {

        log.info("DepenseController | editDepense is called");

        log.info("DepenseController | editDepense | depenseId : " + depenseId);

        depenseService.editDepense(depenseRequest, depenseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteDepenseById(@PathVariable("id") long depenseId) {
        depenseService.deleteDepenseById(depenseId);
    }
}