package com.dopediatrie.hosman.hospi.controller;

import com.dopediatrie.hosman.hospi.entity.Sortie;
import com.dopediatrie.hosman.hospi.payload.request.SortieRequest;
import com.dopediatrie.hosman.hospi.payload.response.SortieResponse;
import com.dopediatrie.hosman.hospi.service.SortieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sorties")
@RequiredArgsConstructor
@Log4j2
public class SortieController {

    private final SortieService sortieService;

    @GetMapping
    public ResponseEntity<List<Sortie>> getAllSorties() {

        log.info("SortieController | getAllSorties is called");
        return new ResponseEntity<>(sortieService.getAllSorties(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addSortie(@RequestBody SortieRequest sortieRequest) {

        log.info("SortieController | addSortie is called");

        log.info("SortieController | addSortie | sortieRequest : " + sortieRequest.toString());

        long sortieId = sortieService.addSortie(sortieRequest);
        return new ResponseEntity<>(sortieId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SortieResponse> getSortieById(@PathVariable("id") long sortieId) {

        log.info("SortieController | getSortieById is called");

        log.info("SortieController | getSortieById | sortieId : " + sortieId);

        SortieResponse sortieResponse
                = sortieService.getSortieById(sortieId);
        return new ResponseEntity<>(sortieResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editSortie(@RequestBody SortieRequest sortieRequest,
            @PathVariable("id") long sortieId
    ) {

        log.info("SortieController | editSortie is called");

        log.info("SortieController | editSortie | sortieId : " + sortieId);

        sortieService.editSortie(sortieRequest, sortieId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteSortieById(@PathVariable("id") long sortieId) {
        sortieService.deleteSortieById(sortieId);
    }
}