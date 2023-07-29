package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Pays;
import com.dopediatrie.hosman.secretariat.payload.request.PaysRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PaysResponse;
import com.dopediatrie.hosman.secretariat.service.PaysService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pays")
@RequiredArgsConstructor
@Log4j2
public class PaysController {

    private final PaysService paysService;

    @GetMapping
    public ResponseEntity<List<Pays>> getAllPayss() {

        log.info("PaysController | getAllPayss is called");
        return new ResponseEntity<>(paysService.getAllPays(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addPays(@RequestBody PaysRequest paysRequest) {

        log.info("PaysController | addPays is called");

        log.info("PaysController | addPays | paysRequest : " + paysRequest.toString());

        long paysId = paysService.addPays(paysRequest);
        return new ResponseEntity<>(paysId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaysResponse> getPaysById(@PathVariable("id") long paysId) {

        log.info("PaysController | getPaysById is called");

        log.info("PaysController | getPaysById | paysId : " + paysId);

        PaysResponse paysResponse
                = paysService.getPaysById(paysId);
        return new ResponseEntity<>(paysResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editPays(@RequestBody PaysRequest paysRequest,
            @PathVariable("id") long paysId
    ) {

        log.info("PaysController | editPays is called");

        log.info("PaysController | editPays | paysId : " + paysId);

        paysService.editPays(paysRequest, paysId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deletePaysById(@PathVariable("id") long paysId) {
        paysService.deletePaysById(paysId);
    }
}