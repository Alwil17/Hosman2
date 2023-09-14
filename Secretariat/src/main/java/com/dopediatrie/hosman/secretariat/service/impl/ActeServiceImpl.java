package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Acte;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.ActeRequest;
import com.dopediatrie.hosman.secretariat.payload.response.ActeResponse;
import com.dopediatrie.hosman.secretariat.repository.ActeRepository;
import com.dopediatrie.hosman.secretariat.service.ActeService;
import com.dopediatrie.hosman.secretariat.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class ActeServiceImpl implements ActeService {
    private final ActeRepository acteRepository;
    private final String NOT_FOUND = "ACTE_NOT_FOUND";

    @Override
    public List<Acte> getAllActes() {
        return acteRepository.findAll();
    }

    @Override
    public long addActe(ActeRequest acteRequest) {
        log.info("ActeServiceImpl | addActe is called");

        Acte acte
                = Acte.builder()
                .libelle(acteRequest.getLibelle())
                .slug(Str.slug(acteRequest.getLibelle()))
                .structure_id(acteRequest.getStructure_id())
                .build();

        acte = acteRepository.save(acte);

        log.info("ActeServiceImpl | addActe | Acte Created");
        log.info("ActeServiceImpl | addActe | Acte Id : " + acte.getId());
        return acte.getId();
    }

    @Override
    public void addActe(List<ActeRequest> acteRequests) {
        log.info("ActeServiceImpl | addActe is called");

        for (ActeRequest acteRequest: acteRequests) {
            Acte acte
                    = Acte.builder()
                    .libelle(acteRequest.getLibelle())
                    .slug(Str.slug(acteRequest.getLibelle()))
                    .code(acteRequest.getCode())
                    .position(acteRequest.getPosition())
                    .structure_id(acteRequest.getStructure_id())
                    .build();
            acteRepository.save(acte);
        }

        log.info("ActeServiceImpl | addActe | Actes Created");
    }

    @Override
    public ActeResponse getActeById(long acteId) {
        log.info("ActeServiceImpl | getActeById is called");
        log.info("ActeServiceImpl | getActeById | Get the acte for acteId: {}", acteId);

        Acte acte
                = acteRepository.findById(acteId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Acte with given Id not found", NOT_FOUND));

        ActeResponse acteResponse = new ActeResponse();

        copyProperties(acte, acteResponse);

        log.info("ActeServiceImpl | getActeById | acteResponse :" + acteResponse.toString());

        return acteResponse;
    }

    @Override
    public void editActe(ActeRequest acteRequest, long acteId) {
        log.info("ActeServiceImpl | editActe is called");

        Acte acte
                = acteRepository.findById(acteId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Acte with given Id not found",
                        NOT_FOUND
                ));
        acte.setLibelle(acteRequest.getLibelle());
        acte.setSlug(Str.slug(acteRequest.getLibelle()));
        acte.setCode(acteRequest.getCode());
        acte.setCouleur(acteRequest.getCouleur());
        acte.setPosition(acteRequest.getPosition());
        acte.setShow_acte(acteRequest.isShow_acte());
        acte.setStructure_id(acteRequest.getStructure_id());
        acteRepository.save(acte);

        log.info("ActeServiceImpl | editActe | Acte Updated");
        log.info("ActeServiceImpl | editActe | Acte Id : " + acte.getId());
    }

    @Override
    public void deleteActeById(long acteId) {
        log.info("Acte id: {}", acteId);

        if (!acteRepository.existsById(acteId)) {
            log.info("Im in this loop {}", !acteRepository.existsById(acteId));
            throw new SecretariatCustomException(
                    "Acte with given with Id: " + acteId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Acte with id: {}", acteId);
        acteRepository.deleteById(acteId);
    }
}
