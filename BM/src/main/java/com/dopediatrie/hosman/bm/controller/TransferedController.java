package com.dopediatrie.hosman.bm.controller;

import com.dopediatrie.hosman.bm.entity.Transfered;
import com.dopediatrie.hosman.bm.payload.request.TransferedRequest;
import com.dopediatrie.hosman.bm.payload.response.TransferedResponse;
import com.dopediatrie.hosman.bm.service.TransferedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transfered")
@RequiredArgsConstructor
@Log4j2
public class TransferedController {

    private final TransferedService transferedService;

    @GetMapping
    public ResponseEntity<List<Transfered>> getAllTransfereds() {
        log.info("TransferedController | getAllTransfereds is called");
        return new ResponseEntity<>(transferedService.getAllTransfereds(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addTransfered(@RequestBody TransferedRequest transferedRequest) {
        log.info("TransferedController | addTransfered is called");
        log.info("TransferedController | addTransfered | transferedRequest : " + transferedRequest.toString());

        long transferedId = transferedService.addTransfered(transferedRequest);
        return new ResponseEntity<>(transferedId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferedResponse> getTransferedById(@PathVariable("id") long transferedId) {

        log.info("TransferedController | getTransferedById is called");

        log.info("TransferedController | getTransferedById | transferedId : " + transferedId);

        TransferedResponse transferedResponse
                = transferedService.getTransferedById(transferedId);
        return new ResponseEntity<>(transferedResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editTransfered(@RequestBody TransferedRequest transferedRequest,
            @PathVariable("id") long transferedId) {
        log.info("TransferedController | editTransfered is called");
        log.info("TransferedController | editTransfered | transferedId : " + transferedId);

        transferedService.editTransfered(transferedRequest, transferedId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteTransferedById(@PathVariable("id") long transferedId) {
        transferedService.deleteTransferedById(transferedId);
    }
}