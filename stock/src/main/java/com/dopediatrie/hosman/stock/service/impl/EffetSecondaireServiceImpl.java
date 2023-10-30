package com.dopediatrie.hosman.stock.service.impl;

import com.dopediatrie.hosman.stock.entity.EffetSecondaire;
import com.dopediatrie.hosman.stock.exception.StockCustomException;
import com.dopediatrie.hosman.stock.payload.request.NameRequest;
import com.dopediatrie.hosman.stock.payload.response.NameResponse;
import com.dopediatrie.hosman.stock.repository.EffetSecondaireRepository;
import com.dopediatrie.hosman.stock.repository.ProduitRepository;
import com.dopediatrie.hosman.stock.service.EffetSecondaireService;
import com.dopediatrie.hosman.stock.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class EffetSecondaireServiceImpl implements EffetSecondaireService {
    private final EffetSecondaireRepository effetSecondaireRepository;
    private final ProduitRepository produitRepository;
    private final String NOT_FOUND = "CONTRE_INDICATION_NOT_FOUND";

    @Override
    public List<EffetSecondaire> getAllEffetSecondaires() {
        return effetSecondaireRepository.findAll();
    }

    @Override
    public long addEffetSecondaire(NameRequest effetSecondaireRequest) {
        log.info("EffetSecondaireServiceImpl | addEffetSecondaire is called");

        EffetSecondaire effetSecondaire
                = EffetSecondaire.builder()
                .libelle(effetSecondaireRequest.getLibelle())
                .slug(Str.slug(effetSecondaireRequest.getLibelle()))
                .produit(produitRepository.findById(effetSecondaireRequest.getProduit_id()).get())
                .build();

        effetSecondaire = effetSecondaireRepository.save(effetSecondaire);

        log.info("EffetSecondaireServiceImpl | addEffetSecondaire | EffetSecondaire Created");
        log.info("EffetSecondaireServiceImpl | addEffetSecondaire | EffetSecondaire Id : " + effetSecondaire.getId());
        return effetSecondaire.getId();
    }

    @Override
    public void addEffetSecondaire(List<NameRequest> effetSecondaireRequests) {
        log.info("EffetSecondaireServiceImpl | addEffetSecondaire is called");

        for (NameRequest effetSecondaireRequest: effetSecondaireRequests) {
            EffetSecondaire effetSecondaire
                    = EffetSecondaire.builder()
                    .libelle(effetSecondaireRequest.getLibelle())
                    .slug(Str.slug(effetSecondaireRequest.getLibelle()))
                    .produit(produitRepository.findById(effetSecondaireRequest.getProduit_id()).get())
                    .build();
            effetSecondaireRepository.save(effetSecondaire);
        }

        log.info("EffetSecondaireServiceImpl | addEffetSecondaire | EffetSecondaires Created");
    }

    @Override
    public NameResponse getEffetSecondaireById(long effetSecondaireId) {
        log.info("EffetSecondaireServiceImpl | getEffetSecondaireById is called");
        log.info("EffetSecondaireServiceImpl | getEffetSecondaireById | Get the effetSecondaire for effetSecondaireId: {}", effetSecondaireId);

        EffetSecondaire effetSecondaire
                = effetSecondaireRepository.findById(effetSecondaireId)
                .orElseThrow(
                        () -> new StockCustomException("EffetSecondaire with given Id not found", NOT_FOUND));

        NameResponse effetSecondaireResponse = new NameResponse();

        copyProperties(effetSecondaire, effetSecondaireResponse);

        log.info("EffetSecondaireServiceImpl | getEffetSecondaireById | effetSecondaireResponse :" + effetSecondaireResponse.toString());

        return effetSecondaireResponse;
    }

    @Override
    public void editEffetSecondaire(NameRequest effetSecondaireRequest, long effetSecondaireId) {
        log.info("EffetSecondaireServiceImpl | editEffetSecondaire is called");

        EffetSecondaire effetSecondaire
                = effetSecondaireRepository.findById(effetSecondaireId)
                .orElseThrow(() -> new StockCustomException(
                        "EffetSecondaire with given Id not found",
                        NOT_FOUND
                ));
        effetSecondaire.setLibelle(effetSecondaireRequest.getLibelle());
        effetSecondaire.setSlug(Str.slug(effetSecondaireRequest.getLibelle()));
        effetSecondaire.setProduit(produitRepository.findById(effetSecondaireRequest.getProduit_id()).get());
        effetSecondaireRepository.save(effetSecondaire);

        log.info("EffetSecondaireServiceImpl | editEffetSecondaire | EffetSecondaire Updated");
        log.info("EffetSecondaireServiceImpl | editEffetSecondaire | EffetSecondaire Id : " + effetSecondaire.getId());
    }

    @Override
    public void deleteEffetSecondaireById(long effetSecondaireId) {
        log.info("EffetSecondaire id: {}", effetSecondaireId);

        if (!effetSecondaireRepository.existsById(effetSecondaireId)) {
            log.info("Im in this loop {}", !effetSecondaireRepository.existsById(effetSecondaireId));
            throw new StockCustomException(
                    "EffetSecondaire with given with Id: " + effetSecondaireId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting EffetSecondaire with id: {}", effetSecondaireId);
        effetSecondaireRepository.deleteById(effetSecondaireId);
    }
}
