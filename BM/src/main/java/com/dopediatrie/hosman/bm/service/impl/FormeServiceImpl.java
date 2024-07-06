package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.entity.Forme;
import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.request.FormeRequest;
import com.dopediatrie.hosman.bm.payload.response.FormeResponse;
import com.dopediatrie.hosman.bm.repository.FormeRepository;
import com.dopediatrie.hosman.bm.repository.ProduitRepository;
import com.dopediatrie.hosman.bm.service.FormeService;
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
public class FormeServiceImpl implements FormeService {
    private final String NOT_FOUND = "FORME_NOT_FOUND";
    private final FormeRepository formeRepository;
    private final ProduitRepository produitRepository;


    @Autowired
    JdbcTemplate jdbcTemplate;

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
                        () -> new BMCustomException("Forme with given Id not found", NOT_FOUND));

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
                .orElseThrow(() -> new BMCustomException(
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
            throw new BMCustomException(
                    "Forme with given with Id: " + formeId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Forme with id: {}", formeId);
        formeRepository.deleteById(formeId);
    }

    @Override
    public List<String> getPresentationLike(String q) {
        log.info("FormeServiceImpl | getPresentationLike is called");
        String sqlquery = "SELECT distinct a.presentation as data FROM `forme` a where a.presentation like '%"+ q +"%'";

        List<String> res = jdbcTemplate.query(sqlquery, (resultSet, rowNum) -> resultSet.getString("data"));
        return res;
    }

    @Override
    public List<String> getDosageLike(String q) {
        log.info("FormeServiceImpl | getDosageLike is called");
        String sqlquery = "SELECT distinct a.dosage as data FROM `forme` a where a.dosage like '%"+ q +"%'";

        List<String> res = jdbcTemplate.query(sqlquery, (resultSet, rowNum) -> resultSet.getString("data"));
        return res;
    }

    @Override
    public List<String> getConditionnementLike(String q) {
        log.info("FormeServiceImpl | getConditionnementLike is called");
        String sqlquery = "SELECT distinct a.conditionnement as data FROM `forme` a where a.conditionnement like '%"+ q +"%'";

        List<String> res = jdbcTemplate.query(sqlquery, (resultSet, rowNum) -> resultSet.getString("data"));
        return res;
    }

    @Override
    public void deleteAllForProduitId(long produitId) {
        log.info("FormeServiceImpl | deleteAllForProduitId is called");
        formeRepository.deleteAllForProduitId(produitId);
    }
}
