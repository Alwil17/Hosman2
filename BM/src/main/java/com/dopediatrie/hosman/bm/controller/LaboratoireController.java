package com.dopediatrie.hosman.bm.controller;

import com.dopediatrie.hosman.bm.entity.Laboratoire;
import com.dopediatrie.hosman.bm.payload.request.LaboratoireRequest;
import com.dopediatrie.hosman.bm.payload.response.LaboratoireResponse;
import com.dopediatrie.hosman.bm.service.LaboratoireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/laboratoires")
@RequiredArgsConstructor
@Log4j2
public class LaboratoireController {

    private final LaboratoireService laboratoireService;

    @GetMapping
    public ResponseEntity<List<Laboratoire>> getAllLaboratoires() {

        log.info("LaboratoireController | getAllLaboratoires is called");
        return new ResponseEntity<>(laboratoireService.getAllLaboratoires(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addLaboratoire(@RequestBody LaboratoireRequest laboratoireRequest) {

        log.info("LaboratoireController | addLaboratoire is called");

        log.info("LaboratoireController | addLaboratoire | laboratoireRequest : " + laboratoireRequest.toString());

        long laboratoireId = laboratoireService.addLaboratoire(laboratoireRequest);
        return new ResponseEntity<>(laboratoireId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LaboratoireResponse> getLaboratoireById(@PathVariable("id") long laboratoireId) {

        log.info("LaboratoireController | getLaboratoireById is called");

        log.info("LaboratoireController | getLaboratoireById | laboratoireId : " + laboratoireId);

        LaboratoireResponse laboratoireResponse
                = laboratoireService.getLaboratoireById(laboratoireId);
        return new ResponseEntity<>(laboratoireResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Laboratoire>> getLaboratoireBySearch(@RequestParam(required = false, value = "agence_id") Long agenceId, @RequestParam(required = false, value = "q") String q) {
        log.info("LaboratoireController | getLaboratoireBySearch is called");
        List<Laboratoire> labos = new ArrayList<>();
        boolean checkAgence = (agenceId != null && agenceId != 0);
        boolean checkQ = (q != null && !q.isBlank());
        if(checkAgence == true && checkQ == true){
            labos = laboratoireService.getLaboratoireByAgenceIdAndQ(agenceId, q);
        }else if(checkAgence == true && checkQ == false){
            labos = laboratoireService.getLaboratoireByAgenceId(agenceId);
        }else if(checkAgence == false && checkQ == true){
            labos = laboratoireService.getLaboratoireByQueryString(q);
        }
        return new ResponseEntity<>(labos, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> editLaboratoire(@RequestBody LaboratoireRequest laboratoireRequest,
            @PathVariable("id") long laboratoireId
    ) {

        log.info("LaboratoireController | editLaboratoire is called");

        log.info("LaboratoireController | editLaboratoire | laboratoireId : " + laboratoireId);

        laboratoireService.editLaboratoire(laboratoireRequest, laboratoireId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteLaboratoireById(@PathVariable("id") long laboratoireId) {
        laboratoireService.deleteLaboratoireById(laboratoireId);
    }
}