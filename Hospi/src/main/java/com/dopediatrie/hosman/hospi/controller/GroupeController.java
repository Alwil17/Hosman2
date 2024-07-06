package com.dopediatrie.hosman.hospi.controller;

import com.dopediatrie.hosman.hospi.payload.response.GroupeResponse;
import com.dopediatrie.hosman.hospi.service.GroupeService;
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
    public ResponseEntity<List<GroupeResponse>> getAllGroupes() {
        log.info("GroupeController | getAllGroupes is called");
        return new ResponseEntity<>(groupeService.getAllGroupes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupeResponse> getGroupeById(@PathVariable("id") long id) {
        log.info("GroupeController | getGroupeById is called");
        return new ResponseEntity<>(groupeService.getGroupeById(id), HttpStatus.OK);
    }

}