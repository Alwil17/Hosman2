package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.AssuranceTarif;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.AssuranceTarifRequest;
import com.dopediatrie.hosman.secretariat.payload.response.AssuranceTarifResponse;
import com.dopediatrie.hosman.secretariat.repository.AssuranceTarifRepository;
import com.dopediatrie.hosman.secretariat.service.AssuranceTarifService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class AssuranceTarifServiceImpl implements AssuranceTarifService {
    private final AssuranceTarifRepository assuranceTarifRepository;
    private final String NOT_FOUND = "ASSURANCE_TARIF_NOT_FOUND";

    @Override
    public List<AssuranceTarif> getAllAssuranceTarifs() {
        return assuranceTarifRepository.findAll();
    }

    @Override
    public long addAssuranceTarif(AssuranceTarifRequest assuranceTarifRequest) {
        log.info("AssuranceTarifServiceImpl | addAssuranceTarif is called");

        AssuranceTarif assuranceTarif
                = AssuranceTarif.builder()
                .assurance_id(assuranceTarifRequest.getAssurance_id())
                .tarif_id(assuranceTarifRequest.getTarif_id())
                .base_remboursement(assuranceTarifRequest.getBase_remboursement())
                .build();

        assuranceTarif = assuranceTarifRepository.save(assuranceTarif);

        log.info("AssuranceTarifServiceImpl | addAssuranceTarif | AssuranceTarif Created");
        log.info("AssuranceTarifServiceImpl | addAssuranceTarif | AssuranceTarif Id : " + assuranceTarif.getId());
        return assuranceTarif.getId();
    }

    @Override
    public AssuranceTarifResponse getAssuranceTarifById(long assuranceTarifId) {
        log.info("AssuranceTarifServiceImpl | getAssuranceTarifById is called");
        log.info("AssuranceTarifServiceImpl | getAssuranceTarifById | Get the assuranceTarif for assuranceTarifId: {}", assuranceTarifId);

        AssuranceTarif assuranceTarif
                = assuranceTarifRepository.findById(assuranceTarifId)
                .orElseThrow(
                        () -> new SecretariatCustomException("AssuranceTarif with given Id not found", NOT_FOUND));

        AssuranceTarifResponse assuranceTarifResponse = new AssuranceTarifResponse();

        copyProperties(assuranceTarif, assuranceTarifResponse);

        log.info("AssuranceTarifServiceImpl | getAssuranceTarifById | assuranceTarifResponse :" + assuranceTarifResponse.toString());

        return assuranceTarifResponse;
    }

    @Override
    public void editAssuranceTarif(AssuranceTarifRequest assuranceTarifRequest, long assuranceTarifId) {
        log.info("AssuranceTarifServiceImpl | editAssuranceTarif is called");

        AssuranceTarif assuranceTarif
                = assuranceTarifRepository.findById(assuranceTarifId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "AssuranceTarif with given Id not found",
                        NOT_FOUND
                ));
        assuranceTarif.setAssurance_id(assuranceTarifRequest.getAssurance_id());
        assuranceTarif.setTarif_id(assuranceTarifRequest.getTarif_id());
        assuranceTarif.setBase_remboursement(assuranceTarifRequest.getBase_remboursement());
        assuranceTarifRepository.save(assuranceTarif);

        log.info("AssuranceTarifServiceImpl | editAssuranceTarif | AssuranceTarif Updated");
        log.info("AssuranceTarifServiceImpl | editAssuranceTarif | AssuranceTarif Id : " + assuranceTarif.getId());
    }

    @Override
    public void deleteAssuranceTarifById(long assuranceTarifId) {
        log.info("AssuranceTarif id: {}", assuranceTarifId);

        if (!assuranceTarifRepository.existsById(assuranceTarifId)) {
            log.info("Im in this loop {}", !assuranceTarifRepository.existsById(assuranceTarifId));
            throw new SecretariatCustomException(
                    "AssuranceTarif with given with Id: " + assuranceTarifId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting AssuranceTarif with id: {}", assuranceTarifId);
        assuranceTarifRepository.deleteById(assuranceTarifId);
    }
}
