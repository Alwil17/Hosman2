package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Depense;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.DepenseRequest;
import com.dopediatrie.hosman.secretariat.payload.response.DepenseResponse;
import com.dopediatrie.hosman.secretariat.repository.DepenseRepository;
import com.dopediatrie.hosman.secretariat.repository.PersonneRepository;
import com.dopediatrie.hosman.secretariat.repository.RubriqueDepenseRepository;
import com.dopediatrie.hosman.secretariat.service.DepenseService;
import com.dopediatrie.hosman.secretariat.service.PersonneService;
import com.dopediatrie.hosman.secretariat.service.RubriqueDepenseService;
import com.dopediatrie.hosman.secretariat.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class DepenseServiceImpl implements DepenseService {
    private final DepenseRepository depenseRepository;
    private final RubriqueDepenseRepository rubriqueDepenseRepository;
    private final PersonneRepository personneRepository;

    private final RubriqueDepenseService rubriqueDepenseService;
    private final PersonneService personneService;
    private final String NOT_FOUND = "DEPENSE_NOT_FOUND";

    @Override
    public List<Depense> getAllDepenses() {
        return depenseRepository.findAll();
    }

    @Override
    public long addDepense(DepenseRequest depenseRequest) {
        log.info("DepenseServiceImpl | addDepense is called");

        long rubriqueId = rubriqueDepenseService.addRubriqueDepense(depenseRequest.getRubrique());
        long beneficaireId = personneService.addPersonne(depenseRequest.getBeneficiaire());

        Depense depense
                = Depense.builder()
                .montant(depenseRequest.getMontant())
                .motif(depenseRequest.getMotif())
                .rubrique(rubriqueDepenseRepository.findById(rubriqueId).orElseThrow())
                .beneficiaire(personneRepository.findById(beneficaireId).orElseThrow())
                .accordeur_id(depenseRequest.getAccordeur_id())
                .caissier_id(depenseRequest.getCaissier_id())
                .date_depense(depenseRequest.getDate_depense())
                .recu(depenseRequest.getRecu())
                .build();

        depense = depenseRepository.save(depense);

        log.info("DepenseServiceImpl | addDepense | Depense Created");
        log.info("DepenseServiceImpl | addDepense | Depense Id : " + depense.getId());
        return depense.getId();
    }

    @Override
    public DepenseResponse getDepenseById(long depenseId) {
        log.info("DepenseServiceImpl | getDepenseById is called");
        log.info("DepenseServiceImpl | getDepenseById | Get the depense for depenseId: {}", depenseId);

        Depense depense
                = depenseRepository.findById(depenseId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Depense with given Id not found", NOT_FOUND));

        DepenseResponse depenseResponse = new DepenseResponse();

        copyProperties(depense, depenseResponse);

        log.info("DepenseServiceImpl | getDepenseById | depenseResponse :" + depenseResponse.toString());

        return depenseResponse;
    }

    @Override
    public void editDepense(DepenseRequest depenseRequest, long depenseId) {
        log.info("DepenseServiceImpl | editDepense is called");

        Depense depense
                = depenseRepository.findById(depenseId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Depense with given Id not found",
                        NOT_FOUND
                ));
        long rubriqueId;
        if(!depense.getRubrique().getNom().equals(depenseRequest.getRubrique().getNom())){
            if(rubriqueDepenseRepository.existsByNom(depenseRequest.getRubrique().getNom())){
                rubriqueId = rubriqueDepenseRepository.findByNom(depenseRequest.getRubrique().getNom()).orElseThrow().getId();
            }else {
                rubriqueId = rubriqueDepenseService.addRubriqueDepense(depenseRequest.getRubrique());
            }
        }else{
            rubriqueId = rubriqueDepenseService.addRubriqueDepense(depenseRequest.getRubrique());
        }

        personneService.editPersonne(depenseRequest.getBeneficiaire(), depense.getBeneficiaire().getId());

        depense.setMontant(depenseRequest.getMontant());
        depense.setMotif(depenseRequest.getMotif());
        depense.setRubrique(rubriqueDepenseRepository.findById(rubriqueId).orElseThrow());
        depense.setAccordeur_id(depenseRequest.getAccordeur_id());
        depense.setCaissier_id(depenseRequest.getCaissier_id());
        depense.setDate_depense(depenseRequest.getDate_depense());
        depense.setRecu(depenseRequest.getRecu());
        depenseRepository.save(depense);

        log.info("DepenseServiceImpl | editDepense | Depense Updated");
        log.info("DepenseServiceImpl | editDepense | Depense Id : " + depense.getId());
    }

    @Override
    public void deleteDepenseById(long depenseId) {
        log.info("Depense id: {}", depenseId);

        if (!depenseRepository.existsById(depenseId)) {
            log.info("Im in this loop {}", !depenseRepository.existsById(depenseId));
            throw new SecretariatCustomException(
                    "Depense with given with Id: " + depenseId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Depense with id: {}", depenseId);
        depenseRepository.deleteById(depenseId);
    }
}
