package com.dopediatrie.hosman.auth.service.impl;

import com.dopediatrie.hosman.auth.entity.Secteur;
import com.dopediatrie.hosman.auth.exception.AuthCustomException;
import com.dopediatrie.hosman.auth.payload.request.SecteurRequest;
import com.dopediatrie.hosman.auth.payload.response.SecteurResponse;
import com.dopediatrie.hosman.auth.repository.DepartementRepository;
import com.dopediatrie.hosman.auth.repository.SecteurRepository;
import com.dopediatrie.hosman.auth.service.SecteurService;
import com.dopediatrie.hosman.auth.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class SecteurServiceImpl implements SecteurService {
    private final String NOT_FOUND = "SECTEUR_NOT_FOUND";

    private final SecteurRepository secteurRepository;
    private final DepartementRepository departementRepository;

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
                .departement(departementRepository.findByCodeEquals(secteurRequest.getDepartement()).get())
                .build();

        secteur = secteurRepository.save(secteur);

        log.info("SecteurServiceImpl | addSecteur | Secteur Created");
        log.info("SecteurServiceImpl | addSecteur | Secteur Id : " + secteur.getId());
        return secteur.getId();
    }

    @Override
    public void addSecteur(List<SecteurRequest> secteurRequests) {
        log.info("SecteurServiceImpl | addSecteur is called");

        for (SecteurRequest secteurRequest: secteurRequests) {
            Secteur secteur
                    = Secteur.builder()
                    .libelle(secteurRequest.getLibelle())
                    .slug(Str.slug(secteurRequest.getLibelle()))
                    .couleur(secteurRequest.getCouleur())
                    .code(secteurRequest.getCode())
                    .departement(departementRepository.findByCodeEquals(secteurRequest.getDepartement()).orElseThrow())
                    .build();
            secteurRepository.save(secteur);
        }

        log.info("SecteurServiceImpl | addSecteur | Secteurs Created");
    }

    @Override
    public SecteurResponse getSecteurById(long secteurId) {
        log.info("SecteurServiceImpl | getSecteurById is called");
        log.info("SecteurServiceImpl | getSecteurById | Get the secteur for secteurId: {}", secteurId);

        Secteur secteur
                = secteurRepository.findById(secteurId)
                .orElseThrow(
                        () -> new AuthCustomException("Secteur with given Id not found", NOT_FOUND));

        SecteurResponse secteurResponse = new SecteurResponse();

        copyProperties(secteur, secteurResponse);

        log.info("SecteurServiceImpl | getSecteurById | secteurResponse :" + secteurResponse.toString());

        return secteurResponse;
    }

    @Override
    public SecteurResponse getSecteurByUserId(long userId) {
        log.info("SecteurServiceImpl | getSecteurByUserId is called | userId {}", userId);
        Secteur secteur = secteurRepository.findByUserId(userId).orElseThrow(() -> new AuthCustomException(
                "Secteur with given Id not found",
                NOT_FOUND
        ));
        SecteurResponse secteurResponse = new SecteurResponse();
        copyProperties(secteur, secteurResponse);
        return secteurResponse;
    }

    @Override
    public List<Secteur> getSecteurByDepartement(String departement) {
        log.info("SecteurServiceImpl | getSecteurByDepartement is called");
        return secteurRepository.findByDepartement(departement);
    }

    @Override
    public List<Secteur> getSecteurByDepartementAndCode(String departement, String code) {
        log.info("SecteurServiceImpl | getSecteurByDepartementAndCode is called");
        return secteurRepository.findByDepartementAndCode(departement, code);
    }

    @Override
    public void editSecteur(SecteurRequest secteurRequest, long secteurId) {
        log.info("SecteurServiceImpl | editSecteur is called");

        Secteur secteur
                = secteurRepository.findById(secteurId)
                .orElseThrow(() -> new AuthCustomException(
                        "Secteur with given Id not found",
                        NOT_FOUND
                ));
        secteur.setLibelle(secteurRequest.getLibelle());
        secteur.setSlug(Str.slug(secteurRequest.getLibelle()));
        secteur.setCouleur(secteurRequest.getCouleur());
        secteur.setCode(secteurRequest.getCode());
        secteur.setDepartement(departementRepository.findByCodeEquals(secteurRequest.getDepartement()).get());
        secteurRepository.save(secteur);

        log.info("SecteurServiceImpl | editSecteur | Secteur Updated");
        log.info("SecteurServiceImpl | editSecteur | Secteur Id : " + secteur.getId());
    }

    @Override
    public void deleteSecteurById(long secteurId) {
        log.info("Secteur id: {}", secteurId);

        if (!secteurRepository.existsById(secteurId)) {
            log.info("Im in this loop {}", !secteurRepository.existsById(secteurId));
            throw new AuthCustomException(
                    "Secteur with given with Id: " + secteurId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Secteur with id: {}", secteurId);
        secteurRepository.deleteById(secteurId);
    }
}
