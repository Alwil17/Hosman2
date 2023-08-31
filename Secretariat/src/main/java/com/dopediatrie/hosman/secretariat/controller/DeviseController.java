package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Devise;
import com.dopediatrie.hosman.secretariat.payload.request.DeviseRequest;
import com.dopediatrie.hosman.secretariat.payload.response.DeviseResponse;
import com.dopediatrie.hosman.secretariat.service.DeviseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devises")
@RequiredArgsConstructor
@Log4j2
public class DeviseController {

    private final DeviseService deviseService;

    @GetMapping
    public ResponseEntity<List<Devise>> getAllDevises() {

        log.info("DeviseController | getAllDevises is called");
        return new ResponseEntity<>(deviseService.getAllDevises(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addDevise(@RequestBody DeviseRequest deviseRequest) {

        log.info("DeviseController | addDevise is called");

        log.info("DeviseController | addDevise | deviseRequest : " + deviseRequest.toString());

        long deviseId = deviseService.addDevise(deviseRequest);
        return new ResponseEntity<>(deviseId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviseResponse> getDeviseById(@PathVariable("id") long deviseId) {

        log.info("DeviseController | getDeviseById is called");

        log.info("DeviseController | getDeviseById | deviseId : " + deviseId);

        DeviseResponse deviseResponse
                = deviseService.getDeviseById(deviseId);
        return new ResponseEntity<>(deviseResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editDevise(@RequestBody DeviseRequest deviseRequest,
            @PathVariable("id") long deviseId
    ) {

        log.info("DeviseController | editDevise is called");

        log.info("DeviseController | editDevise | deviseId : " + deviseId);

        deviseService.editDevise(deviseRequest, deviseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteDeviseById(@PathVariable("id") long deviseId) {
        deviseService.deleteDeviseById(deviseId);
    }
}