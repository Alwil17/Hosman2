package com.dopediatrie.hosman.auth.controller;

import com.dopediatrie.hosman.auth.entity.Departement;
import com.dopediatrie.hosman.auth.payload.request.DepartementRequest;
import com.dopediatrie.hosman.auth.payload.response.DepartementResponse;
import com.dopediatrie.hosman.auth.service.DepartementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departements")
@RequiredArgsConstructor
@Log4j2
public class DepartementController {
    private final DepartementService departementService;

    @GetMapping
    public ResponseEntity<List<Departement>> getAllDepartements() {

        log.info("DepartementController | getAllDepartements is called");
        return new ResponseEntity<>(departementService.getAllDepartements(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addDepartement(@RequestBody DepartementRequest departementRequest) {

        log.info("DepartementController | addDepartement is called");

        log.info("DepartementController | addDepartement | departementRequest : " + departementRequest.toString());

        long departementId = departementService.addDepartement(departementRequest);
        return new ResponseEntity<>(departementId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartementResponse> getDepartementById(@PathVariable("id") long departementId) {

        log.info("DepartementController | getDepartementById is called");

        log.info("DepartementController | getDepartementById | departementId : " + departementId);

        DepartementResponse departementResponse
                = departementService.getDepartementById(departementId);
        return new ResponseEntity<>(departementResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editDepartement(@RequestBody DepartementRequest departementRequest,
                                         @PathVariable("id") long departementId
    ) {

        log.info("DepartementController | editDepartement is called");

        log.info("DepartementController | editDepartement | departementId : " + departementId);

        departementService.editDepartement(departementRequest, departementId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartementById(@PathVariable("id") long departementId) {
        departementService.deleteDepartementById(departementId);
    }
}
