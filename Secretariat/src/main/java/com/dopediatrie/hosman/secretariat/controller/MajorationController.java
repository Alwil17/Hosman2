package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Majoration;
import com.dopediatrie.hosman.secretariat.payload.request.MajorationRequest;
import com.dopediatrie.hosman.secretariat.payload.response.MajorationResponse;
import com.dopediatrie.hosman.secretariat.service.MajorationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/majorations")
@RequiredArgsConstructor
@Log4j2
public class MajorationController {

    private final MajorationService majorationService;

    @GetMapping
    public ResponseEntity<List<Majoration>> getAllMajorations() {

        log.info("MajorationController | getAllMajorations is called");
        return new ResponseEntity<>(majorationService.getAllMajorations(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addMajoration(@RequestBody MajorationRequest majorationRequest) {

        log.info("MajorationController | addMajoration is called");

        log.info("MajorationController | addMajoration | majorationRequest : " + majorationRequest.toString());

        long majorationId = majorationService.addMajoration(majorationRequest);
        return new ResponseEntity<>(majorationId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MajorationResponse> getMajorationById(@PathVariable("id") long majorationId) {

        log.info("MajorationController | getMajorationById is called");

        log.info("MajorationController | getMajorationById | majorationId : " + majorationId);

        MajorationResponse majorationResponse
                = majorationService.getMajorationById(majorationId);
        return new ResponseEntity<>(majorationResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editMajoration(@RequestBody MajorationRequest majorationRequest,
            @PathVariable("id") long majorationId
    ) {

        log.info("MajorationController | editMajoration is called");

        log.info("MajorationController | editMajoration | majorationId : " + majorationId);

        majorationService.editMajoration(majorationRequest, majorationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteMajorationById(@PathVariable("id") long majorationId) {
        majorationService.deleteMajorationById(majorationId);
    }
}