package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.EncaissementMode;
import com.dopediatrie.hosman.secretariat.entity.EncaissementModePK;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.EncaissementModeRequest;
import com.dopediatrie.hosman.secretariat.payload.response.EncaissementModeResponse;
import com.dopediatrie.hosman.secretariat.repository.EncaissementRepository;
import com.dopediatrie.hosman.secretariat.repository.EncaissementModeRepository;
import com.dopediatrie.hosman.secretariat.repository.ModePayementRepository;
import com.dopediatrie.hosman.secretariat.service.EncaissementModeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class EncaissementModeServiceImpl implements EncaissementModeService {
    private final EncaissementModeRepository encaissementModeRepository;
    private final EncaissementRepository encaissementRepository;
    private final ModePayementRepository modeRepository;
    private final String NOT_FOUND = "ENCAISSEMENT_MODE_NOT_FOUND";

    @Override
    public List<EncaissementMode> getAllEncaissementModes() {
        return encaissementModeRepository.findAll();
    }

    @Override
    public EncaissementModePK addEncaissementMode(EncaissementModeRequest encaissementModeRequest) {
        log.info("EncaissementModeServiceImpl | addEncaissementMode is called");

        EncaissementModePK pk = new EncaissementModePK();
        pk.encaissement_id = encaissementModeRequest.getEncaissement_id();
        pk.mode_payement_id = encaissementModeRequest.getMode_payement_id();

        EncaissementMode encaissementMode
                = EncaissementMode.builder()
                .id(pk)
                .encaissement(encaissementRepository.findById(encaissementModeRequest.getEncaissement_id()).orElseThrow())
                .mode_payement(modeRepository.findById(encaissementModeRequest.getMode_payement_id()).orElseThrow())
                .montant(encaissementModeRequest.getMontant())
                .build();

        encaissementMode = encaissementModeRepository.save(encaissementMode);

        log.info("EncaissementModeServiceImpl | addEncaissementMode | EncaissementMode Created");
        log.info("EncaissementModeServiceImpl | addEncaissementMode | EncaissementMode Id : " + encaissementMode.getId());
        return encaissementMode.getId();
    }

    @Override
    public EncaissementModeResponse getEncaissementModeById(long encaissementModeId) {
        log.info("EncaissementModeServiceImpl | getEncaissementModeById is called");
        log.info("EncaissementModeServiceImpl | getEncaissementModeById | Get the encaissementMode for encaissementModeId: {}", encaissementModeId);

        EncaissementMode encaissementMode
                = encaissementModeRepository.findById(encaissementModeId)
                .orElseThrow(
                        () -> new SecretariatCustomException("EncaissementMode with given Id not found", NOT_FOUND));

        EncaissementModeResponse encaissementModeResponse = new EncaissementModeResponse();

        copyProperties(encaissementMode, encaissementModeResponse);

        log.info("EncaissementModeServiceImpl | getEncaissementModeById | encaissementModeResponse :" + encaissementModeResponse.toString());

        return encaissementModeResponse;
    }

    @Override
    public void editEncaissementMode(EncaissementModeRequest encaissementModeRequest, long encaissementModeId) {
        log.info("EncaissementModeServiceImpl | editEncaissementMode is called");

        EncaissementMode encaissementMode
                = encaissementModeRepository.findById(encaissementModeId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "EncaissementMode with given Id not found",
                        NOT_FOUND
                ));
        encaissementMode.setEncaissement(encaissementRepository.findById(encaissementModeRequest.getEncaissement_id()).orElseThrow());
        encaissementMode.setMode_payement(modeRepository.findById(encaissementModeRequest.getMode_payement_id()).orElseThrow());
        encaissementMode.setMontant(encaissementModeRequest.getMontant());
        encaissementModeRepository.save(encaissementMode);

        log.info("EncaissementModeServiceImpl | editEncaissementMode | EncaissementMode Updated");
        log.info("EncaissementModeServiceImpl | editEncaissementMode | EncaissementMode Id : " + encaissementMode.getId());
    }

    @Override
    public void deleteEncaissementModeById(long encaissementModeId) {
        log.info("EncaissementMode id: {}", encaissementModeId);

        if (!encaissementModeRepository.existsById(encaissementModeId)) {
            log.info("Im in this loop {}", !encaissementModeRepository.existsById(encaissementModeId));
            throw new SecretariatCustomException(
                    "EncaissementMode with given with Id: " + encaissementModeId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting EncaissementMode with id: {}", encaissementModeId);
        encaissementModeRepository.deleteById(encaissementModeId);
    }
}
