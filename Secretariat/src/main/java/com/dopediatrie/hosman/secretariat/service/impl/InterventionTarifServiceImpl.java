package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.InterventionTarif;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.InterventionTarifRequest;
import com.dopediatrie.hosman.secretariat.payload.response.InterventionTarifResponse;
import com.dopediatrie.hosman.secretariat.repository.InterventionTarifRepository;
import com.dopediatrie.hosman.secretariat.service.InterventionTarifService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class InterventionTarifServiceImpl implements InterventionTarifService {
    private final InterventionTarifRepository interventionTarifRepository;
    private final String NOT_FOUND = "INTERVENTION_TARIF_NOT_FOUND";

    @Override
    public List<InterventionTarif> getAllInterventionTarifs() {
        return interventionTarifRepository.findAll();
    }

    @Override
    public long addInterventionTarif(InterventionTarifRequest interventionTarifRequest) {
        log.info("InterventionTarifServiceImpl | addInterventionTarif is called");

        InterventionTarif interventionTarif
                = InterventionTarif.builder()
                .intervention_id(interventionTarifRequest.getIntervention_id())
                .tarif_id(interventionTarifRequest.getTarif_id())
                .build();

        interventionTarif = interventionTarifRepository.save(interventionTarif);

        log.info("InterventionTarifServiceImpl | addInterventionTarif | InterventionTarif Created");
        log.info("InterventionTarifServiceImpl | addInterventionTarif | InterventionTarif Id : " + interventionTarif.getId());
        return interventionTarif.getId();
    }

    @Override
    public InterventionTarifResponse getInterventionTarifById(long interventionTarifId) {
        log.info("InterventionTarifServiceImpl | getInterventionTarifById is called");
        log.info("InterventionTarifServiceImpl | getInterventionTarifById | Get the interventionTarif for interventionTarifId: {}", interventionTarifId);

        InterventionTarif interventionTarif
                = interventionTarifRepository.findById(interventionTarifId)
                .orElseThrow(
                        () -> new SecretariatCustomException("InterventionTarif with given Id not found", NOT_FOUND));

        InterventionTarifResponse interventionTarifResponse = new InterventionTarifResponse();

        copyProperties(interventionTarif, interventionTarifResponse);

        log.info("InterventionTarifServiceImpl | getInterventionTarifById | interventionTarifResponse :" + interventionTarifResponse.toString());

        return interventionTarifResponse;
    }

    @Override
    public void editInterventionTarif(InterventionTarifRequest interventionTarifRequest, long interventionTarifId) {
        log.info("InterventionTarifServiceImpl | editInterventionTarif is called");

        InterventionTarif interventionTarif
                = interventionTarifRepository.findById(interventionTarifId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "InterventionTarif with given Id not found",
                        NOT_FOUND
                ));
        interventionTarif.setIntervention_id(interventionTarifRequest.getIntervention_id());
        interventionTarif.setTarif_id(interventionTarifRequest.getTarif_id());
        interventionTarifRepository.save(interventionTarif);

        log.info("InterventionTarifServiceImpl | editInterventionTarif | InterventionTarif Updated");
        log.info("InterventionTarifServiceImpl | editInterventionTarif | InterventionTarif Id : " + interventionTarif.getId());
    }

    @Override
    public void deleteInterventionTarifById(long interventionTarifId) {
        log.info("InterventionTarif id: {}", interventionTarifId);

        if (!interventionTarifRepository.existsById(interventionTarifId)) {
            log.info("Im in this loop {}", !interventionTarifRepository.existsById(interventionTarifId));
            throw new SecretariatCustomException(
                    "InterventionTarif with given with Id: " + interventionTarifId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting InterventionTarif with id: {}", interventionTarifId);
        interventionTarifRepository.deleteById(interventionTarifId);
    }
}
