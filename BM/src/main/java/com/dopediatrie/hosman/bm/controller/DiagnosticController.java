package com.dopediatrie.hosman.bm.controller;

import com.dopediatrie.hosman.bm.payload.request.ConsultationDiagnosticRequest;
import com.dopediatrie.hosman.bm.payload.request.ConsultationRequest;
import com.dopediatrie.hosman.bm.payload.response.ConsultationDiagnosticResponse;
import com.dopediatrie.hosman.bm.payload.response.ConsultationResponse;
import com.dopediatrie.hosman.bm.payload.response.DiagnosticResponse;
import com.dopediatrie.hosman.bm.service.DiagnosticService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

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

    @GetMapping("/token")
    public ResponseEntity<String> getToken() {
        log.info("DiagnosticController | getDiagnosticBySearch is called");
        String token = diagnosticService.getToken();
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<List<ConsultationDiagnosticResponse>> getDiagnosticCountBySearch(@RequestParam("datemin") String datemin, @RequestParam(value = "datemax", required = false) String datemax) {
        log.info("DiagnosticController | getDiagnosticCountBySearch is called");
        List<ConsultationDiagnosticResponse> consultations;
        LocalDateTime dateDebut;
        LocalDateTime dateFin;

        // déterminer la dateDebut et dateFin
        if(datemin != null){
            String dD = datemin+"T00:00:00";
            String dF;
            if(datemax == null){
                dF = datemin + "T23:59:59";
            }else{
                dF = datemax + "T23:59:59";
            }
            dateDebut = LocalDateTime.parse(dD);
            dateFin = LocalDateTime.parse(dF).plusDays(7);
        }
        else {
            dateDebut = LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.MIN);
            dateFin = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        }

        // à ce stade, dateDebut et dateFin sont déjà déterminés
        consultations = diagnosticService.getDiagnosticCountByDateRange(dateDebut, dateFin);


        return new ResponseEntity<>(consultations, HttpStatus.OK);
    }

}