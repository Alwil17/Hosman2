package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.entity.Agence;
import com.dopediatrie.hosman.bm.entity.Indication;
import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.request.NameRequest;
import com.dopediatrie.hosman.bm.payload.response.NameResponse;
import com.dopediatrie.hosman.bm.repository.IndicationRepository;
import com.dopediatrie.hosman.bm.repository.ProduitRepository;
import com.dopediatrie.hosman.bm.service.IndicationService;
import com.dopediatrie.hosman.bm.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Indication> getAllIndications() {
        return indicationRepository.findAll();
    }

    @Override
    public long addIndication(NameRequest indicationRequest) {
        log.info("IndicationServiceImpl | addIndication is called");
        Indication indication;
        if(indicationRepository.existsBySlug(Str.slug(indicationRequest.getLibelle()))){
            indication = indicationRepository.findBySlug(Str.slug(indicationRequest.getLibelle())).orElseThrow();
            editIndication(indicationRequest, indication.getId());
        }else{
            indication = Indication.builder()
                    .libelle(indicationRequest.getLibelle())
                    .slug(Str.slug(indicationRequest.getLibelle()))
                    .produit(produitRepository.findById(indicationRequest.getProduit_id()).get())
                    .build();
            indication = indicationRepository.save(indication);

        }


        log.info("IndicationServiceImpl | addIndication | Indication Created");
        log.info("IndicationServiceImpl | addIndication | Indication Id : " + indication.getId());
        return indication.getId();
    }

    @Override
    public void addIndication(List<NameRequest> indicationRequests) {
        log.info("IndicationServiceImpl | addIndication is called");

        for (NameRequest indicationRequest: indicationRequests) {
            Indication indication;
            if(indicationRepository.existsBySlug(Str.slug(indicationRequest.getLibelle()))){
                indication = indicationRepository.findBySlug(Str.slug(indicationRequest.getLibelle())).orElseThrow();
                editIndication(indicationRequest, indication.getId());
            }else{
                indication = Indication.builder()
                        .libelle(indicationRequest.getLibelle())
                        .slug(Str.slug(indicationRequest.getLibelle()))
                        .produit(produitRepository.findById(indicationRequest.getProduit_id()).get())
                        .build();
                indicationRepository.save(indication);

            }
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
                        () -> new BMCustomException("Indication with given Id not found", NOT_FOUND));

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
                .orElseThrow(() -> new BMCustomException(
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
            throw new BMCustomException(
                    "Indication with given with Id: " + indicationId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Indication with id: {}", indicationId);
        indicationRepository.deleteById(indicationId);
    }

    @Override
    public List<String> getIndicationLike(String q) {
        log.info("IndicationServiceImpl | getIndicationLike is called");
        String sqlquery = "SELECT distinct a.libelle as data FROM `indication` a where a.libelle like '%"+ q +"%'";

        List<String> res = jdbcTemplate.query(sqlquery, (resultSet, rowNum) -> resultSet.getString("data"));
        return res;
    }
}
