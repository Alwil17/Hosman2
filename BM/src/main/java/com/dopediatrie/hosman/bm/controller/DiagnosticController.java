package com.dopediatrie.hosman.bm.controller;

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

    @GetMapping("/search")
    public ResponseEntity<List<DiagnosticResponse>> getDiagnosticBySearch(@RequestParam(value = "libelle") String libelle) {
        log.info("DiagnosticController | getDiagnosticBySearch is called");
        List<DiagnosticResponse> diagnosticResponse = Collections.emptyList();

        if(libelle != null && !libelle.isBlank())
            diagnosticResponse = diagnosticService.getDiagnosticByLibelle(libelle);

        return new ResponseEntity<>(diagnosticResponse, HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<DiagnosticResponse> getDiagnosticByCode(@PathVariable("code") String code) {
        log.info("DiagnosticController | getDiagnosticBySearch is called");
        DiagnosticResponse diagnosticResponse = new DiagnosticResponse();

        if(code != null && !code.isBlank())
            diagnosticResponse = diagnosticService.getDiagnosticByCode(code);

        return new ResponseEntity<>(diagnosticResponse, HttpStatus.OK);
    }
}