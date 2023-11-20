package com.dopediatrie.hosman.bm.controller;

import com.dopediatrie.hosman.bm.entity.Diagnostic;
import com.dopediatrie.hosman.bm.payload.request.DiagnosticRequest;
import com.dopediatrie.hosman.bm.payload.response.DiagnosticResponse;
import com.dopediatrie.hosman.bm.service.DiagnosticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/diagnostics")
@RequiredArgsConstructor
@Log4j2
public class DiagnosticController {

    private final DiagnosticService diagnosticService;

    @GetMapping
    public ResponseEntity<List<Diagnostic>> getAllDiagnostics() {

        log.info("DiagnosticController | getAllDiagnostics is called");
        return new ResponseEntity<>(diagnosticService.getAllDiagnostics(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addDiagnostic(@RequestBody DiagnosticRequest diagnosticRequest) {

        log.info("DiagnosticController | addDiagnostic is called");
        log.info("DiagnosticController | addDiagnostic | diagnosticRequest : " + diagnosticRequest.toString());

        long diagnosticId = diagnosticService.addDiagnostic(diagnosticRequest);
        return new ResponseEntity<>(diagnosticId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiagnosticResponse> getDiagnosticById(@PathVariable("id") long diagnosticId) {

        log.info("DiagnosticController | getDiagnosticById is called");

        log.info("DiagnosticController | getDiagnosticById | diagnosticId : " + diagnosticId);

        DiagnosticResponse diagnosticResponse
                = diagnosticService.getDiagnosticById(diagnosticId);
        return new ResponseEntity<>(diagnosticResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editDiagnostic(@RequestBody DiagnosticRequest diagnosticRequest,
            @PathVariable("id") long diagnosticId
    ) {

        log.info("DiagnosticController | editDiagnostic is called");

        log.info("DiagnosticController | editDiagnostic | diagnosticId : " + diagnosticId);

        diagnosticService.editDiagnostic(diagnosticRequest, diagnosticId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteDiagnosticById(@PathVariable("id") long diagnosticId) {
        diagnosticService.deleteDiagnosticById(diagnosticId);
    }
}