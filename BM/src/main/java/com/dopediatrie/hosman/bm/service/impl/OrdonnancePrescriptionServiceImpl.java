package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.entity.OrdonnancePrescription;
import com.dopediatrie.hosman.bm.entity.OrdonnancePrescriptionPK;
import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.request.OrdonnancePrescriptionRequest;
import com.dopediatrie.hosman.bm.payload.response.OrdonnancePrescriptionResponse;
import com.dopediatrie.hosman.bm.repository.OrdonnancePrescriptionRepository;
import com.dopediatrie.hosman.bm.repository.OrdonnanceRepository;
import com.dopediatrie.hosman.bm.repository.PrescriptionRepository;
import com.dopediatrie.hosman.bm.service.OrdonnancePrescriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrdonnancePrescriptionServiceImpl implements OrdonnancePrescriptionService {
    private final String NOT_FOUND = "ORDONNANCE_PRESCRIPTION_NOT_FOUND";

    private final OrdonnancePrescriptionRepository ordonnancePrescriptionRepository;
    private final OrdonnanceRepository ordonnanceRepository;
    private final PrescriptionRepository prescriptionRepository;


    @Override
    public List<OrdonnancePrescription> getAllOrdonnancePrescriptions() {
        return ordonnancePrescriptionRepository.findAll();
    }

    @Override
    public OrdonnancePrescriptionPK addOrdonnancePrescription(OrdonnancePrescriptionRequest ordonnancePrescriptionRequest) {
        log.info("OrdonnancePrescriptionServiceImpl | addOrdonnancePrescription is called");

        OrdonnancePrescriptionPK pk = new OrdonnancePrescriptionPK();
        pk.ordonnance_id = ordonnancePrescriptionRequest.getOrdonnance_id();
        pk.prescription_id = ordonnancePrescriptionRequest.getPrescription_id();

        OrdonnancePrescription ordonnancePrescription
                = OrdonnancePrescription.builder()
                .id(pk)
                .ordonnance(ordonnanceRepository.findById(ordonnancePrescriptionRequest.getOrdonnance_id()).orElseThrow())
                .prescription(prescriptionRepository.findById(ordonnancePrescriptionRequest.getPrescription_id()).orElseThrow())
                .build();

        ordonnancePrescription = ordonnancePrescriptionRepository.save(ordonnancePrescription);

        log.info("OrdonnancePrescriptionServiceImpl | addOrdonnancePrescription | OrdonnancePrescription Created");
        log.info("OrdonnancePrescriptionServiceImpl | addOrdonnancePrescription | OrdonnancePrescription Id : " + ordonnancePrescription.getId());
        return ordonnancePrescription.getId();
    }

    @Override
    public OrdonnancePrescriptionResponse getOrdonnancePrescriptionById(long ordonnancePrescriptionId) {
        log.info("OrdonnancePrescriptionServiceImpl | getOrdonnancePrescriptionById is called");
        log.info("OrdonnancePrescriptionServiceImpl | getOrdonnancePrescriptionById | Get the ordonnancePrescription for ordonnancePrescriptionId: {}", ordonnancePrescriptionId);

        OrdonnancePrescription ordonnancePrescription
                = ordonnancePrescriptionRepository.findById(ordonnancePrescriptionId)
                .orElseThrow(
                        () -> new BMCustomException("OrdonnancePrescription with given Id not found", NOT_FOUND));

        OrdonnancePrescriptionResponse ordonnancePrescriptionResponse = new OrdonnancePrescriptionResponse();

        copyProperties(ordonnancePrescription, ordonnancePrescriptionResponse);

        log.info("OrdonnancePrescriptionServiceImpl | getOrdonnancePrescriptionById | ordonnancePrescriptionResponse :" + ordonnancePrescriptionResponse.toString());

        return ordonnancePrescriptionResponse;
    }

    @Override
    public void editOrdonnancePrescription(OrdonnancePrescriptionRequest ordonnancePrescriptionRequest, long ordonnancePrescriptionId) {
        log.info("OrdonnancePrescriptionServiceImpl | editOrdonnancePrescription is called");

        OrdonnancePrescription ordonnancePrescription
                = ordonnancePrescriptionRepository.findById(ordonnancePrescriptionId)
                .orElseThrow(() -> new BMCustomException(
                        "OrdonnancePrescription with given Id not found",
                        NOT_FOUND
                ));
        ordonnancePrescription.setOrdonnance(ordonnanceRepository.findById(ordonnancePrescriptionRequest.getOrdonnance_id()).orElseThrow());
        ordonnancePrescription.setPrescription(prescriptionRepository.findById(ordonnancePrescriptionRequest.getPrescription_id()).orElseThrow());
        ordonnancePrescriptionRepository.save(ordonnancePrescription);

        log.info("OrdonnancePrescriptionServiceImpl | editOrdonnancePrescription | OrdonnancePrescription Updated");
        log.info("OrdonnancePrescriptionServiceImpl | editOrdonnancePrescription | OrdonnancePrescription Id : " + ordonnancePrescription.getId());
    }

    @Override
    public void deleteOrdonnancePrescriptionById(long ordonnancePrescriptionId) {
        log.info("OrdonnancePrescription id: {}", ordonnancePrescriptionId);

        if (!ordonnancePrescriptionRepository.existsById(ordonnancePrescriptionId)) {
            log.info("Im in this loop {}", !ordonnancePrescriptionRepository.existsById(ordonnancePrescriptionId));
            throw new BMCustomException(
                    "OrdonnancePrescription with given with Id: " + ordonnancePrescriptionId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting OrdonnancePrescription with id: {}", ordonnancePrescriptionId);
        ordonnancePrescriptionRepository.deleteById(ordonnancePrescriptionId);
    }

    @Override
    public void deleteAllForOrdonnanceId(long ordonnanceId) {
        log.info("deleteAllForOrdonnanceId id: {}", ordonnanceId);
        ordonnancePrescriptionRepository.deleteByOrdonnanceId(ordonnanceId);
    }
}
