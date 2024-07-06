package com.dopediatrie.hosman.bm.controller;

import com.dopediatrie.hosman.bm.entity.Prescription;
import com.dopediatrie.hosman.bm.payload.request.PrescriptionRequest;
import com.dopediatrie.hosman.bm.payload.response.PrescriptionResponse;
import com.dopediatrie.hosman.bm.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/prescriptions")
@RequiredArgsConstructor
@Log4j2
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescriptions() {
        log.info("PrescriptionController | getAllPrescriptions is called");
        return new ResponseEntity<>(prescriptionService.getAllPrescriptions(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addPrescription(@RequestBody PrescriptionRequest prescriptionRequest) {
        log.info("PrescriptionController | addPrescription is called");
        log.info("PrescriptionController | addPrescription | prescriptionRequest : " + prescriptionRequest.toString());

        long prescriptionId = prescriptionService.addPrescription(prescriptionRequest);
        return new ResponseEntity<>(prescriptionId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionResponse> getPrescriptionById(@PathVariable("id") long prescriptionId) {
        log.info("PrescriptionController | getPrescriptionById is called");
        log.info("PrescriptionController | getPrescriptionById | prescriptionId : " + prescriptionId);

        PrescriptionResponse prescriptionResponse
                = prescriptionService.getPrescriptionById(prescriptionId);
        return new ResponseEntity<>(prescriptionResponse, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<String>> getPrescriptionStrings(@RequestParam("q") String queryString) {
        log.info("PrescriptionController | getPrescriptionById is called");
        List<String> resp = new ArrayList<>();
        switch (queryString) {
            case "dose":
                resp = prescriptionService.getAllDoseString();
                break;
            case "periode":
                resp = prescriptionService.getAllPeriodeString();
                break;
            case "adverbe":
                resp = prescriptionService.getAllAdverbeString();
                break;
            case "duree":
                resp = prescriptionService.getAllDureeString();
                break;
            default:
                break;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editPrescription(@RequestBody PrescriptionRequest prescriptionRequest,
                                                 @PathVariable("id") long prescriptionId
    ) {
        log.info("PrescriptionController | editPrescription is called");
        log.info("PrescriptionController | editPrescription | prescriptionId : " + prescriptionId);

        prescriptionService.editPrescription(prescriptionRequest, prescriptionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deletePrescriptionById(@PathVariable("id") long prescriptionId) {
        prescriptionService.deletePrescriptionById(prescriptionId);
    }
}