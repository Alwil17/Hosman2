package com.dopediatrie.hosman.bm.controller;

import com.dopediatrie.hosman.bm.entity.Helpers;
import com.dopediatrie.hosman.bm.service.HelpersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/helpers")
@RequiredArgsConstructor
@Log4j2
public class HelpersController {

    private final HelpersService helperService;

    @GetMapping
    public ResponseEntity<List<Helpers>> getAllHelpers() {
        log.info("HelpersController | getAllHelpers is called");
        return new ResponseEntity<>(helperService.getAllHelpers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addHelpers(@RequestBody Helpers helperRequest) {

        log.info("HelpersController | addHelpers is called");

        long helperId = helperService.addHelpers(helperRequest);
        return new ResponseEntity<>(helperId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Helpers> getHelpersById(@PathVariable("id") long helperId) {

        log.info("HelpersController | getHelpersById is called");

        log.info("HelpersController | getHelpersById | helperId : " + helperId);

        Helpers helperResponse
                = helperService.getHelpersById(helperId);
        return new ResponseEntity<>(helperResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Helpers>> getHelpersBySearch(@RequestParam("type") String type) {
        log.info("HelpersController | getHelpersBySearch is called");

        List<Helpers> helpers = new ArrayList<>();
        if(type != null && !type.isBlank()){
            helpers = helperService.getHelpersByType(type);
        }
        return new ResponseEntity<>(helpers, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> editHelpers(@RequestBody Helpers helperRequest,
            @PathVariable("id") long helperId
    ) {

        log.info("HelpersController | editHelpers is called");

        log.info("HelpersController | editHelpers | helperId : " + helperId);

        helperService.editHelpers(helperRequest, helperId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteHelpersById(@PathVariable("id") long helperId) {
        helperService.deleteHelpersById(helperId);
    }
}