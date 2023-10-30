package com.dopediatrie.hosman.auth.controller;

import com.dopediatrie.hosman.auth.entity.Poste;
import com.dopediatrie.hosman.auth.payload.request.PosteRequest;
import com.dopediatrie.hosman.auth.payload.response.PosteResponse;
import com.dopediatrie.hosman.auth.service.PosteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/postes")
@RequiredArgsConstructor
@Log4j2
public class PosteController {
    private final PosteService posteService;

    @GetMapping
    public ResponseEntity<List<Poste>> getAllPostes() {

        log.info("PosteController | getAllPostes is called");
        return new ResponseEntity<>(posteService.getAllPostes(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addPoste(@RequestBody PosteRequest posteRequest) {

        log.info("PosteController | addPoste is called");

        log.info("PosteController | addPoste | posteRequest : " + posteRequest.toString());

        long posteId = posteService.addPoste(posteRequest);
        return new ResponseEntity<>(posteId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PosteResponse> getPosteById(@PathVariable("id") long posteId) {

        log.info("PosteController | getPosteById is called");

        log.info("PosteController | getPosteById | posteId : " + posteId);

        PosteResponse posteResponse
                = posteService.getPosteById(posteId);
        return new ResponseEntity<>(posteResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editPoste(@RequestBody PosteRequest posteRequest,
                                         @PathVariable("id") long posteId
    ) {

        log.info("PosteController | editPoste is called");

        log.info("PosteController | editPoste | posteId : " + posteId);

        posteService.editPoste(posteRequest, posteId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deletePosteById(@PathVariable("id") long posteId) {
        posteService.deletePosteById(posteId);
    }
}
