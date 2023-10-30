package com.dopediatrie.hosman.stock.service.impl;

import com.dopediatrie.hosman.stock.entity.Posologie;
import com.dopediatrie.hosman.stock.exception.StockCustomException;
import com.dopediatrie.hosman.stock.payload.request.PosologieRequest;
import com.dopediatrie.hosman.stock.payload.response.PosologieResponse;
import com.dopediatrie.hosman.stock.repository.PosologieRepository;
import com.dopediatrie.hosman.stock.repository.ProduitRepository;
import com.dopediatrie.hosman.stock.service.PosologieService;
import com.dopediatrie.hosman.stock.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class PosologieServiceImpl implements PosologieService {
    private final PosologieRepository posologieRepository;
    private final ProduitRepository produitRepository;
    private final String NOT_FOUND = "POSOLOGIE_NOT_FOUND";

    @Override
    public List<Posologie> getAllPosologies() {
        return posologieRepository.findAll();
    }

    @Override
    public long addPosologie(PosologieRequest posologieRequest) {
        log.info("PosologieServiceImpl | addPosologie is called");

        Posologie posologie
                = Posologie.builder()
                .libelle(posologieRequest.getLibelle())
                .type(posologieRequest.getType())
                .slug(Str.slug(posologieRequest.getLibelle()))
                .produit(produitRepository.findById(posologieRequest.getProduit_id()).get())
                .build();

        posologie = posologieRepository.save(posologie);

        log.info("PosologieServiceImpl | addPosologie | Posologie Created");
        log.info("PosologieServiceImpl | addPosologie | Posologie Id : " + posologie.getId());
        return posologie.getId();
    }

    @Override
    public void addPosologie(List<PosologieRequest> posologieRequests) {
        log.info("PosologieServiceImpl | addPosologie is called");

        for (PosologieRequest posologieRequest: posologieRequests) {
            Posologie posologie
                    = Posologie.builder()
                    .libelle(posologieRequest.getLibelle())
                    .type(posologieRequest.getType())
                    .slug(Str.slug(posologieRequest.getLibelle()))
                    .produit(produitRepository.findById(posologieRequest.getProduit_id()).get())
                    .build();
            posologieRepository.save(posologie);
        }

        log.info("PosologieServiceImpl | addPosologie | Posologies Created");
    }

    @Override
    public PosologieResponse getPosologieById(long posologieId) {
        log.info("PosologieServiceImpl | getPosologieById is called");
        log.info("PosologieServiceImpl | getPosologieById | Get the posologie for posologieId: {}", posologieId);

        Posologie posologie
                = posologieRepository.findById(posologieId)
                .orElseThrow(
                        () -> new StockCustomException("Posologie with given Id not found", NOT_FOUND));

        PosologieResponse posologieResponse = new PosologieResponse();

        copyProperties(posologie, posologieResponse);

        log.info("PosologieServiceImpl | getPosologieById | posologieResponse :" + posologieResponse.toString());

        return posologieResponse;
    }

    @Override
    public void editPosologie(PosologieRequest posologieRequest, long posologieId) {
        log.info("PosologieServiceImpl | editPosologie is called");

        Posologie posologie
                = posologieRepository.findById(posologieId)
                .orElseThrow(() -> new StockCustomException(
                        "Posologie with given Id not found",
                        NOT_FOUND
                ));
        posologie.setLibelle(posologieRequest.getLibelle());
        posologie.setType(posologieRequest.getType());
        posologie.setSlug(Str.slug(posologieRequest.getLibelle()));
        posologie.setProduit(produitRepository.findById(posologieRequest.getProduit_id()).get());
        posologieRepository.save(posologie);

        log.info("PosologieServiceImpl | editPosologie | Posologie Updated");
        log.info("PosologieServiceImpl | editPosologie | Posologie Id : " + posologie.getId());
    }

    @Override
    public void deletePosologieById(long posologieId) {
        log.info("Posologie id: {}", posologieId);

        if (!posologieRepository.existsById(posologieId)) {
            log.info("Im in this loop {}", !posologieRepository.existsById(posologieId));
            throw new StockCustomException(
                    "Posologie with given with Id: " + posologieId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Posologie with id: {}", posologieId);
        posologieRepository.deleteById(posologieId);
    }
}
