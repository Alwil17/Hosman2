package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Employeur;
import com.dopediatrie.hosman.secretariat.payload.request.EmployeurRequest;
import com.dopediatrie.hosman.secretariat.payload.response.EmployeurResponse;
import com.dopediatrie.hosman.secretariat.service.EmployeurService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employeurs")
@RequiredArgsConstructor
@Log4j2
public class EmployeurController {

    private final EmployeurService employeurService;

    @GetMapping
    public ResponseEntity<List<Employeur>> getAllEmployeurs() {

        log.info("EmployeurController | getAllEmployeurs is called");
        return new ResponseEntity<>(employeurService.getAllEmployeurs(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addEmployeur(@RequestBody EmployeurRequest employeurRequest) {

        log.info("EmployeurController | addEmployeur is called");

        log.info("EmployeurController | addEmployeur | employeurRequest : " + employeurRequest.toString());

        long employeurId = employeurService.addEmployeur(employeurRequest);
        return new ResponseEntity<>(employeurId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeurResponse> getEmployeurById(@PathVariable("id") long employeurId) {

        log.info("EmployeurController | getEmployeurById is called");

        log.info("EmployeurController | getEmployeurById | employeurId : " + employeurId);

        EmployeurResponse employeurResponse
                = employeurService.getEmployeurById(employeurId);
        return new ResponseEntity<>(employeurResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editEmployeur(@RequestBody EmployeurRequest employeurRequest,
            @PathVariable("id") long employeurId
    ) {

        log.info("EmployeurController | editEmployeur is called");

        log.info("EmployeurController | editEmployeur | employeurId : " + employeurId);

        employeurService.editEmployeur(employeurRequest, employeurId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployeurById(@PathVariable("id") long employeurId) {
        employeurService.deleteEmployeurById(employeurId);
    }
}