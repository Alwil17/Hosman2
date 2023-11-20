package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.entity.InterventionMotif;
import com.dopediatrie.hosman.bm.entity.InterventionMotifPK;
import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.request.InterventionMotifRequest;
import com.dopediatrie.hosman.bm.payload.response.InterventionMotifResponse;
import com.dopediatrie.hosman.bm.repository.InterventionRepository;
import com.dopediatrie.hosman.bm.repository.InterventionMotifRepository;
import com.dopediatrie.hosman.bm.repository.MotifRepository;
import com.dopediatrie.hosman.bm.service.InterventionMotifService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class InterventionMotifServiceImpl implements InterventionMotifService {
    private final InterventionMotifRepository interventionMotifRepository;
    private final InterventionRepository interventionRepository;
    private final MotifRepository modeRepository;
    private final String NOT_FOUND = "INTERVENTION_MOTIF_NOT_FOUND";

    @Override
    public List<InterventionMotif> getAllInterventionMotifs() {
        return interventionMotifRepository.findAll();
    }

    @Override
    public InterventionMotifPK addInterventionMotif(InterventionMotifRequest interventionMotifRequest) {
        log.info("InterventionMotifServiceImpl | addInterventionMotif is called");

        InterventionMotifPK pk = new InterventionMotifPK();
        pk.intervention_id = interventionMotifRequest.getIntervention_id();
        pk.motif_id = interventionMotifRequest.getMotif_id();

        InterventionMotif interventionMotif
                = InterventionMotif.builder()
                .id(pk)
                .intervention(interventionRepository.findById(interventionMotifRequest.getIntervention_id()).orElseThrow())
                .motif(modeRepository.findById(interventionMotifRequest.getMotif_id()).orElseThrow())
                .build();

        interventionMotif = interventionMotifRepository.save(interventionMotif);

        log.info("InterventionMotifServiceImpl | addInterventionMotif | InterventionMotif Created");
        log.info("InterventionMotifServiceImpl | addInterventionMotif | InterventionMotif Id : " + interventionMotif.getId());
        return interventionMotif.getId();
    }

    @Override
    public InterventionMotifResponse getInterventionMotifById(long interventionMotifId) {
        log.info("InterventionMotifServiceImpl | getInterventionMotifById is called");
        log.info("InterventionMotifServiceImpl | getInterventionMotifById | Get the interventionMotif for interventionMotifId: {}", interventionMotifId);

        InterventionMotif interventionMotif
                = interventionMotifRepository.findById(interventionMotifId)
                .orElseThrow(
                        () -> new BMCustomException("InterventionMotif with given Id not found", NOT_FOUND));

        InterventionMotifResponse interventionMotifResponse = new InterventionMotifResponse();

        copyProperties(interventionMotif, interventionMotifResponse);

        log.info("InterventionMotifServiceImpl | getInterventionMotifById | interventionMotifResponse :" + interventionMotifResponse.toString());

        return interventionMotifResponse;
    }

    @Override
    public void editInterventionMotif(InterventionMotifRequest interventionMotifRequest, long interventionMotifId) {
        log.info("InterventionMotifServiceImpl | editInterventionMotif is called");

        InterventionMotif interventionMotif
                = interventionMotifRepository.findById(interventionMotifId)
                .orElseThrow(() -> new BMCustomException(
                        "InterventionMotif with given Id not found",
                        NOT_FOUND
                ));
        interventionMotif.setIntervention(interventionRepository.findById(interventionMotifRequest.getIntervention_id()).orElseThrow());
        interventionMotif.setMotif(modeRepository.findById(interventionMotifRequest.getMotif_id()).orElseThrow());
        interventionMotifRepository.save(interventionMotif);

        log.info("InterventionMotifServiceImpl | editInterventionMotif | InterventionMotif Updated");
        log.info("InterventionMotifServiceImpl | editInterventionMotif | InterventionMotif Id : " + interventionMotif.getId());
    }

    @Override
    public void deleteInterventionMotifById(long interventionMotifId) {
        log.info("InterventionMotif id: {}", interventionMotifId);

        if (!interventionMotifRepository.existsById(interventionMotifId)) {
            log.info("Im in this loop {}", !interventionMotifRepository.existsById(interventionMotifId));
            throw new BMCustomException(
                    "InterventionMotif with given with Id: " + interventionMotifId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting InterventionMotif with id: {}", interventionMotifId);
        interventionMotifRepository.deleteById(interventionMotifId);
    }
}
