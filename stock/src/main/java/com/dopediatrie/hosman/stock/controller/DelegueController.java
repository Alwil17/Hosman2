package com.dopediatrie.hosman.stock.controller;

import com.dopediatrie.hosman.stock.entity.Delegue;
import com.dopediatrie.hosman.stock.payload.request.DelegueRequest;
import com.dopediatrie.hosman.stock.payload.response.DelegueResponse;
import com.dopediatrie.hosman.stock.service.DelegueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delegues")
@RequiredArgsConstructor
@Log4j2
public class DelegueController {

    private final DelegueService delegueService;

    @GetMapping
    public ResponseEntity<List<Delegue>> getAllDelegues() {

        log.info("DelegueController | getAllDelegues is called");
        return new ResponseEntity<>(delegueService.getAllDelegues(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addDelegue(@RequestBody DelegueRequest delegueRequest) {

        log.info("DelegueController | addDelegue is called");

        log.info("DelegueController | addDelegue | delegueRequest : " + delegueRequest.toString());

        long delegueId = delegueService.addDelegue(delegueRequest);
        return new ResponseEntity<>(delegueId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DelegueResponse> getDelegueById(@PathVariable("id") long delegueId) {

        log.info("DelegueController | getDelegueById is called");

        log.info("DelegueController | getDelegueById | delegueId : " + delegueId);

        DelegueResponse delegueResponse
                = delegueService.getDelegueById(delegueId);
        return new ResponseEntity<>(delegueResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editDelegue(@RequestBody DelegueRequest delegueRequest,
            @PathVariable("id") long delegueId
    ) {

        log.info("DelegueController | editDelegue is called");

        log.info("DelegueController | editDelegue | delegueId : " + delegueId);

        delegueService.editDelegue(delegueRequest, delegueId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteDelegueById(@PathVariable("id") long delegueId) {
        delegueService.deleteDelegueById(delegueId);
    }
}