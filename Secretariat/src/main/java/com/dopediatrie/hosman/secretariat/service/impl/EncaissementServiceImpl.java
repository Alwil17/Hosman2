package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Encaissement;
import com.dopediatrie.hosman.secretariat.entity.EncaissementMode;
import com.dopediatrie.hosman.secretariat.entity.EncaissementModePK;
import com.dopediatrie.hosman.secretariat.entity.ModePayement;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.EncaissementModeRequest;
import com.dopediatrie.hosman.secretariat.payload.request.EncaissementRequest;
import com.dopediatrie.hosman.secretariat.payload.request.FactureModeRequest;
import com.dopediatrie.hosman.secretariat.payload.response.EncaissementResponse;
import com.dopediatrie.hosman.secretariat.repository.*;
import com.dopediatrie.hosman.secretariat.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class EncaissementServiceImpl implements EncaissementService {
    private final EncaissementRepository encaissementRepository;
    private final EncaissementModeRepository eModeRepository;
    private final ModePayementRepository modePayementRepository;

    private final EncaissementModeService encaissementModeService;
    private final CaisseService caisseService;
    private final String NOT_FOUND = "ENCAISSEMENT_NOT_FOUND";

    @Override
    public List<Encaissement> getAllEncaissements() {
        return encaissementRepository.findAll();
    }

    @Override
    public long addEncaissement(EncaissementRequest encaissementRequest) {
        log.info("EncaissementServiceImpl | addEncaissement is called");

        Encaissement encaissement
                = Encaissement.builder()
                .provenance(encaissementRequest.getProvenance())
                .date_encaissement(encaissementRequest.getDate_encaissement())
                .build();

        encaissement = encaissementRepository.save(encaissement);

        double toCaisse = 0;
        for (EncaissementModeRequest eMode : encaissementRequest.getMode_payements()) {
            eMode.setEncaissement_id(encaissement.getId());
            encaissementModeService.addEncaissementMode(eMode);
            toCaisse += eMode.getMontant();
        }
        caisseService.addAmountCaisse(toCaisse);

        log.info("EncaissementServiceImpl | addEncaissement | Encaissement Created");
        log.info("EncaissementServiceImpl | addEncaissement | Encaissement Id : " + encaissement.getId());
        return encaissement.getId();
    }

    @Override
    public EncaissementResponse getEncaissementById(long encaissementId) {
        log.info("EncaissementServiceImpl | getEncaissementById is called");
        log.info("EncaissementServiceImpl | getEncaissementById | Get the encaissement for encaissementId: {}", encaissementId);

        Encaissement encaissement
                = encaissementRepository.findById(encaissementId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Encaissement with given Id not found", NOT_FOUND));

        EncaissementResponse encaissementResponse = new EncaissementResponse();

        copyProperties(encaissement, encaissementResponse);

        log.info("EncaissementServiceImpl | getEncaissementById | encaissementResponse :" + encaissementResponse.toString());

        return encaissementResponse;
    }

    @Override
    public void editEncaissement(EncaissementRequest encaissementRequest, long encaissementId) {
        log.info("EncaissementServiceImpl | editEncaissement is called");

        Encaissement encaissement
                = encaissementRepository.findById(encaissementId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Encaissement with given Id not found",
                        NOT_FOUND
                ));
        encaissement.setProvenance(encaissementRequest.getProvenance());
        encaissement.setDate_encaissement(encaissementRequest.getDate_encaissement());
        encaissementRepository.save(encaissement);

        for (EncaissementModeRequest eMode : encaissementRequest.getMode_payements()) {
            if(eModeRepository.existsByEncaissement_IdAndMode_payement_Id(eMode.getEncaissement_id(), eMode.getMode_payement_id())){
                EncaissementMode em = eModeRepository.findByEncaissement_IdAndMode_payement_Id(eMode.getEncaissement_id(), eMode.getMode_payement_id()).orElseThrow();
                em.setMontant(eMode.getMontant());
                eModeRepository.save(em);
            }else{
                eMode.setEncaissement_id(encaissement.getId());
                encaissementModeService.addEncaissementMode(eMode);
            }
        }

        log.info("EncaissementServiceImpl | editEncaissement | Encaissement Updated");
        log.info("EncaissementServiceImpl | editEncaissement | Encaissement Id : " + encaissement.getId());
    }

    @Override
    public void deleteEncaissementById(long encaissementId) {
        log.info("Encaissement id: {}", encaissementId);

        if (!encaissementRepository.existsById(encaissementId)) {
            log.info("Im in this loop {}", !encaissementRepository.existsById(encaissementId));
            throw new SecretariatCustomException(
                    "Encaissement with given with Id: " + encaissementId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Encaissement with id: {}", encaissementId);
        encaissementRepository.deleteById(encaissementId);
    }
}
