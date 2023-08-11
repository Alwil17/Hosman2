package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Intervention;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.InterventionRequest;
import com.dopediatrie.hosman.secretariat.payload.response.InterventionResponse;
import com.dopediatrie.hosman.secretariat.repository.InterventionRepository;
import com.dopediatrie.hosman.secretariat.repository.PatientRepository;
import com.dopediatrie.hosman.secretariat.service.InterventionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class InterventionServiceImpl implements InterventionService {
    private final InterventionRepository interventionRepository;
    private final PatientRepository patientRepository;
    private final String NOT_FOUND = "INTERVENTION_NOT_FOUND";

    @Override
    public List<Intervention> getAllInterventions() {
        return interventionRepository.findAll();
    }

    @Override
    public long addIntervention(InterventionRequest interventionRequest) {
        log.info("InterventionServiceImpl | addIntervention is called");

        Intervention intervention
                = Intervention.builder()
                .patient(patientRepository.findById(interventionRequest.getPatient_id()).get())
                .date_entree(interventionRequest.getDate_entree())
                .date_sortie(interventionRequest.getDate_sortie())
                .commentaires(interventionRequest.getCommentaires())
                .build();

        intervention = interventionRepository.save(intervention);

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
                        () -> new SecretariatCustomException("Intervention with given Id not found", NOT_FOUND));

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
                .orElseThrow(() -> new SecretariatCustomException(
                        "Intervention with given Id not found",
                        NOT_FOUND
                ));
        intervention.setPatient(patientRepository.findById(interventionRequest.getPatient_id()).get());
        intervention.setDate_entree(interventionRequest.getDate_entree());
        intervention.setDate_sortie(interventionRequest.getDate_sortie());
        intervention.setCommentaires(interventionRequest.getCommentaires());
        interventionRepository.save(intervention);

        log.info("InterventionServiceImpl | editIntervention | Intervention Updated");
        log.info("InterventionServiceImpl | editIntervention | Intervention Id : " + intervention.getId());
    }

    @Override
    public void deleteInterventionById(long interventionId) {
        log.info("Intervention id: {}", interventionId);

        if (!interventionRepository.existsById(interventionId)) {
            log.info("Im in this loop {}", !interventionRepository.existsById(interventionId));
            throw new SecretariatCustomException(
                    "Intervention with given with Id: " + interventionId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Intervention with id: {}", interventionId);
        interventionRepository.deleteById(interventionId);
    }
}
