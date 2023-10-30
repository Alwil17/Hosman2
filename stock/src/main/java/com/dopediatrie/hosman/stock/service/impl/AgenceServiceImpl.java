package com.dopediatrie.hosman.stock.service.impl;

import com.dopediatrie.hosman.stock.entity.Agence;
import com.dopediatrie.hosman.stock.exception.StockCustomException;
import com.dopediatrie.hosman.stock.payload.request.AgenceRequest;
import com.dopediatrie.hosman.stock.payload.response.AgenceResponse;
import com.dopediatrie.hosman.stock.repository.AgenceRepository;
import com.dopediatrie.hosman.stock.service.AgenceService;
import com.dopediatrie.hosman.stock.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class AgenceServiceImpl implements AgenceService {
    private final AgenceRepository agenceRepository;
    private final String NOT_FOUND = "AGENCE_NOT_FOUND";

    @Override
    public List<Agence> getAllAgences() {
        return agenceRepository.findAll();
    }

    @Override
    public long addAgence(AgenceRequest agenceRequest) {
        log.info("AgenceServiceImpl | addAgence is called");

        Agence agence
                = Agence.builder()
                .nom(agenceRequest.getNom())
                .slug(Str.slug(agenceRequest.getNom()))
                .directeur(agenceRequest.getDirecteur())
                .tel1(agenceRequest.getTel1())
                .tel2(agenceRequest.getTel2())
                .email(agenceRequest.getEmail())
                .adresse(agenceRequest.getAdresse())
                .build();

        agence = agenceRepository.save(agence);

        log.info("AgenceServiceImpl | addAgence | Agence Created");
        log.info("AgenceServiceImpl | addAgence | Agence Id : " + agence.getId());
        return agence.getId();
    }

    @Override
    public void addAgence(List<AgenceRequest> agenceRequests) {
        log.info("AgenceServiceImpl | addAgence is called");

        for (AgenceRequest agenceRequest: agenceRequests) {
            Agence agence
                    = Agence.builder()
                    .nom(agenceRequest.getNom())
                    .slug(Str.slug(agenceRequest.getNom()))
                    .directeur(agenceRequest.getDirecteur())
                    .tel1(agenceRequest.getTel1())
                    .tel2(agenceRequest.getTel2())
                    .email(agenceRequest.getEmail())
                    .adresse(agenceRequest.getAdresse())
                    .build();
            agenceRepository.save(agence);
        }

        log.info("AgenceServiceImpl | addAgence | Agences Created");
    }

    @Override
    public AgenceResponse getAgenceById(long agenceId) {
        log.info("AgenceServiceImpl | getAgenceById is called");
        log.info("AgenceServiceImpl | getAgenceById | Get the agence for agenceId: {}", agenceId);

        Agence agence
                = agenceRepository.findById(agenceId)
                .orElseThrow(
                        () -> new StockCustomException("Agence with given Id not found", NOT_FOUND));

        AgenceResponse agenceResponse = new AgenceResponse();

        copyProperties(agence, agenceResponse);

        log.info("AgenceServiceImpl | getAgenceById | agenceResponse :" + agenceResponse.toString());

        return agenceResponse;
    }

    @Override
    public void editAgence(AgenceRequest agenceRequest, long agenceId) {
        log.info("AgenceServiceImpl | editAgence is called");

        Agence agence
                = agenceRepository.findById(agenceId)
                .orElseThrow(() -> new StockCustomException(
                        "Agence with given Id not found",
                        NOT_FOUND
                ));
        agence.setNom(agenceRequest.getNom());
        agence.setSlug(Str.slug(agenceRequest.getNom()));
        agence.setDirecteur(agenceRequest.getDirecteur());
        agence.setTel1(agenceRequest.getTel1());
        agence.setTel2(agenceRequest.getTel2());
        agence.setEmail(agenceRequest.getEmail());
        agence.setAdresse(agenceRequest.getAdresse());
        agenceRepository.save(agence);

        log.info("AgenceServiceImpl | editAgence | Agence Updated");
        log.info("AgenceServiceImpl | editAgence | Agence Id : " + agence.getId());
    }

    @Override
    public void deleteAgenceById(long agenceId) {
        log.info("Agence id: {}", agenceId);

        if (!agenceRepository.existsById(agenceId)) {
            log.info("Im in this loop {}", !agenceRepository.existsById(agenceId));
            throw new StockCustomException(
                    "Agence with given with Id: " + agenceId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Agence with id: {}", agenceId);
        agenceRepository.deleteById(agenceId);
    }
}
