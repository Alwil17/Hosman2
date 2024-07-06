package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.entity.ContreIndication;
import com.dopediatrie.hosman.bm.entity.Posologie;
import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.request.NameRequest;
import com.dopediatrie.hosman.bm.payload.response.NameResponse;
import com.dopediatrie.hosman.bm.repository.ContreIndicationRepository;
import com.dopediatrie.hosman.bm.repository.ProduitRepository;
import com.dopediatrie.hosman.bm.service.ContreIndicationService;
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
public class ContreIndicationServiceImpl implements ContreIndicationService {
    private final ContreIndicationRepository contreIndcationRepository;
    private final ProduitRepository produitRepository;
    private final String NOT_FOUND = "CONTRE_INDICATION_NOT_FOUND";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<ContreIndication> getAllContreIndications() {
        return contreIndcationRepository.findAll();
    }

    @Override
    public long addContreIndication(NameRequest contreIndcationRequest) {
        log.info("ContreIndicationServiceImpl | addContreIndication is called");

        ContreIndication contreIndcation;
        if(contreIndcationRepository.existsBySlug(Str.slug(contreIndcationRequest.getLibelle()))){
            contreIndcation = contreIndcationRepository.findBySlug(Str.slug(contreIndcationRequest.getLibelle())).orElseThrow();
            editContreIndication(contreIndcationRequest, contreIndcation.getId());
        }else{
            contreIndcation = ContreIndication.builder()
                    .libelle(contreIndcationRequest.getLibelle())
                    .slug(Str.slug(contreIndcationRequest.getLibelle()))
                    .produit(produitRepository.findById(contreIndcationRequest.getProduit_id()).get())
                    .build();
            contreIndcation = contreIndcationRepository.save(contreIndcation);

        }

        log.info("ContreIndicationServiceImpl | addContreIndication | ContreIndication Created");
        log.info("ContreIndicationServiceImpl | addContreIndication | ContreIndication Id : " + contreIndcation.getId());
        return contreIndcation.getId();
    }

    @Override
    public void addContreIndication(List<NameRequest> contreIndcationRequests) {
        log.info("ContreIndicationServiceImpl | addContreIndication is called");

        for (NameRequest contreIndcationRequest: contreIndcationRequests) {
            ContreIndication contreIndcation;
            if(contreIndcationRepository.existsBySlug(Str.slug(contreIndcationRequest.getLibelle()))){
                contreIndcation = contreIndcationRepository.findBySlug(Str.slug(contreIndcationRequest.getLibelle())).orElseThrow();
                editContreIndication(contreIndcationRequest, contreIndcation.getId());
            }else{
                contreIndcation = ContreIndication.builder()
                        .libelle(contreIndcationRequest.getLibelle())
                        .slug(Str.slug(contreIndcationRequest.getLibelle()))
                        .produit(produitRepository.findById(contreIndcationRequest.getProduit_id()).get())
                        .build();
                contreIndcationRepository.save(contreIndcation);

            }
        }

        log.info("ContreIndicationServiceImpl | addContreIndication | ContreIndications Created");
    }

    @Override
    public NameResponse getContreIndicationById(long contreIndcationId) {
        log.info("ContreIndicationServiceImpl | getContreIndicationById is called");
        log.info("ContreIndicationServiceImpl | getContreIndicationById | Get the contreIndcation for contreIndcationId: {}", contreIndcationId);

        ContreIndication contreIndcation
                = contreIndcationRepository.findById(contreIndcationId)
                .orElseThrow(
                        () -> new BMCustomException("ContreIndication with given Id not found", NOT_FOUND));

        NameResponse contreIndcationResponse = new NameResponse();

        copyProperties(contreIndcation, contreIndcationResponse);

        log.info("ContreIndicationServiceImpl | getContreIndicationById | contreIndcationResponse :" + contreIndcationResponse.toString());

        return contreIndcationResponse;
    }

    @Override
    public void editContreIndication(NameRequest contreIndcationRequest, long contreIndcationId) {
        log.info("ContreIndicationServiceImpl | editContreIndication is called");

        ContreIndication contreIndcation
                = contreIndcationRepository.findById(contreIndcationId)
                .orElseThrow(() -> new BMCustomException(
                        "ContreIndication with given Id not found",
                        NOT_FOUND
                ));
        contreIndcation.setLibelle(contreIndcationRequest.getLibelle());
        contreIndcation.setSlug(Str.slug(contreIndcationRequest.getLibelle()));
        contreIndcation.setProduit(produitRepository.findById(contreIndcationRequest.getProduit_id()).get());
        contreIndcationRepository.save(contreIndcation);

        log.info("ContreIndicationServiceImpl | editContreIndication | ContreIndication Updated");
        log.info("ContreIndicationServiceImpl | editContreIndication | ContreIndication Id : " + contreIndcation.getId());
    }

    @Override
    public void deleteContreIndicationById(long contreIndcationId) {
        log.info("ContreIndication id: {}", contreIndcationId);

        if (!contreIndcationRepository.existsById(contreIndcationId)) {
            log.info("Im in this loop {}", !contreIndcationRepository.existsById(contreIndcationId));
            throw new BMCustomException(
                    "ContreIndication with given with Id: " + contreIndcationId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting ContreIndication with id: {}", contreIndcationId);
        contreIndcationRepository.deleteById(contreIndcationId);
    }

    @Override
    public List<String> getContreIndicationLike(String q) {
        log.info("ContreIndicationServiceImpl | getContreIndicationLike is called");
        String sqlquery = "SELECT distinct a.libelle as data FROM `contre_indication` a where a.libelle like '%"+ q +"%'";

        List<String> res = jdbcTemplate.query(sqlquery, (resultSet, rowNum) -> resultSet.getString("data"));
        return res;
    }
}
