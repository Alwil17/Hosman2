package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.entity.InterventionDiagnostic;
import com.dopediatrie.hosman.bm.entity.InterventionDiagnosticPK;
import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.request.InterventionDiagnosticRequest;
import com.dopediatrie.hosman.bm.payload.response.InterventionDiagnosticResponse;
import com.dopediatrie.hosman.bm.repository.InterventionDiagnosticRepository;
import com.dopediatrie.hosman.bm.repository.InterventionRepository;
import com.dopediatrie.hosman.bm.repository.DiagnosticRepository;
import com.dopediatrie.hosman.bm.service.InterventionDiagnosticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class InterventionDiagnosticServiceImpl implements InterventionDiagnosticService {
    private final InterventionDiagnosticRepository interventionDiagnosticRepository;
    private final InterventionRepository interventionRepository;
    private final DiagnosticRepository diagnosticRepository;
    private final String NOT_FOUND = "INTERVENTION_MOTIF_NOT_FOUND";

    @Override
    public List<InterventionDiagnostic> getAllInterventionDiagnostics() {
        return interventionDiagnosticRepository.findAll();
    }

    @Override
    public InterventionDiagnosticPK addInterventionDiagnostic(InterventionDiagnosticRequest interventionDiagnosticRequest) {
        log.info("InterventionDiagnosticServiceImpl | addInterventionDiagnostic is called");

        InterventionDiagnosticPK pk = new InterventionDiagnosticPK();
        pk.intervention_id = interventionDiagnosticRequest.getIntervention_id();
        pk.diagnostic_id = interventionDiagnosticRequest.getDiagnostic_id();

        InterventionDiagnostic interventionDiagnostic
                = InterventionDiagnostic.builder()
                .id(pk)
                .intervention(interventionRepository.findById(interventionDiagnosticRequest.getIntervention_id()).orElseThrow())
                .diagnostic(diagnosticRepository.findById(interventionDiagnosticRequest.getDiagnostic_id()).orElseThrow())
                .build();

        interventionDiagnostic = interventionDiagnosticRepository.save(interventionDiagnostic);

        log.info("InterventionDiagnosticServiceImpl | addInterventionDiagnostic | InterventionDiagnostic Created");
        log.info("InterventionDiagnosticServiceImpl | addInterventionDiagnostic | InterventionDiagnostic Id : " + interventionDiagnostic.getId());
        return interventionDiagnostic.getId();
    }

    @Override
    public InterventionDiagnosticResponse getInterventionDiagnosticById(long interventionDiagnosticId) {
        log.info("InterventionDiagnosticServiceImpl | getInterventionDiagnosticById is called");
        log.info("InterventionDiagnosticServiceImpl | getInterventionDiagnosticById | Get the interventionDiagnostic for interventionDiagnosticId: {}", interventionDiagnosticId);

        InterventionDiagnostic interventionDiagnostic
                = interventionDiagnosticRepository.findById(interventionDiagnosticId)
                .orElseThrow(
                        () -> new BMCustomException("InterventionDiagnostic with given Id not found", NOT_FOUND));

        InterventionDiagnosticResponse interventionDiagnosticResponse = new InterventionDiagnosticResponse();

        copyProperties(interventionDiagnostic, interventionDiagnosticResponse);

        log.info("InterventionDiagnosticServiceImpl | getInterventionDiagnosticById | interventionDiagnosticResponse :" + interventionDiagnosticResponse.toString());

        return interventionDiagnosticResponse;
    }

    @Override
    public void editInterventionDiagnostic(InterventionDiagnosticRequest interventionDiagnosticRequest, long interventionDiagnosticId) {
        log.info("InterventionDiagnosticServiceImpl | editInterventionDiagnostic is called");

        InterventionDiagnostic interventionDiagnostic
                = interventionDiagnosticRepository.findById(interventionDiagnosticId)
                .orElseThrow(() -> new BMCustomException(
                        "InterventionDiagnostic with given Id not found",
                        NOT_FOUND
                ));
        interventionDiagnostic.setIntervention(interventionRepository.findById(interventionDiagnosticRequest.getIntervention_id()).orElseThrow());
        interventionDiagnostic.setDiagnostic(diagnosticRepository.findById(interventionDiagnosticRequest.getDiagnostic_id()).orElseThrow());
        interventionDiagnosticRepository.save(interventionDiagnostic);

        log.info("InterventionDiagnosticServiceImpl | editInterventionDiagnostic | InterventionDiagnostic Updated");
        log.info("InterventionDiagnosticServiceImpl | editInterventionDiagnostic | InterventionDiagnostic Id : " + interventionDiagnostic.getId());
    }

    @Override
    public void deleteInterventionDiagnosticById(long interventionDiagnosticId) {
        log.info("InterventionDiagnostic id: {}", interventionDiagnosticId);

        if (!interventionDiagnosticRepository.existsById(interventionDiagnosticId)) {
            log.info("Im in this loop {}", !interventionDiagnosticRepository.existsById(interventionDiagnosticId));
            throw new BMCustomException(
                    "InterventionDiagnostic with given with Id: " + interventionDiagnosticId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting InterventionDiagnostic with id: {}", interventionDiagnosticId);
        interventionDiagnosticRepository.deleteById(interventionDiagnosticId);
    }
}
