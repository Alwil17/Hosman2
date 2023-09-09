package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Reduction;
import com.dopediatrie.hosman.secretariat.payload.request.ReductionRequest;
import com.dopediatrie.hosman.secretariat.payload.response.ReductionResponse;
import com.dopediatrie.hosman.secretariat.service.ReductionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reductions")
@RequiredArgsConstructor
@Log4j2
public class ReductionController {

    private final ReductionService reductionService;

    @GetMapping
    public ResponseEntity<List<Reduction>> getAllReductions() {

        log.info("ReductionController | getAllReductions is called");
        return new ResponseEntity<>(reductionService.getAllReductions(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addReduction(@RequestBody ReductionRequest reductionRequest) {

        log.info("ReductionController | addReduction is called");

        log.info("ReductionController | addReduction | reductionRequest : " + reductionRequest.toString());

        long reductionId = reductionService.addReduction(reductionRequest);
        return new ResponseEntity<>(reductionId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReductionResponse> getReductionById(@PathVariable("id") long reductionId) {

        log.info("ReductionController | getReductionById is called");

        log.info("ReductionController | getReductionById | reductionId : " + reductionId);

        ReductionResponse reductionResponse
                = reductionService.getReductionById(reductionId);
        return new ResponseEntity<>(reductionResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editReduction(@RequestBody ReductionRequest reductionRequest,
            @PathVariable("id") long reductionId
    ) {

        log.info("ReductionController | editReduction is called");

        log.info("ReductionController | editReduction | reductionId : " + reductionId);

        reductionService.editReduction(reductionRequest, reductionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteReductionById(@PathVariable("id") long reductionId) {
        reductionService.deleteReductionById(reductionId);
    }
}