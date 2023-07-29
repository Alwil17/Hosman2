package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Tarif;
import com.dopediatrie.hosman.secretariat.payload.request.TarifRequest;
import com.dopediatrie.hosman.secretariat.payload.response.TarifResponse;
import com.dopediatrie.hosman.secretariat.service.TarifService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarifs")
@RequiredArgsConstructor
@Log4j2
public class TarifController {

    private final TarifService tarifService;

    @GetMapping
    public ResponseEntity<List<Tarif>> getAllTarifs() {

        log.info("TarifController | getAllTarifs is called");
        return new ResponseEntity<>(tarifService.getAllTarifs(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addTarif(@RequestBody TarifRequest tarifRequest) {

        log.info("TarifController | addTarif is called");

        log.info("TarifController | addTarif | tarifRequest : " + tarifRequest.toString());

        long tarifId = tarifService.addTarif(tarifRequest);
        return new ResponseEntity<>(tarifId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarifResponse> getTarifById(@PathVariable("id") long tarifId) {

        log.info("TarifController | getTarifById is called");

        log.info("TarifController | getTarifById | tarifId : " + tarifId);

        TarifResponse tarifResponse
                = tarifService.getTarifById(tarifId);
        return new ResponseEntity<>(tarifResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editTarif(@RequestBody TarifRequest tarifRequest,
            @PathVariable("id") long tarifId
    ) {

        log.info("TarifController | editTarif is called");

        log.info("TarifController | editTarif | tarifId : " + tarifId);

        tarifService.editTarif(tarifRequest, tarifId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteTarifById(@PathVariable("id") long tarifId) {
        tarifService.deleteTarifById(tarifId);
    }
}