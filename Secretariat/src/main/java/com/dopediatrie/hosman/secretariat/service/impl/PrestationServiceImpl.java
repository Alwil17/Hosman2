package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Prestation;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.PrestationRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PrestationResponse;
import com.dopediatrie.hosman.secretariat.repository.*;
import com.dopediatrie.hosman.secretariat.service.PrestationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class PrestationServiceImpl implements PrestationService {
    private final PrestationRepository prestationRepository;
    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;
    private final SecteurRepository secteurRepository;
    private final FactureRepository factureRepository;
    private final String NOT_FOUND = "PRESTATION_NOT_FOUND";

    @Override
    public List<Prestation> getAllPrestations() {
        return prestationRepository.findAll();
    }

    @Override
    public long addPrestation(PrestationRequest prestationRequest) {
        log.info("PrestationServiceImpl | addPrestation is called");
        Prestation prestation
                = Prestation.builder()
                .patient(patientRepository.findById(prestationRequest.getPatient_id()).get())
                .consulteur(medecinRepository.findById(prestationRequest.getConsulteur_id()).get())
                .demandeur(medecinRepository.findById(prestationRequest.getDemandeur_id()).get())
                .secteur(secteurRepository.findById(prestationRequest.getSecteur_id()).get())
                .date_prestation(prestationRequest.getDate_prestation())
                .build();

        prestation = prestationRepository.save(prestation);

        log.info("PrestationServiceImpl | addPrestation | Prestation Created");
        log.info("PrestationServiceImpl | addPrestation | Prestation Id : " + prestation.getId());
        return prestation.getId();
    }

    @Override
    public PrestationResponse getPrestationById(long prestationId) {
        log.info("PrestationServiceImpl | getPrestationById is called");
        log.info("PrestationServiceImpl | getPrestationById | Get the prestation for prestationId: {}", prestationId);

        Prestation prestation
                = prestationRepository.findById(prestationId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Prestation with given Id not found", NOT_FOUND));

        PrestationResponse prestationResponse = new PrestationResponse();

        copyProperties(prestation, prestationResponse);

        log.info("PrestationServiceImpl | getPrestationById | prestationResponse :" + prestationResponse.toString());

        return prestationResponse;
    }

    @Override
    public void editPrestation(PrestationRequest prestationRequest, long prestationId) {
        log.info("PrestationServiceImpl | editPrestation is called");

        Prestation prestation
                = prestationRepository.findById(prestationId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Prestation with given Id not found",
                        NOT_FOUND
                ));
        prestation.setPatient(patientRepository.findById(prestationRequest.getPatient_id()).get());
        prestation.setConsulteur(medecinRepository.findById(prestationRequest.getConsulteur_id()).get());
        prestation.setDemandeur(medecinRepository.findById(prestationRequest.getDemandeur_id()).get());
        prestation.setSecteur(secteurRepository.findById(prestationRequest.getSecteur_id()).get());
        prestation.setDate_prestation(prestationRequest.getDate_prestation());
        prestation.setFacture(factureRepository.findById(prestationRequest.getFacture_id()).get());
        prestationRepository.save(prestation);

        log.info("PrestationServiceImpl | editPrestation | Prestation Updated");
        log.info("PrestationServiceImpl | editPrestation | Prestation Id : " + prestation.getId());
    }

    @Override
    public void deletePrestationById(long prestationId) {
        log.info("Prestation id: {}", prestationId);

        if (!prestationRepository.existsById(prestationId)) {
            log.info("Im in this loop {}", !prestationRepository.existsById(prestationId));
            throw new SecretariatCustomException(
                    "Prestation with given with Id: " + prestationId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Prestation with id: {}", prestationId);
        prestationRepository.deleteById(prestationId);
    }
}
