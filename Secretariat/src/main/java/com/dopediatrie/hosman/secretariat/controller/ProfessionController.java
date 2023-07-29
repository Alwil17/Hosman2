package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Profession;
import com.dopediatrie.hosman.secretariat.payload.request.ProfessionRequest;
import com.dopediatrie.hosman.secretariat.payload.response.ProfessionResponse;
import com.dopediatrie.hosman.secretariat.service.ProfessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professions")
@RequiredArgsConstructor
@Log4j2
public class ProfessionController {

    private final ProfessionService professionService;

    @GetMapping
    public ResponseEntity<List<Profession>> getAllProfessions() {

        log.info("ProfessionController | getAllProfessions is called");
        return new ResponseEntity<>(professionService.getAllProfessions(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addProfession(@RequestBody ProfessionRequest professionRequest) {

        log.info("ProfessionController | addProfession is called");

        log.info("ProfessionController | addProfession | professionRequest : " + professionRequest.toString());

        long professionId = professionService.addProfession(professionRequest);
        return new ResponseEntity<>(professionId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessionResponse> getProfessionById(@PathVariable("id") long professionId) {

        log.info("ProfessionController | getProfessionById is called");

        log.info("ProfessionController | getProfessionById | professionId : " + professionId);

        ProfessionResponse professionResponse
                = professionService.getProfessionById(professionId);
        return new ResponseEntity<>(professionResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editProfession(@RequestBody ProfessionRequest professionRequest,
            @PathVariable("id") long professionId
    ) {

        log.info("ProfessionController | editProfession is called");

        log.info("ProfessionController | editProfession | professionId : " + professionId);

        professionService.editProfession(professionRequest, professionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteProfessionById(@PathVariable("id") long professionId) {
        professionService.deleteProfessionById(professionId);
    }
}