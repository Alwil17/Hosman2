package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Filiation;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.AssuranceRequest;
import com.dopediatrie.hosman.secretariat.payload.request.EmployeurRequest;
import com.dopediatrie.hosman.secretariat.payload.request.FiliationRequest;
import com.dopediatrie.hosman.secretariat.payload.request.ProfessionRequest;
import com.dopediatrie.hosman.secretariat.payload.response.FiliationResponse;
import com.dopediatrie.hosman.secretariat.payload.response.MedecinResponse;
import com.dopediatrie.hosman.secretariat.payload.response.SecteurResponse;
import com.dopediatrie.hosman.secretariat.repository.*;
import com.dopediatrie.hosman.secretariat.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class FiliationServiceImpl implements FiliationService {
    private final FiliationRepository filiationRepository;
    private final PatientRepository patientRepository;

    private final ProfessionRepository professionRepository;
    private final AssuranceRepository assuranceRepository;
    private final EmployeurRepository employeurRepository;

    private final AssuranceService assuranceService;
    private final ProfessionService professionService;
    private final EmployeurService employeurService;
    private final String NOT_FOUND = "FILIATION_NOT_FOUND";

    @Override
    public List<Filiation> getAllFiliations() { return filiationRepository.findAll(); }

    @Override
    public long addFiliation(FiliationRequest filiationRequest) {
        log.info("FiliationServiceImpl | addFiliation is called");

        Filiation filiation;
        if(filiationRepository.existsByFiliationByPatientIdAndType(filiationRequest.getPatient_id(), filiationRequest.getType())){
            filiation = filiationRepository.findByFiliationByPatientIdAndType(filiationRequest.getPatient_id(), filiationRequest.getType()).orElseThrow();
            editFiliation(filiationRequest, filiation.getId());
        }else {
            ProfessionRequest professionRequest = ProfessionRequest.builder().denomination(filiationRequest.getProfession()).build();
            EmployeurRequest employeurRequest = EmployeurRequest.builder().nom(filiationRequest.getEmployeur()).build();
            long profession_id = (filiationRequest.getProfession() != null) ? professionService.addProfession(professionRequest) : 0;
            long employeur_id = (filiationRequest.getEmployeur() != null) ? employeurService.addEmployeur(employeurRequest) : 0;


            filiation = Filiation.builder()
                    .nom(filiationRequest.getNom())
                    .prenoms(filiationRequest.getPrenoms())
                    .profession(professionRepository.findById(profession_id).orElseThrow())
                    .employeur(employeurRepository.findById(employeur_id).orElseThrow())
                    .assurance(filiationRequest.getAssurance())
                    .telephone(filiationRequest.getTelephone())
                    .sexe(filiationRequest.getSexe())
                    .type(filiationRequest.getType())
                    .annee_naissance(filiationRequest.getAnnee_naissance())
                    .patient(patientRepository.findById(filiationRequest.getPatient_id()).orElseThrow())
                    .build();

            if(profession_id != 0)
                filiation.setProfession(professionRepository.findById(profession_id).orElseThrow());
            if(employeur_id != 0)
                filiation.setEmployeur(employeurRepository.findById(employeur_id).orElseThrow());

            filiation = filiationRepository.save(filiation);
        }
        log.info("FiliationServiceImpl | addFiliation | Filiation Created");
        log.info("FiliationServiceImpl | addFiliation | Filiation Id : " + filiation.getId());
        return filiation.getId();
    }

    @Override
    public FiliationResponse getFiliationById(long filiationId) {
        log.info("FiliationServiceImpl | getFiliationById is called");
        log.info("FiliationServiceImpl | getFiliationById | Get the filiation for filiationId: {}", filiationId);

        Filiation filiation
                = filiationRepository.findById(filiationId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Filiation with given Id not found", NOT_FOUND));

        FiliationResponse filiationResponse = new FiliationResponse();

        copyProperties(filiation, filiationResponse);

        log.info("FiliationServiceImpl | getFiliationById | filiationResponse :" + filiationResponse.toString());

        return filiationResponse;
    }

    @Override
    public void editFiliation(FiliationRequest filiationRequest, long filiationId) {
        log.info("FiliationServiceImpl | editFiliation is called");

        ProfessionRequest professionRequest = ProfessionRequest.builder().denomination(filiationRequest.getProfession()).build();
        EmployeurRequest employeurRequest = EmployeurRequest.builder().nom(filiationRequest.getEmployeur()).build();
        long profession_id = (filiationRequest.getProfession() != null) ? professionService.addProfession(professionRequest) : 0;
        long employeur_id = (filiationRequest.getEmployeur() != null) ? employeurService.addEmployeur(employeurRequest) : 0;

        Filiation filiation
                = filiationRepository.findById(filiationId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Filiation with given Id not found",
                        NOT_FOUND
                ));
        filiation.setNom(filiationRequest.getNom());
        filiation.setPrenoms(filiationRequest.getPrenoms());
        filiation.setTelephone(filiationRequest.getTelephone());
        filiation.setSexe(filiationRequest.getSexe());
        filiation.setType(filiationRequest.getType());
        filiation.setAssurance(filiationRequest.getAssurance());
        filiation.setAnnee_naissance(filiationRequest.getAnnee_naissance());
        filiation.setPatient(patientRepository.findById(filiationRequest.getPatient_id()).orElseThrow());

        if(profession_id != 0)
            filiation.setProfession(professionRepository.findById(profession_id).orElseThrow());
        if(employeur_id != 0)
            filiation.setEmployeur(employeurRepository.findById(employeur_id).orElseThrow());

        filiationRepository.save(filiation);

        log.info("FiliationServiceImpl | editFiliation | Filiation Updated");
        log.info("FiliationServiceImpl | editFiliation | Filiation Id : " + filiation.getId());
    }

    @Override
    public void deleteFiliationById(long filiationId) {
        log.info("Filiation id: {}", filiationId);

        if (!filiationRepository.existsById(filiationId)) {
            log.info("Im in this loop {}", !filiationRepository.existsById(filiationId));
            throw new SecretariatCustomException(
                    "Filiation with given with Id: " + filiationId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Filiation with id: {}", filiationId);
        filiationRepository.deleteById(filiationId);
    }
}
