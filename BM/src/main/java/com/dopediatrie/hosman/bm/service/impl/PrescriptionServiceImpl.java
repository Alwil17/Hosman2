package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.entity.Helpers;
import com.dopediatrie.hosman.bm.entity.Prescription;
import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.request.PrescriptionRequest;
import com.dopediatrie.hosman.bm.payload.response.PrescriptionResponse;
import com.dopediatrie.hosman.bm.repository.FormeRepository;
import com.dopediatrie.hosman.bm.repository.HelpersRepository;
import com.dopediatrie.hosman.bm.repository.PrescriptionRepository;
import com.dopediatrie.hosman.bm.repository.ProduitRepository;
import com.dopediatrie.hosman.bm.service.PrescriptionService;
import com.dopediatrie.hosman.bm.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class PrescriptionServiceImpl implements PrescriptionService {
    private final String NOT_FOUND = "PRESCRIPTION_NOT_FOUND";

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final PrescriptionRepository prescriptionRepository;
    private final ProduitRepository produitRepository;
    private final FormeRepository formeRepository;
    private final HelpersRepository helpersRepository;


    @Override
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    @Override
    public long addPrescription(PrescriptionRequest prescriptionRequest) {
        log.info("PrescriptionServiceImpl | addPrescription is called");

        Prescription prescription = Prescription.builder()
                .presentation(prescriptionRequest.getPresentation())
                .qte(prescriptionRequest.getQte())
                .conditionnement(prescriptionRequest.getConditionnement())
                .dose_qte(prescriptionRequest.getDose_qte())
                .dose(prescriptionRequest.getDose())
                .periode(prescriptionRequest.getPeriode())
                .adverbe(prescriptionRequest.getAdverbe())
                .duree_qte(prescriptionRequest.getDuree_qte())
                .duree_qte(prescriptionRequest.getDuree_qte())
                .duree(prescriptionRequest.getDuree())
                .note(prescriptionRequest.getNote())
                .heures(Str.convertListToString(prescriptionRequest.getHeures(), "#>"))
                .produit(produitRepository.findById(prescriptionRequest.getProduit_id()).orElseThrow())
                .forme(formeRepository.findById(prescriptionRequest.getForme_id()).orElseThrow())
                .build();
        prescription = prescriptionRepository.save(prescription);

        log.info("PrescriptionServiceImpl | addPrescription | Prescription Created");
        log.info("PrescriptionServiceImpl | addPrescription | Prescription Id : " + prescription.getId());
        return prescription.getId();
    }

    @Override
    public void addPrescription(List<PrescriptionRequest> prescriptionRequests) {
        log.info("PrescriptionServiceImpl | addPrescription is called");

        for (PrescriptionRequest prescriptionRequest : prescriptionRequests) {
            Prescription prescription = Prescription.builder()
                    .presentation(prescriptionRequest.getPresentation())
                    .qte(prescriptionRequest.getQte())
                    .conditionnement(prescriptionRequest.getConditionnement())
                    .dose_qte(prescriptionRequest.getDose_qte())
                    .dose(prescriptionRequest.getDose())
                    .periode(prescriptionRequest.getPeriode())
                    .adverbe(prescriptionRequest.getAdverbe())
                    .duree_qte(prescriptionRequest.getDuree_qte())
                    .duree(prescriptionRequest.getDuree())
                    .note(prescriptionRequest.getNote())
                    .heures(Str.convertListToString(prescriptionRequest.getHeures(), "#>"))
                    .produit(produitRepository.findById(prescriptionRequest.getProduit_id()).orElseThrow())
                    .forme(formeRepository.findById(prescriptionRequest.getForme_id()).orElseThrow())
                    .build();
            prescriptionRepository.save(prescription);
        }

        log.info("PrescriptionServiceImpl | addPrescription | Prescriptions Created");
    }

    @Override
    public PrescriptionResponse getPrescriptionById(long prescriptionId) {
        log.info("PrescriptionServiceImpl | getPrescriptionById is called");
        log.info("PrescriptionServiceImpl | getPrescriptionById | Get the prescription for prescriptionId: {}", prescriptionId);

        Prescription prescription
                = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(
                        () -> new BMCustomException("Prescription with given Id not found", NOT_FOUND));

        PrescriptionResponse prescriptionResponse = new PrescriptionResponse();
        copyProperties(prescription, prescriptionResponse);
        prescriptionResponse.setHeures_prise(Str.convertStringToList(prescription.getHeures(), "#>"));

        log.info("PrescriptionServiceImpl | getPrescriptionById | prescriptionResponse :" + prescriptionResponse.toString());

        return prescriptionResponse;
    }

    @Override
    public void editPrescription(PrescriptionRequest prescriptionRequest, long prescriptionId) {
        log.info("PrescriptionServiceImpl | editPrescription is called");

        Prescription prescription
                = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new BMCustomException(
                        "Prescription with given Id not found",
                        NOT_FOUND
                ));
        prescription.setPresentation(prescriptionRequest.getPresentation());
        prescription.setQte(prescriptionRequest.getQte());
        prescription.setConditionnement(prescriptionRequest.getConditionnement());
        prescription.setDuree_qte(prescriptionRequest.getDose_qte());
        prescription.setDose(prescriptionRequest.getDose());
        prescription.setPeriode(prescriptionRequest.getPeriode());
        prescription.setAdverbe(prescriptionRequest.getAdverbe());
        prescription.setDuree_qte(prescriptionRequest.getDuree_qte());
        prescription.setDuree(prescriptionRequest.getDuree());
        prescription.setNote(prescriptionRequest.getNote());
        prescription.setHeures(Str.convertListToString(prescriptionRequest.getHeures(), "#>"));
        prescription.setProduit(produitRepository.findById(prescriptionRequest.getProduit_id()).orElseThrow());
        prescription.setForme(formeRepository.findById(prescriptionRequest.getForme_id()).orElseThrow());
        prescriptionRepository.save(prescription);

        log.info("PrescriptionServiceImpl | editPrescription | Prescription Updated");
        log.info("PrescriptionServiceImpl | editPrescription | Prescription Id : " + prescription.getId());
    }

    @Override
    public void deletePrescriptionById(long prescriptionId) {
        log.info("Prescription id: {}", prescriptionId);

        if (!prescriptionRepository.existsById(prescriptionId)) {
            log.info("Im in this loop {}", !prescriptionRepository.existsById(prescriptionId));
            throw new BMCustomException(
                    "Prescription with given with Id: " + prescriptionId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Prescription with id: {}", prescriptionId);
        prescriptionRepository.deleteById(prescriptionId);
    }

    @Override
    public List<String> getAllDoseString() {
        log.info("PrescriptionServiceImpl | getAllDoseString is called");
        return helpersRepository.findByType("dose").stream().map(Helpers::getContent).collect(Collectors.toList());
    }

    @Override
    public List<String> getAllPeriodeString() {
        log.info("PrescriptionServiceImpl | getAllPeriodeString is called");
        return helpersRepository.findByType("periode").stream().map(Helpers::getContent).collect(Collectors.toList());
    }

    @Override
    public List<String> getAllAdverbeString() {
        log.info("PrescriptionServiceImpl | getAllAdverbeString is called");
        return helpersRepository.findByType("adverbe").stream().map(Helpers::getContent).collect(Collectors.toList());
    }

    @Override
    public List<String> getAllDureeString() {
        log.info("PrescriptionServiceImpl | getAllDureeString is called");
        return helpersRepository.findByType("duree").stream().map(Helpers::getContent).collect(Collectors.toList());
    }
}
