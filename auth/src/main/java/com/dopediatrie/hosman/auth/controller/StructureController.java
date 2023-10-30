package com.dopediatrie.hosman.auth.controller;

import com.dopediatrie.hosman.auth.entity.Structure;
import com.dopediatrie.hosman.auth.payload.request.StructureRequest;
import com.dopediatrie.hosman.auth.payload.response.StructureResponse;
import com.dopediatrie.hosman.auth.service.StructureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/structures")
@RequiredArgsConstructor
@Log4j2
public class StructureController {
    private final StructureService structureService;

    @GetMapping
    public ResponseEntity<List<Structure>> getAllStructures() {

        log.info("StructureController | getAllStructures is called");
        return new ResponseEntity<>(structureService.getAllStructures(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addStructure(@RequestBody StructureRequest structureRequest) {

        log.info("StructureController | addStructure is called");

        log.info("StructureController | addStructure | structureRequest : " + structureRequest.toString());

        long structureId = structureService.addStructure(structureRequest);
        return new ResponseEntity<>(structureId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StructureResponse> getStructureById(@PathVariable("id") long structureId) {

        log.info("StructureController | getStructureById is called");

        log.info("StructureController | getStructureById | structureId : " + structureId);

        StructureResponse structureResponse
                = structureService.getStructureById(structureId);
        return new ResponseEntity<>(structureResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editStructure(@RequestBody StructureRequest structureRequest,
                                         @PathVariable("id") long structureId
    ) {

        log.info("StructureController | editStructure is called");

        log.info("StructureController | editStructure | structureId : " + structureId);

        structureService.editStructure(structureRequest, structureId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteStructureById(@PathVariable("id") long structureId) {
        structureService.deleteStructureById(structureId);
    }
}
