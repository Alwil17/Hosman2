package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.ModePayement;
import com.dopediatrie.hosman.secretariat.payload.request.NameRequest;
import com.dopediatrie.hosman.secretariat.payload.response.NameResponse;
import com.dopediatrie.hosman.secretariat.service.ModePayementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mode-payements")
@RequiredArgsConstructor
@Log4j2
public class ModePayementController {

    private final ModePayementService modePayementService;

    @GetMapping
    public ResponseEntity<List<ModePayement>> getAllModePayements() {

        log.info("ModePayementController | getAllModePayements is called");
        return new ResponseEntity<>(modePayementService.getAllModePayements(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addModePayement(@RequestBody NameRequest modePayementRequest) {

        log.info("ModePayementController | addModePayement is called");

        log.info("ModePayementController | addModePayement | modePayementRequest : " + modePayementRequest.toString());

        long modePayementId = modePayementService.addModePayement(modePayementRequest);
        return new ResponseEntity<>(modePayementId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NameResponse> getModePayementById(@PathVariable("id") long modePayementId) {

        log.info("ModePayementController | getModePayementById is called");

        log.info("ModePayementController | getModePayementById | modePayementId : " + modePayementId);

        NameResponse modePayementResponse
                = modePayementService.getModePayementById(modePayementId);
        return new ResponseEntity<>(modePayementResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editModePayement(@RequestBody NameRequest modePayementRequest,
            @PathVariable("id") long modePayementId
    ) {

        log.info("ModePayementController | editModePayement is called");

        log.info("ModePayementController | editModePayement | modePayementId : " + modePayementId);

        modePayementService.editModePayement(modePayementRequest, modePayementId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteModePayementById(@PathVariable("id") long modePayementId) {
        modePayementService.deleteModePayementById(modePayementId);
    }
}