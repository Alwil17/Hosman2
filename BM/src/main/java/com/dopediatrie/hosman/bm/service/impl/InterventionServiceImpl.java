package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.entity.Constante;
import com.dopediatrie.hosman.bm.entity.Intervention;
import com.dopediatrie.hosman.bm.entity.InterventionMotif;
import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.request.InterventionDiagnosticRequest;
import com.dopediatrie.hosman.bm.payload.request.InterventionMotifRequest;
import com.dopediatrie.hosman.bm.payload.request.InterventionRequest;
import com.dopediatrie.hosman.bm.payload.response.InterventionResponse;
import com.dopediatrie.hosman.bm.repository.*;
import com.dopediatrie.hosman.bm.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class InterventionServiceImpl implements InterventionService {
    private final InterventionRepository interventionRepository;
    private final InterventionMotifRepository iMotifRepository;
    private final InterventionDiagnosticRepository iDiagnosticRepository;
    private final MotifRepository motifRepository;
    private final ConstanteRepository constanteRepository;

    private final InterventionMotifService interventionMotifService;
    private final InterventionDiagnosticService interventionDiagnosticService;
    private final ConstanteService constanteService;
    private final String NOT_FOUND = "INTERVENTION_NOT_FOUND";

    @Override
    public List<Intervention> getAllInterventions() {
        return interventionRepository.findAll();
    }

    @Override
    public long addIntervention(InterventionRequest interventionRequest) {
        log.info("InterventionServiceImpl | addIntervention is called");

        long constante_id = (interventionRequest.getConstante() != null) ? constanteService.addConstante(interventionRequest.getConstante()) : 0;

        Intervention intervention
                = Intervention.builder()
                .reference(interventionRequest.getReference())
                .type(interventionRequest.getType())
                .commentaire(interventionRequest.getCommentaire())
                .hdm(interventionRequest.getHdm())
                .secteur_id(interventionRequest.getSecteur_id())
                .patient_id(interventionRequest.getPatient_id())
                .attente_id(interventionRequest.getAttente_id())
                .date_intervention(interventionRequest.getDate_intervention())
                .build();

        if(constante_id != 0)
            intervention.setConstante(constanteRepository.findById(constante_id).orElseThrow());

        intervention = interventionRepository.save(intervention);

        double toCaisse = 0;
        for (InterventionMotifRequest iMotif : interventionRequest.getMotifs()) {
            iMotif.setIntervention_id(intervention.getId());
            interventionMotifService.addInterventionMotif(iMotif);
        }
        for (InterventionDiagnosticRequest iDiagnostic : interventionRequest.getDiagnostics()) {
            iDiagnostic.setIntervention_id(intervention.getId());
            interventionDiagnosticService.addInterventionDiagnostic(iDiagnostic);
        }

        log.info("InterventionServiceImpl | addIntervention | Intervention Created");
        log.info("InterventionServiceImpl | addIntervention | Intervention Id : " + intervention.getId());
        return intervention.getId();
    }

    @Override
    public InterventionResponse getInterventionById(long interventionId) {
        log.info("InterventionServiceImpl | getInterventionById is called");
        log.info("InterventionServiceImpl | getInterventionById | Get the intervention for interventionId: {}", interventionId);

        Intervention intervention
                = interventionRepository.findById(interventionId)
                .orElseThrow(
                        () -> new BMCustomException("Intervention with given Id not found", NOT_FOUND));

        InterventionResponse interventionResponse = new InterventionResponse();

        copyProperties(intervention, interventionResponse);

        log.info("InterventionServiceImpl | getInterventionById | interventionResponse :" + interventionResponse.toString());

        return interventionResponse;
    }

    @Override
    public void editIntervention(InterventionRequest interventionRequest, long interventionId) {
        log.info("InterventionServiceImpl | editIntervention is called");

        Intervention intervention
                = interventionRepository.findById(interventionId)
                .orElseThrow(() -> new BMCustomException(
                        "Intervention with given Id not found",
                        NOT_FOUND
                ));
        intervention.setReference(interventionRequest.getReference());
        intervention.setType(interventionRequest.getType());
        intervention.setCommentaire(interventionRequest.getCommentaire());
        intervention.setHdm(interventionRequest.getHdm());
        intervention.setSecteur_id(interventionRequest.getSecteur_id());
        intervention.setPatient_id(interventionRequest.getPatient_id());
        intervention.setAttente_id(interventionRequest.getAttente_id());
        intervention.setDate_intervention(interventionRequest.getDate_intervention());
        interventionRepository.save(intervention);

        log.info("InterventionServiceImpl | editIntervention | Intervention Updated");
        log.info("InterventionServiceImpl | editIntervention | Intervention Id : " + intervention.getId());
    }

    @Override
    public void deleteInterventionById(long interventionId) {
        log.info("Intervention id: {}", interventionId);

        if (!interventionRepository.existsById(interventionId)) {
            log.info("Im in this loop {}", !interventionRepository.existsById(interventionId));
            throw new BMCustomException(
                    "Intervention with given with Id: " + interventionId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Intervention with id: {}", interventionId);
        interventionRepository.deleteById(interventionId);
    }
}
