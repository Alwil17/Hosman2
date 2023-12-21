package com.dopediatrie.hosman.hospi.service.impl;

import com.dopediatrie.hosman.hospi.entity.Lit;
import com.dopediatrie.hosman.hospi.exception.HospiCustomException;
import com.dopediatrie.hosman.hospi.payload.request.LitRequest;
import com.dopediatrie.hosman.hospi.payload.response.LitResponse;
import com.dopediatrie.hosman.hospi.repository.ChambreRepository;
import com.dopediatrie.hosman.hospi.repository.LitRepository;
import com.dopediatrie.hosman.hospi.service.LitService;
import com.dopediatrie.hosman.hospi.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class LitServiceImpl implements LitService {
    private final String NOT_FOUND = "LIT_NOT_FOUND";

    private final LitRepository litRepository;
    private final ChambreRepository chambreRepository;

    @Override
    public List<Lit> getAllLits() {
        return litRepository.findAll();
    }

    @Override
    public long addLit(LitRequest litRequest) {
        log.info("LitServiceImpl | addLit is called");
        Lit lit;
        if(!litRepository.existsByNom(litRequest.getNom())){
            lit = Lit.builder()
                    .nom(litRequest.getNom())
                    .slug(Str.slug(litRequest.getNom()))
                    .created_at(LocalDateTime.now())
                    .chambre(chambreRepository.findById(litRequest.getChambre_id()).orElseThrow())
                    .build();
            lit = litRepository.save(lit);
        }else{
            lit = litRepository.findByNomEquals(litRequest.getNom()).orElseThrow();
        }

        log.info("LitServiceImpl | addLit | Lit Created");
        log.info("LitServiceImpl | addLit | Lit Id : " + lit.getId());
        return lit.getId();
    }

    @Override
    public void addLit(List<LitRequest> litRequests) {
        log.info("LitServiceImpl | addLits is called");

        for (LitRequest litRequest : litRequests) {
            Lit lit;
            if(!litRepository.existsByNom(litRequest.getNom())){
                lit = Lit.builder()
                        .nom(litRequest.getNom())
                        .slug(Str.slug(litRequest.getNom()))
                        .created_at(LocalDateTime.now())
                        .chambre(chambreRepository.findById(litRequest.getChambre_id()).orElseThrow())
                        .build();
                litRepository.save(lit);
            }
        }
        log.info("LitServiceImpl | addLit | Lits Created");
    }

    @Override
    public LitResponse getLitById(long litId) {
        log.info("LitServiceImpl | getLitById is called");
        log.info("LitServiceImpl | getLitById | Get the lit for litId: {}", litId);

        Lit lit = litRepository.findById(litId)
                .orElseThrow(
                        () -> new HospiCustomException("Lit with given Id not found", NOT_FOUND));

        LitResponse litResponse = new LitResponse();

        copyProperties(lit, litResponse);

        log.info("LitServiceImpl | getLitById | litResponse :" + litResponse.toString());

        return litResponse;
    }

    @Override
    public void editLit(LitRequest litRequest, long litId) {
        log.info("LitServiceImpl | editLit is called");

        Lit lit = litRepository.findById(litId)
                .orElseThrow(() -> new HospiCustomException(
                        "Lit with given Id not found",
                        NOT_FOUND
                ));
        lit.setNom(litRequest.getNom());
        lit.setSlug(Str.slug(litRequest.getNom()));
        lit.setUpdated_at(LocalDateTime.now());
        lit.setChambre(chambreRepository.findById(litRequest.getChambre_id()).orElseThrow());
        litRepository.save(lit);

        log.info("LitServiceImpl | editLit | Lit Updated");
        log.info("LitServiceImpl | editLit | Lit Id : " + lit.getId());
    }

    @Override
    public void deleteLitById(long litId) {
        log.info("Lit id: {}", litId);

        if (!litRepository.existsById(litId)) {
            log.info("Im in this loop {}", !litRepository.existsById(litId));
            throw new HospiCustomException(
                    "Lit with given with Id: " + litId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Lit with id: {}", litId);
        litRepository.deleteById(litId);
    }

    @Override
    public List<Lit> getLitByChambreId(long chambreId) {
        log.info("LitServiceImpl | getLitByChambreId is called");
        return litRepository.findByChambreId(chambreId);
    }

    @Override
    public List<Lit> getLitByNom(String nom) {
        log.info("LitServiceImpl | getLitByNom is called");
        return litRepository.findByNomLike(nom);
    }
}
