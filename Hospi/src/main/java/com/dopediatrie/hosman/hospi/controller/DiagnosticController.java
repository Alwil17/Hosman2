package com.dopediatrie.hosman.hospi.controller;

import com.dopediatrie.hosman.hospi.payload.response.AttenteResponse;
import com.dopediatrie.hosman.hospi.service.AttenteService;
import com.dopediatrie.hosman.hospi.service.DiagnosticService;
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

    @GetMapping("token")
    public ResponseEntity<String> getToken() {
        log.info("DiagnosticController | getToken is called");
        String token = diagnosticService.getToken();
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}