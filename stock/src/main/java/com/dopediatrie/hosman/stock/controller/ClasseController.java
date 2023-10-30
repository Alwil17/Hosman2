package com.dopediatrie.hosman.stock.controller;

import com.dopediatrie.hosman.stock.entity.Classe;
import com.dopediatrie.hosman.stock.payload.request.ClasseRequest;
import com.dopediatrie.hosman.stock.payload.response.ClasseResponse;
import com.dopediatrie.hosman.stock.service.ClasseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classes")
@RequiredArgsConstructor
@Log4j2
public class ClasseController {

    private final ClasseService classeService;

    @GetMapping
    public ResponseEntity<List<Classe>> getAllClasses() {

        log.info("ClasseController | getAllClasses is called");
        return new ResponseEntity<>(classeService.getAllClasses(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addClasse(@RequestBody ClasseRequest classeRequest) {

        log.info("ClasseController | addClasse is called");

        log.info("ClasseController | addClasse | classeRequest : " + classeRequest.toString());

        long classeId = classeService.addClasse(classeRequest);
        return new ResponseEntity<>(classeId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClasseResponse> getClasseById(@PathVariable("id") long classeId) {

        log.info("ClasseController | getClasseById is called");

        log.info("ClasseController | getClasseById | classeId : " + classeId);

        ClasseResponse classeResponse
                = classeService.getClasseById(classeId);
        return new ResponseEntity<>(classeResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editClasse(@RequestBody ClasseRequest classeRequest,
            @PathVariable("id") long classeId
    ) {

        log.info("ClasseController | editClasse is called");

        log.info("ClasseController | editClasse | classeId : " + classeId);

        classeService.editClasse(classeRequest, classeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteClasseById(@PathVariable("id") long classeId) {
        classeService.deleteClasseById(classeId);
    }
}