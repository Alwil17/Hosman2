package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Caisse;
import com.dopediatrie.hosman.secretariat.payload.response.CaisseResponse;
import com.dopediatrie.hosman.secretariat.service.CaisseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.beans.BeanUtils.copyProperties;

@RestController
@RequestMapping("/caisse")
@RequiredArgsConstructor
@Log4j2
public class CaisseController {

    private final CaisseService caisseService;

    @GetMapping
    public ResponseEntity<CaisseResponse> getCurrentCaisse() {

        log.info("CaisseController | getCurrentCaisse is called");
        CaisseResponse caisseResponse = new CaisseResponse();
        Caisse caisse = caisseService.getCurrentCaisse();
        copyProperties(caisse, caisseResponse);
        return new ResponseEntity<>(caisseResponse, HttpStatus.OK);
    }
}