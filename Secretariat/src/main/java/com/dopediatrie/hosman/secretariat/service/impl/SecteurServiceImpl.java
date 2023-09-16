package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Secteur;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.SecteurRequest;
import com.dopediatrie.hosman.secretariat.payload.response.SecteurResponse;
import com.dopediatrie.hosman.secretariat.repository.SecteurRepository;
import com.dopediatrie.hosman.secretariat.service.SecteurService;
import com.dopediatrie.hosman.secretariat.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class SecteurServiceImpl implements SecteurService {
    private final SecteurRepository secteurRepository;
    private final String NOT_FOUND = "SECTEUR_NOT_FOUND";

    @Override
    public List<Secteur> getAllSecteurs() {
        return secteurRepository.findAll();
    }

    @Override
    public long addSecteur(SecteurRequest secteurRequest) {
        log.info("SecteurServiceImpl | addSecteur is called");

        Secteur secteur
                = Secteur.builder()
                .libelle(secteurRequest.getLibelle())
                .slug(Str.slug(secteurRequest.getLibelle()))
                .couleur(secteurRequest.getCouleur())
                .code(secteurRequest.getCode())
                .build();

        secteur = secteurRepository.save(secteur);

        log.info("SecteurServiceImpl | addSecteur | Secteur Created");
        log.info("SecteurServiceImpl | addSecteur | Secteur Id : " + secteur.getId());
        return secteur.getId();
    }

    @Override
    public void addSecteur(List<SecteurRequest> secteurRequests) {
        log.info("SecteurServiceImpl | addSecteur is called");

        for (SecteurRequest secteurRequest : secteurRequests) {
            Secteur secteur
                    = Secteur.builder()
                    .libelle(secteurRequest.getLibelle())
                    .slug(Str.slug(secteurRequest.getLibelle()))
                    .couleur(secteurRequest.getCouleur())
                    .code(secteurRequest.getCode())
                    .build();

            secteurRepository.save(secteur);
        }

        log.info("SecteurServiceImpl | addSecteur | Secteur Created");
    }

    @Override
    public SecteurResponse getSecteurById(long secteurId) {
        log.info("SecteurServiceImpl | getSecteurById is called");
        log.info("SecteurServiceImpl | getSecteurById | Get the secteur for secteurId: {}", secteurId);

        Secteur secteur
                = secteurRepository.findById(secteurId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Secteur with given Id not found", NOT_FOUND));

        SecteurResponse secteurResponse = new SecteurResponse();

        copyProperties(secteur, secteurResponse);

        log.info("SecteurServiceImpl | getSecteurById | secteurResponse :" + secteurResponse.toString());

        return secteurResponse;
    }

    @Override
    public void editSecteur(SecteurRequest secteurRequest, long secteurId) {
        log.info("SecteurServiceImpl | editSecteur is called");

        Secteur secteur
                = secteurRepository.findById(secteurId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Secteur with given Id not found",
                        NOT_FOUND
                ));
        secteur.setLibelle(secteurRequest.getLibelle());
        secteur.setSlug(Str.slug(secteurRequest.getLibelle()));
        secteur.setCouleur(secteurRequest.getCouleur());
        secteur.setCode(secteurRequest.getCode());
        secteurRepository.save(secteur);

        log.info("SecteurServiceImpl | editSecteur | Secteur Updated");
        log.info("SecteurServiceImpl | editSecteur | Secteur Id : " + secteur.getId());
    }

    @Override
    public void deleteSecteurById(long secteurId) {
        log.info("Secteur id: {}", secteurId);

        if (!secteurRepository.existsById(secteurId)) {
            log.info("Im in this loop {}", !secteurRepository.existsById(secteurId));
            throw new SecretariatCustomException(
                    "Secteur with given with Id: " + secteurId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Secteur with id: {}", secteurId);
        secteurRepository.deleteById(secteurId);
    }
}
