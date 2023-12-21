package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Attente;
import com.dopediatrie.hosman.secretariat.payload.request.AttenteRequest;
import com.dopediatrie.hosman.secretariat.payload.response.AttenteResponse;
import com.dopediatrie.hosman.secretariat.service.AttenteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/attentes")
@RequiredArgsConstructor
@Log4j2
public class AttenteController {

    private final AttenteService attenteService;

    @GetMapping
    public ResponseEntity<List<Attente>> getAllAttentes() {

        log.info("AttenteController | getAllAttentes is called");
        return new ResponseEntity<>(attenteService.getAllAttentes(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addAttente(@RequestBody AttenteRequest attenteRequest) {

        log.info("AttenteController | addAttente is called");

        log.info("AttenteController | addAttente | attenteRequest : " + attenteRequest.toString());

        long attenteId = attenteService.addAttente(attenteRequest);
        return new ResponseEntity<>(attenteId, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Attente>> getAttenteBySearch(@RequestHeader(value = "user_id", required = false) Long user_id, @RequestParam(value = "vue") String vue, @RequestParam(value = "medecin", required = false) String medecin) {
        log.info("AttenteController | getAttenteById is called");
        List<Attente> attentes = Collections.emptyList();
        long userId = (user_id != null && user_id != 0) ? user_id : 1;
        //System.out.println(userId);
        switch (vue){
            case "SECTEUR":
                attentes = attenteService.getAttenteForMySecteur(userId);
                break;
            case "MOI":
                attentes = attenteService.getAttenteForMe(userId);
                break;
            case "MEDECIN":
                attentes = attenteService.getAttenteForMedecin(medecin);
                break;
            default:
                attentes = attenteService.getAllAttentes();
                break;
        }
        System.out.println(attentes.size());
        return new ResponseEntity<>(attentes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttenteResponse> getAttenteById(@PathVariable("id") long attenteId) {
        log.info("AttenteController | getAttenteById is called");
        log.info("AttenteController | getAttenteById | attenteId : " + attenteId);

        AttenteResponse attenteResponse
                = attenteService.getAttenteById(attenteId);
        return new ResponseEntity<>(attenteResponse, HttpStatus.OK);
    }

    @GetMapping("/num/{id}")
    public ResponseEntity<AttenteResponse> getAttenteByNum(@PathVariable("id") long attenteNum) {
        log.info("AttenteController | getAttenteByNum is called");
        log.info("AttenteController | getAttenteByNum | attenteNum : " + attenteNum);

        AttenteResponse attenteResponse
                = attenteService.getAttenteByNum(attenteNum);
        return new ResponseEntity<>(attenteResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editAttente(@RequestBody AttenteRequest attenteRequest,
            @PathVariable("id") long attenteId
    ) {

        log.info("AttenteController | editAttente is called");

        log.info("AttenteController | editAttente | attenteId : " + attenteId);

        attenteService.editAttente(attenteRequest, attenteId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/updateStatus")
    public ResponseEntity<Void> editAttenteStatus(@RequestHeader(value = "user_id", required = false) Long user_id, @RequestBody AttenteRequest attenteRequest,
                                            @PathVariable("id") long attenteNum) {
        log.info("AttenteController | editAttente is called");
        long userId = (user_id != null && user_id != 0) ? user_id : 1;

        attenteService.updateStatus(attenteNum, attenteRequest, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteAttenteById(@PathVariable("id") long attenteId) {
        attenteService.deleteAttenteById(attenteId);
    }
}