package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.SousActe;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.SousActeRequest;
import com.dopediatrie.hosman.secretariat.payload.response.SousActeResponse;
import com.dopediatrie.hosman.secretariat.repository.SousActeRepository;
import com.dopediatrie.hosman.secretariat.service.SousActeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class SousActeServiceImpl implements SousActeService {
    private final SousActeRepository sousActeRepository;
    private final String NOT_FOUND = "SOUSACTE_NOT_FOUND";

    @Override
    public List<SousActe> getAllSousActes() {
        return sousActeRepository.findAll();
    }

    @Override
    public long addSousActe(SousActeRequest sousActeRequest) {
        log.info("SousActeServiceImpl | addSousActe is called");

        SousActe sousActe
                = SousActe.builder()
                .libelle(sousActeRequest.getLibelle())
                .code(sousActeRequest.getCode())
                .acte_id(sousActeRequest.getActe_id())
                .build();

        sousActe = sousActeRepository.save(sousActe);

        log.info("SousActeServiceImpl | addSousActe | SousActe Created");
        log.info("SousActeServiceImpl | addSousActe | SousActe Id : " + sousActe.getId());
        return sousActe.getId();
    }

    @Override
    public SousActeResponse getSousActeById(long sousActeId) {
        log.info("SousActeServiceImpl | getSousActeById is called");
        log.info("SousActeServiceImpl | getSousActeById | Get the sousActe for sousActeId: {}", sousActeId);

        SousActe sousActe
                = sousActeRepository.findById(sousActeId)
                .orElseThrow(
                        () -> new SecretariatCustomException("SousActe with given Id not found", NOT_FOUND));

        SousActeResponse sousActeResponse = new SousActeResponse();

        copyProperties(sousActe, sousActeResponse);

        log.info("SousActeServiceImpl | getSousActeById | sousActeResponse :" + sousActeResponse.toString());

        return sousActeResponse;
    }

    @Override
    public void editSousActe(SousActeRequest sousActeRequest, long sousActeId) {
        log.info("SousActeServiceImpl | editSousActe is called");

        SousActe sousActe
                = sousActeRepository.findById(sousActeId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "SousActe with given Id not found",
                        NOT_FOUND
                ));
        sousActe.setLibelle(sousActeRequest.getLibelle());
        sousActe.setCode(sousActeRequest.getCode());
        sousActe.setActe_id(sousActeRequest.getActe_id());
        sousActeRepository.save(sousActe);

        log.info("SousActeServiceImpl | editSousActe | SousActe Updated");
        log.info("SousActeServiceImpl | editSousActe | SousActe Id : " + sousActe.getId());
    }

    @Override
    public void deleteSousActeById(long sousActeId) {
        log.info("SousActe id: {}", sousActeId);

        if (!sousActeRepository.existsById(sousActeId)) {
            log.info("Im in this loop {}", !sousActeRepository.existsById(sousActeId));
            throw new SecretariatCustomException(
                    "SousActe with given with Id: " + sousActeId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting SousActe with id: {}", sousActeId);
        sousActeRepository.deleteById(sousActeId);
    }
}
