package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Etat;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.EtatRequest;
import com.dopediatrie.hosman.secretariat.payload.response.EtatResponse;
import com.dopediatrie.hosman.secretariat.repository.EtatRepository;
import com.dopediatrie.hosman.secretariat.service.EtatService;
import com.dopediatrie.hosman.secretariat.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class EtatServiceImpl implements EtatService {
    private final EtatRepository etatRepository;
    private final String NOT_FOUND = "ETAT_NOT_FOUND";

    @Override
    public List<Etat> getAllEtats() {
        return etatRepository.findAll();
    }

    @Override
    public long addEtat(EtatRequest etatRequest) {
        log.info("EtatServiceImpl | addEtat is called");

        Etat etat
                = Etat.builder()
                .nom(etatRequest.getNom())
                .slug(Str.slug(etatRequest.getNom()))
                .couleur(etatRequest.getCouleur())
                .indice(etatRequest.getIndice())
                .structure_id(etatRequest.getStructure_id())
                .build();

        etat = etatRepository.save(etat);

        log.info("EtatServiceImpl | addEtat | Etat Created");
        log.info("EtatServiceImpl | addEtat | Etat Id : " + etat.getId());
        return etat.getId();
    }

    @Override
    public void addEtat(List<EtatRequest> etatRequests) {
        log.info("EtatServiceImpl | addEtat is called");

        for (EtatRequest etatRequest : etatRequests) {
            Etat etat
                    = Etat.builder()
                    .nom(etatRequest.getNom())
                    .slug(Str.slug(etatRequest.getNom()))
                    .couleur(etatRequest.getCouleur())
                    .indice(etatRequest.getIndice())
                    .structure_id(etatRequest.getStructure_id())
                    .build();

            etatRepository.save(etat);
        }

        log.info("EtatServiceImpl | addEtat | Etat Created");
    }

    @Override
    public EtatResponse getEtatById(long etatId) {
        log.info("EtatServiceImpl | getEtatById is called");
        log.info("EtatServiceImpl | getEtatById | Get the etat for etatId: {}", etatId);

        Etat etat
                = etatRepository.findById(etatId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Etat with given Id not found", NOT_FOUND));

        EtatResponse etatResponse = new EtatResponse();

        copyProperties(etat, etatResponse);

        log.info("EtatServiceImpl | getEtatById | etatResponse :" + etatResponse.toString());

        return etatResponse;
    }

    @Override
    public void editEtat(EtatRequest etatRequest, long etatId) {
        log.info("EtatServiceImpl | editEtat is called");

        Etat etat
                = etatRepository.findById(etatId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Etat with given Id not found",
                        NOT_FOUND
                ));
        etat.setNom(etatRequest.getNom());
        etat.setSlug(Str.slug(etatRequest.getNom()));
        etat.setCouleur(etatRequest.getCouleur());
        etat.setIndice(etatRequest.getIndice());
        etat.setStructure_id(etatRequest.getStructure_id());
        etatRepository.save(etat);

        log.info("EtatServiceImpl | editEtat | Etat Updated");
        log.info("EtatServiceImpl | editEtat | Etat Id : " + etat.getId());
    }

    @Override
    public void deleteEtatById(long etatId) {
        log.info("Etat id: {}", etatId);

        if (!etatRepository.existsById(etatId)) {
            log.info("Im in this loop {}", !etatRepository.existsById(etatId));
            throw new SecretariatCustomException(
                    "Etat with given with Id: " + etatId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Etat with id: {}", etatId);
        etatRepository.deleteById(etatId);
    }
}
