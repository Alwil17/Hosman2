package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Reduction;
import com.dopediatrie.hosman.secretariat.entity.RubriqueDepense;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.NameRequest;
import com.dopediatrie.hosman.secretariat.payload.response.NameResponse;
import com.dopediatrie.hosman.secretariat.repository.RubriqueDepenseRepository;
import com.dopediatrie.hosman.secretariat.service.RubriqueDepenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class RubriqueDepenseServiceImpl implements RubriqueDepenseService {
    private final RubriqueDepenseRepository rubriqueDepenseRepository;
    private final String NOT_FOUND = "RUBRIQUE_DEPENSE_NOT_FOUND";

    @Override
    public List<RubriqueDepense> getAllRubriqueDepenses() {
        return rubriqueDepenseRepository.findAll();
    }

    @Override
    public long addRubriqueDepense(NameRequest rubriqueDepenseRequest) {
        log.info("RubriqueDepenseServiceImpl | addRubriqueDepense is called");

        RubriqueDepense rubriqueDepense;
        if(!rubriqueDepenseRepository.existsByNom(rubriqueDepenseRequest.getNom())){
            rubriqueDepense
                    = RubriqueDepense.builder()
                    .nom(rubriqueDepenseRequest.getNom())
                    .build();

            rubriqueDepense = rubriqueDepenseRepository.save(rubriqueDepense);
        }else{
            rubriqueDepense = rubriqueDepenseRepository.findByNom(rubriqueDepenseRequest.getNom()).orElseThrow();
        }

        log.info("RubriqueDepenseServiceImpl | addRubriqueDepense | RubriqueDepense Created");
        log.info("RubriqueDepenseServiceImpl | addRubriqueDepense | RubriqueDepense Id : " + rubriqueDepense.getId());
        return rubriqueDepense.getId();
    }

    @Override
    public NameResponse getRubriqueDepenseById(long rubriqueDepenseId) {
        log.info("RubriqueDepenseServiceImpl | getRubriqueDepenseById is called");
        log.info("RubriqueDepenseServiceImpl | getRubriqueDepenseById | Get the rubriqueDepense for rubriqueDepenseId: {}", rubriqueDepenseId);

        RubriqueDepense rubriqueDepense
                = rubriqueDepenseRepository.findById(rubriqueDepenseId)
                .orElseThrow(
                        () -> new SecretariatCustomException("RubriqueDepense with given Id not found", NOT_FOUND));

        NameResponse rubriqueDepenseResponse = new NameResponse();

        copyProperties(rubriqueDepense, rubriqueDepenseResponse);

        log.info("RubriqueDepenseServiceImpl | getRubriqueDepenseById | rubriqueDepenseResponse :" + rubriqueDepenseResponse.toString());

        return rubriqueDepenseResponse;
    }

    @Override
    public void editRubriqueDepense(NameRequest rubriqueDepenseRequest, long rubriqueDepenseId) {
        log.info("RubriqueDepenseServiceImpl | editRubriqueDepense is called");

        RubriqueDepense rubriqueDepense
                = rubriqueDepenseRepository.findById(rubriqueDepenseId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "RubriqueDepense with given Id not found",
                        NOT_FOUND
                ));
        rubriqueDepense.setNom(rubriqueDepenseRequest.getNom());
        rubriqueDepenseRepository.save(rubriqueDepense);

        log.info("RubriqueDepenseServiceImpl | editRubriqueDepense | RubriqueDepense Updated");
        log.info("RubriqueDepenseServiceImpl | editRubriqueDepense | RubriqueDepense Id : " + rubriqueDepense.getId());
    }

    @Override
    public void deleteRubriqueDepenseById(long rubriqueDepenseId) {
        log.info("RubriqueDepense id: {}", rubriqueDepenseId);

        if (!rubriqueDepenseRepository.existsById(rubriqueDepenseId)) {
            log.info("Im in this loop {}", !rubriqueDepenseRepository.existsById(rubriqueDepenseId));
            throw new SecretariatCustomException(
                    "RubriqueDepense with given with Id: " + rubriqueDepenseId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting RubriqueDepense with id: {}", rubriqueDepenseId);
        rubriqueDepenseRepository.deleteById(rubriqueDepenseId);
    }
}
