package com.dopediatrie.hosman.hospi.controller;

import com.dopediatrie.hosman.hospi.entity.Transfused;
import com.dopediatrie.hosman.hospi.payload.request.TransfusedRequest;
import com.dopediatrie.hosman.hospi.payload.response.TransfusedResponse;
import com.dopediatrie.hosman.hospi.service.TransfusedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transfused")
@RequiredArgsConstructor
@Log4j2
public class TransfusedController {

    private final TransfusedService transfusedService;

    @GetMapping
    public ResponseEntity<List<Transfused>> getAllTransfuseds() {

        log.info("TransfusedController | getAllTransfuseds is called");
        return new ResponseEntity<>(transfusedService.getAllTransfuseds(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addTransfused(@RequestBody TransfusedRequest transfusedRequest) {

        log.info("TransfusedController | addTransfused is called");

        log.info("TransfusedController | addTransfused | transfusedRequest : " + transfusedRequest.toString());

        long transfusedId = transfusedService.addTransfused(transfusedRequest);
        return new ResponseEntity<>(transfusedId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransfusedResponse> getTransfusedById(@PathVariable("id") long transfusedId) {

        log.info("TransfusedController | getTransfusedById is called");

        log.info("TransfusedController | getTransfusedById | transfusedId : " + transfusedId);

        TransfusedResponse transfusedResponse
                = transfusedService.getTransfusedById(transfusedId);
        return new ResponseEntity<>(transfusedResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editTransfused(@RequestBody TransfusedRequest transfusedRequest,
            @PathVariable("id") long transfusedId) {
        log.info("TransfusedController | editTransfused is called");
        log.info("TransfusedController | editTransfused | transfusedId : " + transfusedId);

        transfusedService.editTransfused(transfusedRequest, transfusedId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteTransfusedById(@PathVariable("id") long transfusedId) {
        transfusedService.deleteTransfusedById(transfusedId);
    }
}