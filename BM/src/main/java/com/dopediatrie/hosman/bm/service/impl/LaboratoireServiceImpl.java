package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.entity.Laboratoire;
import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.request.LaboratoireRequest;
import com.dopediatrie.hosman.bm.payload.response.LaboratoireResponse;
import com.dopediatrie.hosman.bm.repository.AgenceRepository;
import com.dopediatrie.hosman.bm.repository.LaboratoireRepository;
import com.dopediatrie.hosman.bm.service.AgenceService;
import com.dopediatrie.hosman.bm.service.LaboratoireService;
import com.dopediatrie.hosman.bm.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class LaboratoireServiceImpl implements LaboratoireService {
    private final String NOT_FOUND = "LABORATOIRE_NOT_FOUND";

    private final LaboratoireRepository laboRepository;
    private final AgenceRepository agenceRepository;
    private final AgenceService agenceService;


    @Override
    public List<Laboratoire> getAllLaboratoires() {
        return laboRepository.findAll();
    }

    @Override
    public long addLaboratoire(LaboratoireRequest laboRequest) {
        log.info("LaboratoireServiceImpl | addLaboratoire is called");

        long agence_id = laboRequest.getAgence() != null ? agenceService.addAgence(laboRequest.getAgence()) : 0 ;

        Laboratoire labo;
        if(laboRepository.existsBySlug(Str.slug(laboRequest.getNom()))){
            labo = laboRepository.findBySlug(Str.slug(laboRequest.getNom())).orElseThrow();
            editLaboratoire(laboRequest, labo.getId());
        }else {
            labo = Laboratoire.builder()
                    .nom(laboRequest.getNom())
                    .slug(Str.slug(laboRequest.getNom()))
                    .tel1(laboRequest.getTel1())
                    .tel2(laboRequest.getTel2())
                    .email(laboRequest.getEmail())
                    .adresse(laboRequest.getAdresse())
                    .build();

            if(agence_id != 0)
                labo.setAgence(agenceRepository.findById(agence_id).get());

            labo = laboRepository.save(labo);
        }

        log.info("LaboratoireServiceImpl | addLaboratoire | Laboratoire Created");
        log.info("LaboratoireServiceImpl | addLaboratoire | Laboratoire Id : " + labo.getId());
        return labo.getId();
    }

    @Override
    public void addLaboratoire(List<LaboratoireRequest> laboRequests) {
        log.info("LaboratoireServiceImpl | addLaboratoire is called");

        for (LaboratoireRequest laboRequest: laboRequests) {
            long agence_id = laboRequest.getAgence() != null ? agenceService.addAgence(laboRequest.getAgence()) : 0 ;

            Laboratoire labo;
            if(laboRepository.existsBySlug(Str.slug(laboRequest.getNom()))){
                labo = laboRepository.findBySlug(Str.slug(laboRequest.getNom())).orElseThrow();
                editLaboratoire(laboRequest, labo.getId());
            }else {
                labo = Laboratoire.builder()
                        .nom(laboRequest.getNom())
                        .slug(Str.slug(laboRequest.getNom()))
                        .tel1(laboRequest.getTel1())
                        .tel2(laboRequest.getTel2())
                        .email(laboRequest.getEmail())
                        .adresse(laboRequest.getAdresse())
                        .build();

                if(agence_id != 0)
                    labo.setAgence(agenceRepository.findById(agence_id).get());

                laboRepository.save(labo);
            }
        }

        log.info("LaboratoireServiceImpl | addLaboratoire | Laboratoires Created");
    }

    @Override
    public LaboratoireResponse getLaboratoireById(long laboId) {
        log.info("LaboratoireServiceImpl | getLaboratoireById is called");
        log.info("LaboratoireServiceImpl | getLaboratoireById | Get the labo for laboId: {}", laboId);

        Laboratoire labo
                = laboRepository.findById(laboId)
                .orElseThrow(
                        () -> new BMCustomException("Laboratoire with given Id not found", NOT_FOUND));

        LaboratoireResponse laboResponse = new LaboratoireResponse();

        copyProperties(labo, laboResponse);

        log.info("LaboratoireServiceImpl | getLaboratoireById | laboResponse :" + laboResponse.toString());

        return laboResponse;
    }

    @Override
    public void editLaboratoire(LaboratoireRequest laboRequest, long laboId) {
        log.info("LaboratoireServiceImpl | editLaboratoire is called");

        Laboratoire labo
                = laboRepository.findById(laboId)
                .orElseThrow(() -> new BMCustomException(
                        "Laboratoire with given Id not found",
                        NOT_FOUND
                ));
        long agence_id = laboRequest.getAgence() != null ? agenceService.addAgence(laboRequest.getAgence()) : 0 ;

        labo.setNom(laboRequest.getNom());
        labo.setSlug(Str.slug(laboRequest.getNom()));
        labo.setTel1(laboRequest.getTel1());
        labo.setTel2(laboRequest.getTel2());
        labo.setEmail(laboRequest.getEmail());
        labo.setAdresse(laboRequest.getAdresse());
        if(agence_id != 0)
            labo.setAgence(agenceRepository.findById(agence_id).get());
        laboRepository.save(labo);

        log.info("LaboratoireServiceImpl | editLaboratoire | Laboratoire Updated");
        log.info("LaboratoireServiceImpl | editLaboratoire | Laboratoire Id : " + labo.getId());
    }

    @Override
    public void deleteLaboratoireById(long laboId) {
        log.info("Laboratoire id: {}", laboId);

        if (!laboRepository.existsById(laboId)) {
            log.info("Im in this loop {}", !laboRepository.existsById(laboId));
            throw new BMCustomException(
                    "Laboratoire with given with Id: " + laboId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Laboratoire with id: {}", laboId);
        laboRepository.deleteById(laboId);
    }

    @Override
    public List<Laboratoire> getLaboratoireByAgenceIdAndQ(long agenceId, String q) {
        log.info("LaboratoireServiceImpl | getLaboratoireById is called");
        return laboRepository.findByAgenceIdAndQueryString(agenceId, q);
    }

    @Override
    public List<Laboratoire> getLaboratoireByAgenceId(long agenceId) {
        log.info("LaboratoireServiceImpl | getLaboratoireById is called");
        return laboRepository.findByAgenceId(agenceId);
    }

    @Override
    public List<Laboratoire> getLaboratoireByQueryString(String q) {
        log.info("LaboratoireServiceImpl | getLaboratoireById is called");
        return laboRepository.findByQueryString(q);
    }
}
