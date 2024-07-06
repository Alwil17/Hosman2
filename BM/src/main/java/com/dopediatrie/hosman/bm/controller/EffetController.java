package com.dopediatrie.hosman.bm.controller;

import com.dopediatrie.hosman.bm.entity.EffetSecondaire;
import com.dopediatrie.hosman.bm.payload.request.NameRequest;
import com.dopediatrie.hosman.bm.payload.response.NameResponse;
import com.dopediatrie.hosman.bm.service.EffetSecondaireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/effets")
@RequiredArgsConstructor
@Log4j2
public class EffetController {

    private final EffetSecondaireService effetService;

    @GetMapping
    public ResponseEntity<List<EffetSecondaire>> getAllEffetSecondaires() {

        log.info("EffetSecondaireController | getAllEffetSecondaires is called");
        return new ResponseEntity<>(effetService.getAllEffetSecondaires(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addEffetSecondaire(@RequestBody NameRequest effetRequest) {

        log.info("EffetSecondaireController | addEffetSecondaire is called");

        log.info("EffetSecondaireController | addEffetSecondaire | effetRequest : " + effetRequest.toString());

        long effetId = effetService.addEffetSecondaire(effetRequest);
        return new ResponseEntity<>(effetId, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<String>> getEffetSecondaireBySearch(@RequestParam("q") String q) {
        log.info("EffetSecondaireController | getEffetSecondaireBySearch is called");
        List<String> effets = effetService.getEffetSecondaireLike(q);
        return new ResponseEntity<>(effets, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NameResponse> getEffetSecondaireById(@PathVariable("id") long effetId) {
        log.info("EffetSecondaireController | getEffetSecondaireById is called");
        log.info("EffetSecondaireController | getEffetSecondaireById | effetId : " + effetId);

        NameResponse effetResponse
                = effetService.getEffetSecondaireById(effetId);
        return new ResponseEntity<>(effetResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editEffetSecondaire(@RequestBody NameRequest effetRequest,
            @PathVariable("id") long effetId
    ) {

        log.info("EffetSecondaireController | editEffetSecondaire is called");

        log.info("EffetSecondaireController | editEffetSecondaire | effetId : " + effetId);

        effetService.editEffetSecondaire(effetRequest, effetId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteEffetSecondaireById(@PathVariable("id") long effetId) {
        effetService.deleteEffetSecondaireById(effetId);
    }
}