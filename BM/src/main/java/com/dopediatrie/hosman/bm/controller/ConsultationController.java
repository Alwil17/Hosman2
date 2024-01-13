package com.dopediatrie.hosman.bm.controller;

import com.dopediatrie.hosman.bm.entity.Consultation;
import com.dopediatrie.hosman.bm.payload.request.ConsultationRequest;
import com.dopediatrie.hosman.bm.payload.response.ConsultationResponse;
import com.dopediatrie.hosman.bm.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultations")
@RequiredArgsConstructor
@Log4j2
public class ConsultationController {

    private final ConsultationService consultationService;

    @GetMapping
    public ResponseEntity<List<Consultation>> getAllConsultations() {

        log.info("ConsultationController | getAllConsultations is called");
        return new ResponseEntity<>(consultationService.getAllConsultations(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addConsultation(@RequestBody ConsultationRequest consultationRequest) {

        log.info("ConsultationController | addConsultation is called");
        log.info("ConsultationController | addConsultation | consultationRequest : " + consultationRequest.toString());

        long consultationId = consultationService.addConsultation(consultationRequest);
        return new ResponseEntity<>(consultationId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultationResponse> getConsultationById(@PathVariable("id") long consultationId) {

        log.info("ConsultationController | getConsultationById is called");

        log.info("ConsultationController | getConsultationById | consultationId : " + consultationId);

        ConsultationResponse consultationResponse
                = consultationService.getConsultationById(consultationId);
        return new ResponseEntity<>(consultationResponse, HttpStatus.OK);
    }

    @GetMapping("/ref/{id}")
    public ResponseEntity<ConsultationResponse> getConsultationByRef(@PathVariable("id") String consultationRef) {

        log.info("ConsultationController | getConsultationByRef is called");
        log.info("ConsultationController | getConsultationByRef | consultationId : " + consultationRef);

        ConsultationResponse consultationResponse
                = consultationService.getConsultationByRef(consultationRef);
        return new ResponseEntity<>(consultationResponse, HttpStatus.OK);
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<ConsultationResponse>> getConsultationByPatientRef(@PathVariable("id") String patient_ref) {
        log.info("ConsultationController | getConsultationByPatientRef is called");

        List<ConsultationResponse> consultationResponses
                = consultationService.getConsultationByPatientRef(patient_ref);

        return new ResponseEntity<>(consultationResponses, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editConsultation(@RequestBody ConsultationRequest consultationRequest,
            @PathVariable("id") long consultationId) {

        log.info("ConsultationController | editConsultation is called");

        log.info("ConsultationController | editConsultation | consultationId : " + consultationId);

        consultationService.editConsultation(consultationRequest, consultationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteConsultationById(@PathVariable("id") long consultationId) {
        consultationService.deleteConsultationById(consultationId);
    }
}