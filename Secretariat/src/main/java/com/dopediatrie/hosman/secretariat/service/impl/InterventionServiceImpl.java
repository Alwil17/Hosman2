package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Prestation;
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
    public List<Prestation> getAllInterventions() {
        return interventionRepository.findAll();
    }

    @Override
    public long addIntervention(InterventionRequest interventionRequest) {
        log.info("InterventionServiceImpl | addIntervention is called");

        Prestation prestation
                = Prestation.builder()
                .patient(patientRepository.findById(interventionRequest.getPatient_id()).get())
                .date_entree(interventionRequest.getDate_entree())
                .date_sortie(interventionRequest.getDate_sortie())
                .commentaires(interventionRequest.getCommentaires())
                .build();

        prestation = interventionRepository.save(prestation);

        log.info("InterventionServiceImpl | addIntervention | Prestation Created");
        log.info("InterventionServiceImpl | addIntervention | Prestation Id : " + prestation.getId());
        return prestation.getId();
    }

    @Override
    public InterventionResponse getInterventionById(long interventionId) {
        log.info("InterventionServiceImpl | getInterventionById is called");
        log.info("InterventionServiceImpl | getInterventionById | Get the prestation for interventionId: {}", interventionId);

        Prestation prestation
                = interventionRepository.findById(interventionId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Prestation with given Id not found", NOT_FOUND));

        InterventionResponse interventionResponse = new InterventionResponse();

        copyProperties(prestation, interventionResponse);

        log.info("InterventionServiceImpl | getInterventionById | interventionResponse :" + interventionResponse.toString());

        return interventionResponse;
    }

    @Override
    public void editIntervention(InterventionRequest interventionRequest, long interventionId) {
        log.info("InterventionServiceImpl | editIntervention is called");

        Prestation prestation
                = interventionRepository.findById(interventionId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Prestation with given Id not found",
                        NOT_FOUND
                ));
        prestation.setPatient(patientRepository.findById(interventionRequest.getPatient_id()).get());
        prestation.setDate_entree(interventionRequest.getDate_entree());
        prestation.setDate_sortie(interventionRequest.getDate_sortie());
        prestation.setCommentaires(interventionRequest.getCommentaires());
        interventionRepository.save(prestation);

        log.info("InterventionServiceImpl | editIntervention | Prestation Updated");
        log.info("InterventionServiceImpl | editIntervention | Prestation Id : " + prestation.getId());
    }

    @Override
    public void deleteInterventionById(long interventionId) {
        log.info("Prestation id: {}", interventionId);

        if (!interventionRepository.existsById(interventionId)) {
            log.info("Im in this loop {}", !interventionRepository.existsById(interventionId));
            throw new SecretariatCustomException(
                    "Prestation with given with Id: " + interventionId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Prestation with id: {}", interventionId);
        interventionRepository.deleteById(interventionId);
    }
}
