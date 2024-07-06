package com.dopediatrie.hosman.bm.controller;

import com.dopediatrie.hosman.bm.entity.Delegue;
import com.dopediatrie.hosman.bm.payload.request.DelegueRequest;
import com.dopediatrie.hosman.bm.payload.response.DelegueResponse;
import com.dopediatrie.hosman.bm.service.DelegueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/delegues")
@RequiredArgsConstructor
@Log4j2
public class DelegueController {

    private final DelegueService delegueService;

    @GetMapping
    public ResponseEntity<List<Delegue>> getAllDelegues() {

        log.info("DelegueController | getAllDelegues is called");
        return new ResponseEntity<>(delegueService.getAllDelegues(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addDelegue(@RequestBody DelegueRequest delegueRequest) {

        log.info("DelegueController | addDelegue is called");

        log.info("DelegueController | addDelegue | delegueRequest : " + delegueRequest.toString());

        long delegueId = delegueService.addDelegue(delegueRequest);
        return new ResponseEntity<>(delegueId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DelegueResponse> getDelegueById(@PathVariable("id") long delegueId) {

        log.info("DelegueController | getDelegueById is called");

        log.info("DelegueController | getDelegueById | delegueId : " + delegueId);

        DelegueResponse delegueResponse
                = delegueService.getDelegueById(delegueId);
        return new ResponseEntity<>(delegueResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Delegue>> getDelegueBySearch(@RequestParam(required = false, value = "agence_id") Long agenceId, @RequestParam(required = false, value = "labo_id") Long laboId, @RequestParam(required = false, value = "q") String q) {
        log.info("DelegueController | getDelegueBySearch is called");
        List<Delegue> delegues = new ArrayList<>();
        boolean checkAgence = (agenceId != null && agenceId != 0);
        boolean checkLabo = (laboId != null && laboId != 0);
        boolean checkQ = (q != null && !q.isBlank());

        if(checkAgence && !checkLabo && !checkQ){
            delegues = delegueService.getDelegueByAgenceId(agenceId);
        }else if(checkAgence && !checkLabo && checkQ){
            delegues = delegueService.getDelegueByAgenceIdAndQueryString(agenceId, q);
        }else if(!checkAgence && checkLabo && checkQ){
            delegues = delegueService.getDelegueByLaboIdAndQueryString(laboId, q);
        }else if(!checkAgence && checkLabo && !checkQ){
            delegues = delegueService.getDelegueByLaboId(laboId);
        }else {
            delegues = delegueService.getDelegueByQueryString(q);
        }

        return new ResponseEntity<>(delegues, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> editDelegue(@RequestBody DelegueRequest delegueRequest,
            @PathVariable("id") long delegueId
    ) {

        log.info("DelegueController | editDelegue is called");

        log.info("DelegueController | editDelegue | delegueId : " + delegueId);

        delegueService.editDelegue(delegueRequest, delegueId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteDelegueById(@PathVariable("id") long delegueId) {
        delegueService.deleteDelegueById(delegueId);
    }
}