package com.dopediatrie.hosman.bm.controller;

import com.dopediatrie.hosman.bm.entity.Deceded;
import com.dopediatrie.hosman.bm.payload.request.DecededRequest;
import com.dopediatrie.hosman.bm.payload.response.DecededResponse;
import com.dopediatrie.hosman.bm.service.DecededService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deceded")
@RequiredArgsConstructor
@Log4j2
public class DecededController {

    private final DecededService decededService;

    @GetMapping
    public ResponseEntity<List<Deceded>> getAllDecededs() {

        log.info("DecededController | getAllDecededs is called");
        return new ResponseEntity<>(decededService.getAllDecededs(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addDeceded(@RequestBody DecededRequest decededRequest) {

        log.info("DecededController | addDeceded is called");

        log.info("DecededController | addDeceded | decededRequest : " + decededRequest.toString());

        long decededId = decededService.addDeceded(decededRequest);
        return new ResponseEntity<>(decededId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DecededResponse> getDecededById(@PathVariable("id") long decededId) {

        log.info("DecededController | getDecededById is called");

        log.info("DecededController | getDecededById | decededId : " + decededId);

        DecededResponse decededResponse
                = decededService.getDecededById(decededId);
        return new ResponseEntity<>(decededResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editDeceded(@RequestBody DecededRequest decededRequest,
            @PathVariable("id") long decededId) {
        log.info("DecededController | editDeceded is called");
        log.info("DecededController | editDeceded | decededId : " + decededId);

        decededService.editDeceded(decededRequest, decededId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteDecededById(@PathVariable("id") long decededId) {
        decededService.deleteDecededById(decededId);
    }
}