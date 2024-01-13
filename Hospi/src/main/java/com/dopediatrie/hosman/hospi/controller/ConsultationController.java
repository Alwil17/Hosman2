package com.dopediatrie.hosman.hospi.controller;

import com.dopediatrie.hosman.hospi.payload.response.ConsultationResponse;
import com.dopediatrie.hosman.hospi.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultations")
@RequiredArgsConstructor
@Log4j2
public class ConsultationController {

    private final ConsultationService consultationService;

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
        log.info("ConsultationController | getConsultationByRef | consultationRef : " + consultationRef);

        ConsultationResponse consultationResponse
                = consultationService.getConsultationByRef(consultationRef);
        return new ResponseEntity<>(consultationResponse, HttpStatus.OK);
    }

}