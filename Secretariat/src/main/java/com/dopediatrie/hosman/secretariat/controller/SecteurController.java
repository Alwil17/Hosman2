package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Secteur;
import com.dopediatrie.hosman.secretariat.payload.request.SecteurRequest;
import com.dopediatrie.hosman.secretariat.payload.response.SecteurResponse;
import com.dopediatrie.hosman.secretariat.service.SecteurService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secteurs")
@RequiredArgsConstructor
@Log4j2
public class SecteurController {

    private final SecteurService secteurService;

    @GetMapping
    public ResponseEntity<List<Secteur>> getAllSecteurs() {

        log.info("SecteurController | getAllSecteurs is called");
        return new ResponseEntity<>(secteurService.getAllSecteurs(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addSecteur(@RequestBody SecteurRequest secteurRequest) {

        log.info("SecteurController | addSecteur is called");

        log.info("SecteurController | addSecteur | secteurRequest : " + secteurRequest.toString());

        long secteurId = secteurService.addSecteur(secteurRequest);
        return new ResponseEntity<>(secteurId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecteurResponse> getSecteurById(@PathVariable("id") long secteurId) {

        log.info("SecteurController | getSecteurById is called");

        log.info("SecteurController | getSecteurById | secteurId : " + secteurId);

        SecteurResponse secteurResponse
                = secteurService.getSecteurById(secteurId);
        return new ResponseEntity<>(secteurResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editSecteur(@RequestBody SecteurRequest secteurRequest,
            @PathVariable("id") long secteurId
    ) {

        log.info("SecteurController | editSecteur is called");

        log.info("SecteurController | editSecteur | secteurId : " + secteurId);

        secteurService.editSecteur(secteurRequest, secteurId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteSecteurById(@PathVariable("id") long secteurId) {
        secteurService.deleteSecteurById(secteurId);
    }
}