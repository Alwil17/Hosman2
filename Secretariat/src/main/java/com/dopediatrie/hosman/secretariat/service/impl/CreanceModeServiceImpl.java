package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.CreanceMode;
import com.dopediatrie.hosman.secretariat.entity.CreanceModePK;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.CreanceModeRequest;
import com.dopediatrie.hosman.secretariat.payload.response.CreanceModeResponse;
import com.dopediatrie.hosman.secretariat.repository.CreanceModeRepository;
import com.dopediatrie.hosman.secretariat.repository.CreanceRepository;
import com.dopediatrie.hosman.secretariat.repository.ModePayementRepository;
import com.dopediatrie.hosman.secretariat.service.CreanceModeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class CreanceModeServiceImpl implements CreanceModeService {
    private final CreanceModeRepository creanceModeRepository;
    private final CreanceRepository creanceRepository;
    private final ModePayementRepository modeRepository;
    private final String NOT_FOUND = "ENCAISSEMENT_MODE_NOT_FOUND";

    @Override
    public List<CreanceMode> getAllCreanceModes() {
        return creanceModeRepository.findAll();
    }

    @Override
    public CreanceModePK addCreanceMode(CreanceModeRequest creanceModeRequest) {
        log.info("CreanceModeServiceImpl | addCreanceMode is called");

        CreanceModePK pk = new CreanceModePK();
        pk.creance_id = creanceModeRequest.getCreance_id();
        pk.mode_payement_id = creanceModeRequest.getMode_payement_id();

        CreanceMode creanceMode;
        Boolean check = creanceModeRepository.existsByCreance_IdAndMode_payement_Id(creanceModeRequest.getCreance_id(), creanceModeRequest.getMode_payement_id());
        if(check == null || !check){
            creanceMode = CreanceMode.builder()
                    .id(pk)
                    .creance(creanceRepository.findById(creanceModeRequest.getCreance_id()).orElseThrow())
                    .mode_payement(modeRepository.findById(creanceModeRequest.getMode_payement_id()).orElseThrow())
                    .montant(creanceModeRequest.getMontant())
                    .date_depot(creanceModeRequest.getDate_depot())
                    .build();
        }else{
            creanceMode = creanceModeRepository.findByCreance_IdAndMode_payement_Id(creanceModeRequest.getCreance_id(), creanceModeRequest.getMode_payement_id()).orElseThrow();
            creanceMode.setMontant(creanceModeRequest.getMontant() + creanceMode.getMontant());
            creanceMode.setDate_depot(creanceModeRequest.getDate_depot());
            creanceMode.setId(pk);
        }

        creanceMode = creanceModeRepository.save(creanceMode);

        log.info("CreanceModeServiceImpl | addCreanceMode | CreanceMode Created");
        log.info("CreanceModeServiceImpl | addCreanceMode | CreanceMode Id : " + creanceMode.getId());
        return creanceMode.getId();
    }

    @Override
    public CreanceModeResponse getCreanceModeById(long creanceModeId) {
        log.info("CreanceModeServiceImpl | getCreanceModeById is called");
        log.info("CreanceModeServiceImpl | getCreanceModeById | Get the creanceMode for creanceModeId: {}", creanceModeId);

        CreanceMode creanceMode
                = creanceModeRepository.findById(creanceModeId)
                .orElseThrow(
                        () -> new SecretariatCustomException("CreanceMode with given Id not found", NOT_FOUND));

        CreanceModeResponse creanceModeResponse = new CreanceModeResponse();

        copyProperties(creanceMode, creanceModeResponse);

        log.info("CreanceModeServiceImpl | getCreanceModeById | creanceModeResponse :" + creanceModeResponse.toString());

        return creanceModeResponse;
    }

    @Override
    public void editCreanceMode(CreanceModeRequest creanceModeRequest, long creanceModeId) {
        log.info("CreanceModeServiceImpl | editCreanceMode is called");

        CreanceMode creanceMode
                = creanceModeRepository.findById(creanceModeId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "CreanceMode with given Id not found",
                        NOT_FOUND
                ));
        creanceMode.setCreance(creanceRepository.findById(creanceModeRequest.getCreance_id()).orElseThrow());
        creanceMode.setMode_payement(modeRepository.findById(creanceModeRequest.getMode_payement_id()).orElseThrow());
        creanceMode.setMontant(creanceModeRequest.getMontant());
        creanceModeRepository.save(creanceMode);

        log.info("CreanceModeServiceImpl | editCreanceMode | CreanceMode Updated");
        log.info("CreanceModeServiceImpl | editCreanceMode | CreanceMode Id : " + creanceMode.getId());
    }

    @Override
    public void deleteCreanceModeById(long creanceModeId) {
        log.info("CreanceMode id: {}", creanceModeId);

        if (!creanceModeRepository.existsById(creanceModeId)) {
            log.info("Im in this loop {}", !creanceModeRepository.existsById(creanceModeId));
            throw new SecretariatCustomException(
                    "CreanceMode with given with Id: " + creanceModeId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting CreanceMode with id: {}", creanceModeId);
        creanceModeRepository.deleteById(creanceModeId);
    }
}
