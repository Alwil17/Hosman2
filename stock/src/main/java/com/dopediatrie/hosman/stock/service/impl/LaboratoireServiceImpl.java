package com.dopediatrie.hosman.stock.service.impl;

import com.dopediatrie.hosman.stock.entity.Laboratoire;
import com.dopediatrie.hosman.stock.exception.StockCustomException;
import com.dopediatrie.hosman.stock.payload.request.LaboratoireRequest;
import com.dopediatrie.hosman.stock.payload.response.LaboratoireResponse;
import com.dopediatrie.hosman.stock.repository.LaboratoireRepository;
import com.dopediatrie.hosman.stock.repository.AgenceRepository;
import com.dopediatrie.hosman.stock.service.LaboratoireService;
import com.dopediatrie.hosman.stock.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class LaboratoireServiceImpl implements LaboratoireService {
    private final LaboratoireRepository delegueRepository;
    private final AgenceRepository agenceRepository;
    private final String NOT_FOUND = "LABORATOIRE_NOT_FOUND";

    @Override
    public List<Laboratoire> getAllLaboratoires() {
        return delegueRepository.findAll();
    }

    @Override
    public long addLaboratoire(LaboratoireRequest delegueRequest) {
        log.info("LaboratoireServiceImpl | addLaboratoire is called");

        Laboratoire delegue
                = Laboratoire.builder()
                .nom(delegueRequest.getNom())
                .slug(Str.slug(delegueRequest.getNom()))
                .agence(agenceRepository.findById(delegueRequest.getAgence_id()).get())
                .tel1(delegueRequest.getTel1())
                .tel2(delegueRequest.getTel2())
                .email(delegueRequest.getEmail())
                .adresse(delegueRequest.getAdresse())
                .build();

        delegue = delegueRepository.save(delegue);

        log.info("LaboratoireServiceImpl | addLaboratoire | Laboratoire Created");
        log.info("LaboratoireServiceImpl | addLaboratoire | Laboratoire Id : " + delegue.getId());
        return delegue.getId();
    }

    @Override
    public void addLaboratoire(List<LaboratoireRequest> delegueRequests) {
        log.info("LaboratoireServiceImpl | addLaboratoire is called");

        for (LaboratoireRequest delegueRequest: delegueRequests) {
            Laboratoire delegue
                    = Laboratoire.builder()
                    .nom(delegueRequest.getNom())
                    .slug(Str.slug(delegueRequest.getNom()))
                    .agence(agenceRepository.findById(delegueRequest.getAgence_id()).get())
                    .tel1(delegueRequest.getTel1())
                    .tel2(delegueRequest.getTel2())
                    .email(delegueRequest.getEmail())
                    .adresse(delegueRequest.getAdresse())
                    .build();
            delegueRepository.save(delegue);
        }

        log.info("LaboratoireServiceImpl | addLaboratoire | Laboratoires Created");
    }

    @Override
    public LaboratoireResponse getLaboratoireById(long delegueId) {
        log.info("LaboratoireServiceImpl | getLaboratoireById is called");
        log.info("LaboratoireServiceImpl | getLaboratoireById | Get the delegue for delegueId: {}", delegueId);

        Laboratoire delegue
                = delegueRepository.findById(delegueId)
                .orElseThrow(
                        () -> new StockCustomException("Laboratoire with given Id not found", NOT_FOUND));

        LaboratoireResponse delegueResponse = new LaboratoireResponse();

        copyProperties(delegue, delegueResponse);

        log.info("LaboratoireServiceImpl | getLaboratoireById | delegueResponse :" + delegueResponse.toString());

        return delegueResponse;
    }

    @Override
    public void editLaboratoire(LaboratoireRequest delegueRequest, long delegueId) {
        log.info("LaboratoireServiceImpl | editLaboratoire is called");

        Laboratoire delegue
                = delegueRepository.findById(delegueId)
                .orElseThrow(() -> new StockCustomException(
                        "Laboratoire with given Id not found",
                        NOT_FOUND
                ));
        delegue.setNom(delegueRequest.getNom());
        delegue.setSlug(Str.slug(delegueRequest.getNom()));
        delegue.setAgence(agenceRepository.findById(delegueRequest.getAgence_id()).get());
        delegue.setTel1(delegueRequest.getTel1());
        delegue.setTel2(delegueRequest.getTel2());
        delegue.setEmail(delegueRequest.getEmail());
        delegue.setAdresse(delegueRequest.getAdresse());
        delegueRepository.save(delegue);

        log.info("LaboratoireServiceImpl | editLaboratoire | Laboratoire Updated");
        log.info("LaboratoireServiceImpl | editLaboratoire | Laboratoire Id : " + delegue.getId());
    }

    @Override
    public void deleteLaboratoireById(long delegueId) {
        log.info("Laboratoire id: {}", delegueId);

        if (!delegueRepository.existsById(delegueId)) {
            log.info("Im in this loop {}", !delegueRepository.existsById(delegueId));
            throw new StockCustomException(
                    "Laboratoire with given with Id: " + delegueId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Laboratoire with id: {}", delegueId);
        delegueRepository.deleteById(delegueId);
    }
}
