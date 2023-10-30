package com.dopediatrie.hosman.stock.service.impl;

import com.dopediatrie.hosman.stock.entity.ContreIndication;
import com.dopediatrie.hosman.stock.exception.StockCustomException;
import com.dopediatrie.hosman.stock.payload.request.NameRequest;
import com.dopediatrie.hosman.stock.payload.response.NameResponse;
import com.dopediatrie.hosman.stock.repository.ContreIndicationRepository;
import com.dopediatrie.hosman.stock.repository.ProduitRepository;
import com.dopediatrie.hosman.stock.service.ContreIndicationService;
import com.dopediatrie.hosman.stock.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

    @Override
    public List<ContreIndication> getAllContreIndications() {
        return contreIndcationRepository.findAll();
    }

    @Override
    public long addContreIndication(NameRequest contreIndcationRequest) {
        log.info("ContreIndicationServiceImpl | addContreIndication is called");

        ContreIndication contreIndcation
                = ContreIndication.builder()
                .libelle(contreIndcationRequest.getLibelle())
                .slug(Str.slug(contreIndcationRequest.getLibelle()))
                .produit(produitRepository.findById(contreIndcationRequest.getProduit_id()).get())
                .build();

        contreIndcation = contreIndcationRepository.save(contreIndcation);

        log.info("ContreIndicationServiceImpl | addContreIndication | ContreIndication Created");
        log.info("ContreIndicationServiceImpl | addContreIndication | ContreIndication Id : " + contreIndcation.getId());
        return contreIndcation.getId();
    }

    @Override
    public void addContreIndication(List<NameRequest> contreIndcationRequests) {
        log.info("ContreIndicationServiceImpl | addContreIndication is called");

        for (NameRequest contreIndcationRequest: contreIndcationRequests) {
            ContreIndication contreIndcation
                    = ContreIndication.builder()
                    .libelle(contreIndcationRequest.getLibelle())
                    .slug(Str.slug(contreIndcationRequest.getLibelle()))
                    .produit(produitRepository.findById(contreIndcationRequest.getProduit_id()).get())
                    .build();
            contreIndcationRepository.save(contreIndcation);
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
                        () -> new StockCustomException("ContreIndication with given Id not found", NOT_FOUND));

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
                .orElseThrow(() -> new StockCustomException(
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
            throw new StockCustomException(
                    "ContreIndication with given with Id: " + contreIndcationId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting ContreIndication with id: {}", contreIndcationId);
        contreIndcationRepository.deleteById(contreIndcationId);
    }
}
