package com.dopediatrie.hosman.hospi.controller;

import com.dopediatrie.hosman.hospi.entity.Hospit;
import com.dopediatrie.hosman.hospi.entity.Suivi;
import com.dopediatrie.hosman.hospi.payload.request.HospitRequest;
import com.dopediatrie.hosman.hospi.payload.response.*;
import com.dopediatrie.hosman.hospi.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hospits")
@RequiredArgsConstructor
@Log4j2
public class HospitController {

    private final HospitService hospitService;
    private final SuiviService suiviService;
    private final ChirurgieService chirurgieService;
    private final AddressedService addressedService;
    private final TransfusedService transfusedService;
    private final DecededService decededService;
    private final ScamService scamService;
    private final SortieService sortieService;
    private final MedExterneService medExterneService;

    @GetMapping
    public ResponseEntity<List<Hospit>> getAllHospits() {

        log.info("HospitController | getAllHospits is called");
        return new ResponseEntity<>(hospitService.getAllHospits(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addHospit(@RequestBody HospitRequest hospitRequest) {

        log.info("HospitController | addHospit is called");

        log.info("HospitController | addHospit | hospitRequest : " + hospitRequest.toString());

        long hospitId = hospitService.addHospit(hospitRequest);
        return new ResponseEntity<>(hospitId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospitResponse> getHospitById(@PathVariable("id") long hospitId) {

        log.info("HospitController | getHospitById is called");

        log.info("HospitController | getHospitById | hospitId : " + hospitId);

        HospitResponse hospitResponse
                = hospitService.getHospitById(hospitId);
        return new ResponseEntity<>(hospitResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}/suivis")
    public ResponseEntity<List<SuiviResponse>> getSuiviByHospitId(@PathVariable("id") long hospitId) {
        log.info("HospitController | getHospitById is called");
        log.info("HospitController | getHospitById | hospitId : " + hospitId);

        List<SuiviResponse> suivis
                = suiviService.getSuiviByHospitId(hospitId);
        return new ResponseEntity<>(suivis, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Hospit>> getHospitBySearch(@RequestParam("status") int status) {
        log.info("HospitController | getHospitBySearch is called");

        List<Hospit> hospits
                = hospitService.getHospitByStatus(status);
        return new ResponseEntity<>(hospits, HttpStatus.OK);
    }

    @GetMapping("/{id}/chirurgies")
    public ResponseEntity<List<ChirurgieResponse>> getChirurgieByHospitId(@PathVariable("id") long hospitId) {
        log.info("HospitController | getHospitById is called");
        log.info("HospitController | getHospitById | hospitId : " + hospitId);

        List<ChirurgieResponse> chirurgies
                = chirurgieService.getChirurgieByHospitId(hospitId);
        return new ResponseEntity<>(chirurgies, HttpStatus.OK);
    }

    @GetMapping("/{id}/addressed")
    public ResponseEntity<List<AddressedResponse>> getAddressedByHospitId(@PathVariable("id") long hospitId) {
        log.info("HospitController | getHospitById is called");
        log.info("HospitController | getHospitById | hospitId : " + hospitId);

        List<AddressedResponse> addresseds
                = addressedService.getAddressedByHospitId(hospitId);
        return new ResponseEntity<>(addresseds, HttpStatus.OK);
    }

    @GetMapping("/{id}/transfused")
    public ResponseEntity<List<TransfusedResponse>> getTransfusedByHospitId(@PathVariable("id") long hospitId) {
        log.info("HospitController | getHospitById is called");
        log.info("HospitController | getHospitById | hospitId : " + hospitId);

        List<TransfusedResponse> transfuseds
                = transfusedService.getTransfusedByHospitId(hospitId);
        return new ResponseEntity<>(transfuseds, HttpStatus.OK);
    }

    @GetMapping("/{id}/deceded")
    public ResponseEntity<List<DecededResponse>> getDecededByHospitId(@PathVariable("id") long hospitId) {
        log.info("HospitController | getHospitById is called");
        log.info("HospitController | getHospitById | hospitId : " + hospitId);

        List<DecededResponse> decededs
                = decededService.getDecededByHospitId(hospitId);
        return new ResponseEntity<>(decededs, HttpStatus.OK);
    }

    @GetMapping("/{id}/scams")
    public ResponseEntity<List<ScamResponse>> getScamByHospitId(@PathVariable("id") long hospitId) {
        log.info("HospitController | getHospitById is called");
        log.info("HospitController | getHospitById | hospitId : " + hospitId);

        List<ScamResponse> scams
                = scamService.getScamByHospitId(hospitId);
        return new ResponseEntity<>(scams, HttpStatus.OK);
    }

    @GetMapping("/{id}/sorties")
    public ResponseEntity<List<SortieResponse>> getSortieByHospitId(@PathVariable("id") long hospitId) {
        log.info("HospitController | getHospitById is called");
        log.info("HospitController | getHospitById | hospitId : " + hospitId);

        List<SortieResponse> sorties
                = sortieService.getSortieByHospitId(hospitId);
        return new ResponseEntity<>(sorties, HttpStatus.OK);
    }

    @GetMapping("/{id}/med-externes")
    public ResponseEntity<List<MedExterneResponse>> getMedExterneByHospitId(@PathVariable("id") long hospitId) {
        log.info("HospitController | getHospitById is called");
        log.info("HospitController | getHospitById | hospitId : " + hospitId);

        List<MedExterneResponse> medExternes
                = medExterneService.getMedExterneByHospitId(hospitId);
        return new ResponseEntity<>(medExternes, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editHospit(@RequestBody HospitRequest hospitRequest,
            @PathVariable("id") long hospitId) {
        log.info("HospitController | editHospit is called");
        log.info("HospitController | editHospit | hospitId : " + hospitId);

        hospitService.editHospit(hospitRequest, hospitId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Void> updateStatus(@PathVariable("id") long hospitId, @RequestParam("status") int status) {
        log.info("HospitController | updateStatus is called");
        log.info("HospitController | updateStatus | hospitId : " + hospitId);

        hospitService.updateStatus(hospitId, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteHospitById(@PathVariable("id") long hospitId) {
        hospitService.deleteHospitById(hospitId);
    }
}