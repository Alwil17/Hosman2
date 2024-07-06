package com.dopediatrie.hosman.hospi.controller;

import com.dopediatrie.hosman.hospi.payload.response.GroupeResponse;
import com.dopediatrie.hosman.hospi.payload.response.TarifGlobalResponse;
import com.dopediatrie.hosman.hospi.payload.response.TarifResponse;
import com.dopediatrie.hosman.hospi.service.TarifService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tarifs")
@RequiredArgsConstructor
@Log4j2
public class TarifController {

    private final TarifService tarifService;

    @GetMapping
    public ResponseEntity<Map<String, List<TarifResponse>>> getAllTarifs() {
        log.info("TarifController | getAllTarifs is called");
        return new ResponseEntity<>(tarifService.getAllTarifs(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarifResponse> getTarifById(@PathVariable("id") long id) {
        log.info("TarifController | getTarifById is called");
        return new ResponseEntity<>(tarifService.getTarifById(id), HttpStatus.OK);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<TarifResponse> getTarifByCode(@PathVariable("code") String code) {
        log.info("TarifController | getTarifByCode is called");
        return new ResponseEntity<>(tarifService.getTarifByCode(code), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TarifResponse>> getTarifBySearch(@RequestParam(value = "groupe", required = false) String groupeCode, @RequestParam(value = "acte", required = false) String acte) {
        log.info("TarifController | getTarifBySearch is called");
        List<TarifResponse> tarifResponses = Collections.emptyList();

        boolean checkGroupe = (groupeCode != null && !groupeCode.isBlank());
        boolean checkActe = (acte != null && !acte.isBlank());
        if(checkGroupe && checkActe){
            tarifResponses = tarifService.getTarifForGroupeAndActe(groupeCode, acte);
        }else if(checkGroupe == true && checkActe == false){
            tarifResponses = tarifService.getTarifForGroupeAndActe(groupeCode, null);
        }else if(checkGroupe == false && checkActe == true){
            tarifResponses = tarifService.getTarifForGroupeAndActe(null, acte);
        }

        return new ResponseEntity<>(tarifResponses, HttpStatus.OK);
    }


    @GetMapping("/examens")
    public ResponseEntity<List<TarifResponse>> getTarifByExamens() {
        log.info("TarifController | getTarifByExamens is called");
        return new ResponseEntity<>(tarifService.getTarifForExamen(), HttpStatus.OK);
    }

}