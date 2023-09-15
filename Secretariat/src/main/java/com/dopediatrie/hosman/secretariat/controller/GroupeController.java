package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Groupe;
import com.dopediatrie.hosman.secretariat.payload.request.GroupeRequest;
import com.dopediatrie.hosman.secretariat.payload.response.GroupeResponse;
import com.dopediatrie.hosman.secretariat.service.GroupeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groupes")
@RequiredArgsConstructor
@Log4j2
public class GroupeController {

    private final GroupeService groupeService;

    @GetMapping
    public ResponseEntity<List<Groupe>> getAllGroupes() {

        log.info("GroupeController | getAllGroupes is called");
        return new ResponseEntity<>(groupeService.getAllGroupes(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addGroupe(@RequestBody GroupeRequest groupeRequest) {

        log.info("GroupeController | addGroupe is called");

        log.info("GroupeController | addGroupe | groupeRequest : " + groupeRequest.toString());

        long groupeId = groupeService.addGroupe(groupeRequest);
        return new ResponseEntity<>(groupeId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupeResponse> getGroupeById(@PathVariable("id") long groupeId) {

        log.info("GroupeController | getGroupeById is called");

        log.info("GroupeController | getGroupeById | groupeId : " + groupeId);

        GroupeResponse groupeResponse
                = groupeService.getGroupeById(groupeId);
        return new ResponseEntity<>(groupeResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editGroupe(@RequestBody GroupeRequest groupeRequest,
            @PathVariable("id") long groupeId
    ) {

        log.info("GroupeController | editGroupe is called");

        log.info("GroupeController | editGroupe | groupeId : " + groupeId);

        groupeService.editGroupe(groupeRequest, groupeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteGroupeById(@PathVariable("id") long groupeId) {
        groupeService.deleteGroupeById(groupeId);
    }
}