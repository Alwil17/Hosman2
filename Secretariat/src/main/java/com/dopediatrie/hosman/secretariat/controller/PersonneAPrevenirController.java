package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.PersonneAPrevenir;
import com.dopediatrie.hosman.secretariat.payload.request.PersonneAPrevenirRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PersonneAPrevenirResponse;
import com.dopediatrie.hosman.secretariat.service.PersonneAPrevenirService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personne-a-prevenirs")
@RequiredArgsConstructor
@Log4j2
public class PersonneAPrevenirController {

    private final PersonneAPrevenirService personneAPrevenirService;

    @GetMapping
    public ResponseEntity<List<PersonneAPrevenir>> getAllPersonneAPrevenirs() {

        log.info("PersonneAPrevenirController | getAllPersonneAPrevenirs is called");
        return new ResponseEntity<>(personneAPrevenirService.getAllPersonneAPrevenirs(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addPersonneAPrevenir(@RequestBody PersonneAPrevenirRequest personneAPrevenirRequest) {

        log.info("PersonneAPrevenirController | addPersonneAPrevenir is called");

        log.info("PersonneAPrevenirController | addPersonneAPrevenir | personneAPrevenirRequest : " + personneAPrevenirRequest.toString());

        long personneAPrevenirId = personneAPrevenirService.addPersonneAPrevenir(personneAPrevenirRequest);
        return new ResponseEntity<>(personneAPrevenirId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonneAPrevenirResponse> getPersonneAPrevenirById(@PathVariable("id") long personneAPrevenirId) {

        log.info("PersonneAPrevenirController | getPersonneAPrevenirById is called");

        log.info("PersonneAPrevenirController | getPersonneAPrevenirById | personneAPrevenirId : " + personneAPrevenirId);

        PersonneAPrevenirResponse personneAPrevenirResponse
                = personneAPrevenirService.getPersonneAPrevenirById(personneAPrevenirId);
        return new ResponseEntity<>(personneAPrevenirResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editPersonneAPrevenir(@RequestBody PersonneAPrevenirRequest personneAPrevenirRequest,
            @PathVariable("id") long personneAPrevenirId
    ) {

        log.info("PersonneAPrevenirController | editPersonneAPrevenir is called");

        log.info("PersonneAPrevenirController | editPersonneAPrevenir | personneAPrevenirId : " + personneAPrevenirId);

        personneAPrevenirService.editPersonneAPrevenir(personneAPrevenirRequest, personneAPrevenirId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deletePersonneAPrevenirById(@PathVariable("id") long personneAPrevenirId) {
        personneAPrevenirService.deletePersonneAPrevenirById(personneAPrevenirId);
    }
}