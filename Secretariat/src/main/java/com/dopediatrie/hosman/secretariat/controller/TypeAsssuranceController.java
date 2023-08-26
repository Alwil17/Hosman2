package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.TypeAssurance;
import com.dopediatrie.hosman.secretariat.payload.request.NameRequest;
import com.dopediatrie.hosman.secretariat.payload.response.NameResponse;
import com.dopediatrie.hosman.secretariat.service.TypeAssuranceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/typeAssurances")
@RequiredArgsConstructor
@Log4j2
public class TypeAsssuranceController {

    private final TypeAssuranceService typeAsssuranceService;

    @GetMapping
    public ResponseEntity<List<TypeAssurance>> getAllTypeAssurances() {

        log.info("TypeAssuranceController | getAllTypeAssurances is called");
        return new ResponseEntity<>(typeAsssuranceService.getAllTypeAssurances(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addTypeAssurance(@RequestBody NameRequest nameRequest) {
        log.info("TypeAssuranceController | addTypeAssurance is called");
        log.info("TypeAssuranceController | addTypeAssurance | nameRequest : " + nameRequest.toString());

        long typeAsssuranceId = typeAsssuranceService.addTypeAssurance(nameRequest);
        return new ResponseEntity<>(typeAsssuranceId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NameResponse> getTypeAssuranceById(@PathVariable("id") long typeAsssuranceId) {

        log.info("TypeAssuranceController | getTypeAssuranceById is called");

        log.info("TypeAssuranceController | getTypeAssuranceById | typeAsssuranceId : " + typeAsssuranceId);

        NameResponse typeAsssuranceResponse
                = typeAsssuranceService.getTypeAssuranceById(typeAsssuranceId);
        return new ResponseEntity<>(typeAsssuranceResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editTypeAssurance(@RequestBody NameRequest typeAsssuranceRequest,
            @PathVariable("id") long typeAsssuranceId
    ) {

        log.info("TypeAssuranceController | editTypeAssurance is called");

        log.info("TypeAssuranceController | editTypeAssurance | typeAsssuranceId : " + typeAsssuranceId);

        typeAsssuranceService.editTypeAssurance(typeAsssuranceRequest, typeAsssuranceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteTypeAssuranceById(@PathVariable("id") long typeAsssuranceId) {
        typeAsssuranceService.deleteTypeAssuranceById(typeAsssuranceId);
    }
}