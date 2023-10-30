package com.dopediatrie.hosman.stock.service.impl;

import com.dopediatrie.hosman.stock.entity.Indication;
import com.dopediatrie.hosman.stock.exception.StockCustomException;
import com.dopediatrie.hosman.stock.payload.request.NameRequest;
import com.dopediatrie.hosman.stock.payload.response.NameResponse;
import com.dopediatrie.hosman.stock.repository.IndicationRepository;
import com.dopediatrie.hosman.stock.repository.ProduitRepository;
import com.dopediatrie.hosman.stock.service.IndicationService;
import com.dopediatrie.hosman.stock.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class IndicationServiceImpl implements IndicationService {
    private final IndicationRepository indicationRepository;
    private final ProduitRepository produitRepository;
    private final String NOT_FOUND = "INDICATION_NOT_FOUND";

    @Override
    public List<Indication> getAllIndications() {
        return indicationRepository.findAll();
    }

    @Override
    public long addIndication(NameRequest indicationRequest) {
        log.info("IndicationServiceImpl | addIndication is called");

        Indication indication
                = Indication.builder()
                .libelle(indicationRequest.getLibelle())
                .slug(Str.slug(indicationRequest.getLibelle()))
                .produit(produitRepository.findById(indicationRequest.getProduit_id()).get())
                .build();

        indication = indicationRepository.save(indication);

        log.info("IndicationServiceImpl | addIndication | Indication Created");
        log.info("IndicationServiceImpl | addIndication | Indication Id : " + indication.getId());
        return indication.getId();
    }

    @Override
    public void addIndication(List<NameRequest> indicationRequests) {
        log.info("IndicationServiceImpl | addIndication is called");

        for (NameRequest indicationRequest: indicationRequests) {
            Indication indication
                    = Indication.builder()
                    .libelle(indicationRequest.getLibelle())
                    .slug(Str.slug(indicationRequest.getLibelle()))
                    .produit(produitRepository.findById(indicationRequest.getProduit_id()).get())
                    .build();
            indicationRepository.save(indication);
        }

        log.info("IndicationServiceImpl | addIndication | Indications Created");
    }

    @Override
    public NameResponse getIndicationById(long indicationId) {
        log.info("IndicationServiceImpl | getIndicationById is called");
        log.info("IndicationServiceImpl | getIndicationById | Get the indication for indicationId: {}", indicationId);

        Indication indication
                = indicationRepository.findById(indicationId)
                .orElseThrow(
                        () -> new StockCustomException("Indication with given Id not found", NOT_FOUND));

        NameResponse indicationResponse = new NameResponse();

        copyProperties(indication, indicationResponse);

        log.info("IndicationServiceImpl | getIndicationById | indicationResponse :" + indicationResponse.toString());

        return indicationResponse;
    }

    @Override
    public void editIndication(NameRequest indicationRequest, long indicationId) {
        log.info("IndicationServiceImpl | editIndication is called");

        Indication indication
                = indicationRepository.findById(indicationId)
                .orElseThrow(() -> new StockCustomException(
                        "Indication with given Id not found",
                        NOT_FOUND
                ));
        indication.setLibelle(indicationRequest.getLibelle());
        indication.setSlug(Str.slug(indicationRequest.getLibelle()));
        indication.setProduit(produitRepository.findById(indicationRequest.getProduit_id()).get());
        indicationRepository.save(indication);

        log.info("IndicationServiceImpl | editIndication | Indication Updated");
        log.info("IndicationServiceImpl | editIndication | Indication Id : " + indication.getId());
    }

    @Override
    public void deleteIndicationById(long indicationId) {
        log.info("Indication id: {}", indicationId);

        if (!indicationRepository.existsById(indicationId)) {
            log.info("Im in this loop {}", !indicationRepository.existsById(indicationId));
            throw new StockCustomException(
                    "Indication with given with Id: " + indicationId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Indication with id: {}", indicationId);
        indicationRepository.deleteById(indicationId);
    }
}
