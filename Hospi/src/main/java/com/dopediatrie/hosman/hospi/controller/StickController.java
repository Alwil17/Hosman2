package com.dopediatrie.hosman.hospi.controller;

import com.dopediatrie.hosman.hospi.entity.Stick;
import com.dopediatrie.hosman.hospi.payload.request.StickRequest;
import com.dopediatrie.hosman.hospi.payload.response.StickResponse;
import com.dopediatrie.hosman.hospi.service.StickService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/sticks")
@RequiredArgsConstructor
@Log4j2
public class StickController {

    private final StickService stickService;

    @GetMapping
    public ResponseEntity<List<Stick>> getAllSticks() {
        log.info("StickController | getAllSticks is called");
        return new ResponseEntity<>(stickService.getAllSticks(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addStick(@RequestBody StickRequest stickRequest) {
        log.info("StickController | addStick is called");
        log.info("StickController | addStick | stickRequest : " + stickRequest.toString());

        long stickId = stickService.addStick(stickRequest);
        return new ResponseEntity<>(stickId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StickResponse> getStickById(@PathVariable("id") long stickId) {

        log.info("StickController | getStickById is called");

        log.info("StickController | getStickById | stickId : " + stickId);

        StickResponse stickResponse
                = stickService.getStickById(stickId);
        return new ResponseEntity<>(stickResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Stick>> getStickBySearch(@RequestParam("groupe") String groupe) {
        log.info("StickController | getStickBySearch is called");
        List<Stick> sticks = Collections.emptyList();
        if(groupe != null && groupe.isBlank())
            sticks = stickService.getStickByGroupe(groupe);
        return new ResponseEntity<>(sticks, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editStick(@RequestBody StickRequest stickRequest,
            @PathVariable("id") long stickId
    ) {

        log.info("StickController | editStick is called");

        log.info("StickController | editStick | stickId : " + stickId);

        stickService.editStick(stickRequest, stickId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteStickById(@PathVariable("id") long stickId) {
        stickService.deleteStickById(stickId);
    }
}