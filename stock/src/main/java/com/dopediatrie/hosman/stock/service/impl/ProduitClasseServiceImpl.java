package com.dopediatrie.hosman.stock.service.impl;

import com.dopediatrie.hosman.stock.entity.ProduitClasse;
import com.dopediatrie.hosman.stock.entity.ProduitClassePK;
import com.dopediatrie.hosman.stock.exception.StockCustomException;
import com.dopediatrie.hosman.stock.payload.request.ProduitClasseRequest;
import com.dopediatrie.hosman.stock.payload.response.ProduitClasseResponse;
import com.dopediatrie.hosman.stock.repository.ClasseRepository;
import com.dopediatrie.hosman.stock.repository.ProduitClasseRepository;
import com.dopediatrie.hosman.stock.repository.ProduitRepository;
import com.dopediatrie.hosman.stock.service.ProduitClasseService;
import com.dopediatrie.hosman.stock.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProduitClasseServiceImpl implements ProduitClasseService {
    private final ProduitClasseRepository produitClasseRepository;
    private final ProduitRepository produitRepository;
    private final ClasseRepository classeRepository;
    private final String NOT_FOUND = "PRODUIT_CLASSE_NOT_FOUND";

    @Override
    public List<ProduitClasse> getAllProduitClasses() {
        return produitClasseRepository.findAll();
    }

    @Override
    public ProduitClassePK addProduitClasse(ProduitClasseRequest produitClasseRequest) {
        log.info("ProduitClasseServiceImpl | addProduitClasse is called");

        ProduitClasse produitClasse
                = ProduitClasse.builder()
                .produit(produitRepository.findById(produitClasseRequest.getProduit_id()).get())
                .classe(classeRepository.findById(produitClasseRequest.getClasse_id()).get())
                .build();

        produitClasse = produitClasseRepository.save(produitClasse);

        log.info("ProduitClasseServiceImpl | addProduitClasse | ProduitClasse Created");
        log.info("ProduitClasseServiceImpl | addProduitClasse | ProduitClasse Id : " + produitClasse.getId());
        return produitClasse.getId();
    }

    @Override
    public ProduitClasseResponse getProduitClasseById(long produitClasseId) {
        log.info("ProduitClasseServiceImpl | getProduitClasseById is called");
        log.info("ProduitClasseServiceImpl | getProduitClasseById | Get the produitClasse for produitClasseId: {}", produitClasseId);

        ProduitClasse produitClasse
                = produitClasseRepository.findById(produitClasseId)
                .orElseThrow(
                        () -> new StockCustomException("ProduitClasse with given Id not found", NOT_FOUND));

        ProduitClasseResponse produitClasseResponse = new ProduitClasseResponse();

        copyProperties(produitClasse, produitClasseResponse);

        log.info("ProduitClasseServiceImpl | getProduitClasseById | produitClasseResponse :" + produitClasseResponse.toString());

        return produitClasseResponse;
    }

    @Override
    public void editProduitClasse(ProduitClasseRequest produitClasseRequest, long produitClasseId) {
        log.info("ProduitClasseServiceImpl | editProduitClasse is called");

        ProduitClasse produitClasse
                = produitClasseRepository.findById(produitClasseId)
                .orElseThrow(() -> new StockCustomException(
                        "ProduitClasse with given Id not found",
                        NOT_FOUND
                ));
        produitClasse.setProduit(produitRepository.findById(produitClasseRequest.getProduit_id()).get());
        produitClasse.setClasse(classeRepository.findById(produitClasseRequest.getClasse_id()).get());
        produitClasseRepository.save(produitClasse);

        log.info("ProduitClasseServiceImpl | editProduitClasse | ProduitClasse Updated");
        log.info("ProduitClasseServiceImpl | editProduitClasse | ProduitClasse Id : " + produitClasse.getId());
    }

    @Override
    public void deleteProduitClasseById(long produitClasseId) {
        log.info("ProduitClasse id: {}", produitClasseId);

        if (!produitClasseRepository.existsById(produitClasseId)) {
            log.info("Im in this loop {}", !produitClasseRepository.existsById(produitClasseId));
            throw new StockCustomException(
                    "ProduitClasse with given with Id: " + produitClasseId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting ProduitClasse with id: {}", produitClasseId);
        produitClasseRepository.deleteById(produitClasseId);
    }
}
