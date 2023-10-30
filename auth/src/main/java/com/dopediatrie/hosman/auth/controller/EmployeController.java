package com.dopediatrie.hosman.auth.controller;

import com.dopediatrie.hosman.auth.entity.Employe;
import com.dopediatrie.hosman.auth.payload.request.EmployeRequest;
import com.dopediatrie.hosman.auth.payload.response.EmployeResponse;
import com.dopediatrie.hosman.auth.service.EmployeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
@Log4j2
public class EmployeController {
    private final EmployeService employeService;

    @GetMapping
    public ResponseEntity<List<Employe>> getAllEmployes() {

        log.info("EmployeController | getAllEmployes is called");
        return new ResponseEntity<>(employeService.getAllEmployes(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addEmploye(@RequestBody EmployeRequest employeRequest) {

        log.info("EmployeController | addEmploye is called");

        log.info("EmployeController | addEmploye | employeRequest : " + employeRequest.toString());

        long employeId = employeService.addEmploye(employeRequest);
        return new ResponseEntity<>(employeId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeResponse> getEmployeById(@PathVariable("id") long employeId) {

        log.info("EmployeController | getEmployeById is called");

        log.info("EmployeController | getEmployeById | employeId : " + employeId);

        EmployeResponse employeResponse
                = employeService.getEmployeById(employeId);
        return new ResponseEntity<>(employeResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editEmploye(@RequestBody EmployeRequest employeRequest,
                                         @PathVariable("id") long employeId
    ) {

        log.info("EmployeController | editEmploye is called");

        log.info("EmployeController | editEmploye | employeId : " + employeId);

        employeService.editEmploye(employeRequest, employeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployeById(@PathVariable("id") long employeId) {
        employeService.deleteEmployeById(employeId);
    }
}
