package com.dopediatrie.hosman.stock.service.impl;

import com.dopediatrie.hosman.stock.entity.Forme;
import com.dopediatrie.hosman.stock.exception.StockCustomException;
import com.dopediatrie.hosman.stock.payload.request.FormeRequest;
import com.dopediatrie.hosman.stock.payload.response.FormeResponse;
import com.dopediatrie.hosman.stock.repository.FormeRepository;
import com.dopediatrie.hosman.stock.repository.ProduitRepository;
import com.dopediatrie.hosman.stock.service.FormeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class FormeServiceImpl implements FormeService {
    private final FormeRepository formeRepository;
    private final ProduitRepository produitRepository;
    private final String NOT_FOUND = "FORME_NOT_FOUND";

    @Override
    public List<Forme> getAllFormes() {
        return formeRepository.findAll();
    }

    @Override
    public long addForme(FormeRequest formeRequest) {
        log.info("FormeServiceImpl | addForme is called");

        Forme forme
                = Forme.builder()
                .presentation(formeRequest.getPresentation())
                .dosage(formeRequest.getDosage())
                .produit(produitRepository.findById(formeRequest.getProduit_id()).get())
                .conditionnement(formeRequest.getConditionnement())
                .prix(formeRequest.getPrix())
                .build();

        forme = formeRepository.save(forme);

        log.info("FormeServiceImpl | addForme | Forme Created");
        log.info("FormeServiceImpl | addForme | Forme Id : " + forme.getId());
        return forme.getId();
    }

    @Override
    public void addForme(List<FormeRequest> formeRequests) {
        log.info("FormeServiceImpl | addForme is called");

        for (FormeRequest formeRequest: formeRequests) {
            Forme forme
                    = Forme.builder()
                    .presentation(formeRequest.getPresentation())
                    .dosage(formeRequest.getDosage())
                    .produit(produitRepository.findById(formeRequest.getProduit_id()).get())
                    .conditionnement(formeRequest.getConditionnement())
                    .prix(formeRequest.getPrix())
                    .build();
            formeRepository.save(forme);
        }

        log.info("FormeServiceImpl | addForme | Formes Created");
    }

    @Override
    public FormeResponse getFormeById(long formeId) {
        log.info("FormeServiceImpl | getFormeById is called");
        log.info("FormeServiceImpl | getFormeById | Get the forme for formeId: {}", formeId);

        Forme forme
                = formeRepository.findById(formeId)
                .orElseThrow(
                        () -> new StockCustomException("Forme with given Id not found", NOT_FOUND));

        FormeResponse formeResponse = new FormeResponse();

        copyProperties(forme, formeResponse);

        log.info("FormeServiceImpl | getFormeById | formeResponse :" + formeResponse.toString());

        return formeResponse;
    }

    @Override
    public void editForme(FormeRequest formeRequest, long formeId) {
        log.info("FormeServiceImpl | editForme is called");

        Forme forme
                = formeRepository.findById(formeId)
                .orElseThrow(() -> new StockCustomException(
                        "Forme with given Id not found",
                        NOT_FOUND
                ));
        forme.setPresentation(formeRequest.getPresentation());
        forme.setDosage(formeRequest.getDosage());
        forme.setProduit(produitRepository.findById(formeRequest.getProduit_id()).get());
        forme.setConditionnement(formeRequest.getConditionnement());
        forme.setPrix(formeRequest.getPrix());
        formeRepository.save(forme);

        log.info("FormeServiceImpl | editForme | Forme Updated");
        log.info("FormeServiceImpl | editForme | Forme Id : " + forme.getId());
    }

    @Override
    public void deleteFormeById(long formeId) {
        log.info("Forme id: {}", formeId);

        if (!formeRepository.existsById(formeId)) {
            log.info("Im in this loop {}", !formeRepository.existsById(formeId));
            throw new StockCustomException(
                    "Forme with given with Id: " + formeId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Forme with id: {}", formeId);
        formeRepository.deleteById(formeId);
    }
}
