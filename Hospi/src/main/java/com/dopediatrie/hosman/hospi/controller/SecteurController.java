package com.dopediatrie.hosman.hospi.controller;

import com.dopediatrie.hosman.hospi.payload.request.SecteurRequest;
import com.dopediatrie.hosman.hospi.payload.response.SecteurResponse;
import com.dopediatrie.hosman.hospi.service.SecteurService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/secteurs")
@RequiredArgsConstructor
@Log4j2
public class SecteurController {

    private final SecteurService secteurService;

    @GetMapping
    public ResponseEntity<List<SecteurResponse>> getAllSecteurs() {
        log.info("SecteurController | getAllSecteurs is called");
        return new ResponseEntity<>(secteurService.getAllSecteurs(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecteurResponse> getSecteurById(@PathVariable("id") long secteurId) {

        log.info("SecteurController | getSecteurById is called");
        log.info("SecteurController | getSecteurById | secteurId : " + secteurId);

        SecteurResponse secteurResponse
                = secteurService.getSecteurById(secteurId);
        return new ResponseEntity<>(secteurResponse, HttpStatus.OK);
    }

}