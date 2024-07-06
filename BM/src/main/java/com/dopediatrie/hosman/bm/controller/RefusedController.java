package com.dopediatrie.hosman.bm.controller;

import com.dopediatrie.hosman.bm.entity.Refused;
import com.dopediatrie.hosman.bm.payload.request.RefusedRequest;
import com.dopediatrie.hosman.bm.payload.response.RefusedResponse;
import com.dopediatrie.hosman.bm.service.RefusedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/refused")
@RequiredArgsConstructor
@Log4j2
public class RefusedController {

    private final RefusedService refusedService;

    @GetMapping
    public ResponseEntity<List<Refused>> getAllRefuseds() {
        log.info("RefusedController | getAllRefuseds is called");
        return new ResponseEntity<>(refusedService.getAllRefuseds(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addRefused(@RequestBody RefusedRequest refusedRequest) {
        log.info("RefusedController | addRefused is called");
        log.info("RefusedController | addRefused | refusedRequest : " + refusedRequest.toString());

        long refusedId = refusedService.addRefused(refusedRequest);
        return new ResponseEntity<>(refusedId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefusedResponse> getRefusedById(@PathVariable("id") long refusedId) {

        log.info("RefusedController | getRefusedById is called");

        log.info("RefusedController | getRefusedById | refusedId : " + refusedId);

        RefusedResponse refusedResponse
                = refusedService.getRefusedById(refusedId);
        return new ResponseEntity<>(refusedResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editRefused(@RequestBody RefusedRequest refusedRequest,
            @PathVariable("id") long refusedId) {
        log.info("RefusedController | editRefused is called");
        log.info("RefusedController | editRefused | refusedId : " + refusedId);

        refusedService.editRefused(refusedRequest, refusedId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteRefusedById(@PathVariable("id") long refusedId) {
        refusedService.deleteRefusedById(refusedId);
    }
}