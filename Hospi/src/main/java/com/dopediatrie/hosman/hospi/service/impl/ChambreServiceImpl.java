package com.dopediatrie.hosman.hospi.service.impl;

import com.dopediatrie.hosman.hospi.entity.Chambre;
import com.dopediatrie.hosman.hospi.entity.Lit;
import com.dopediatrie.hosman.hospi.exception.HospiCustomException;
import com.dopediatrie.hosman.hospi.payload.request.ChambreRequest;
import com.dopediatrie.hosman.hospi.payload.response.ChambreResponse;
import com.dopediatrie.hosman.hospi.repository.ChambreRepository;
import com.dopediatrie.hosman.hospi.repository.LitRepository;
import com.dopediatrie.hosman.hospi.service.ChambreService;
import com.dopediatrie.hosman.hospi.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChambreServiceImpl implements ChambreService {
    private final String NOT_FOUND = "CHAMBRE_NOT_FOUND";

    private final ChambreRepository chambreRepository;
    private final LitRepository litRepository;

    @Override
    public List<Chambre> getAllChambres() {
        return chambreRepository.findAll();
    }

    @Override
    public long addChambre(ChambreRequest chambreRequest) {
        log.info("ChambreServiceImpl | addChambre is called");
        Chambre chambre;
        if(!chambreRepository.existsByNom(chambreRequest.getNom())){
            chambre = Chambre.builder()
                    .nom(chambreRequest.getNom())
                    .slug(Str.slug(chambreRequest.getNom()))
                    .created_at(LocalDateTime.now())
                    .build();
            chambre = chambreRepository.save(chambre);
        }else{
            chambre = chambreRepository.findByNomEquals(chambreRequest.getNom()).orElseThrow();
        }

        log.info("ChambreServiceImpl | addChambre | Chambre Created");
        log.info("ChambreServiceImpl | addChambre | Chambre Id : " + chambre.getId());
        return chambre.getId();
    }

    @Override
    public void addChambre(List<ChambreRequest> chambreRequests) {
        log.info("ChambreServiceImpl | addChambre is called");

        for (ChambreRequest chambreRequest : chambreRequests) {
            Chambre chambre;
            if(!chambreRepository.existsByNom(chambreRequest.getNom())){
                chambre = Chambre.builder()
                        .nom(chambreRequest.getNom())
                        .slug(Str.slug(chambreRequest.getNom()))
                        .created_at(LocalDateTime.now())
                        .build();
                chambreRepository.save(chambre);
            }
        }

        log.info("ChambreServiceImpl | addChambre | Chambre Created");
    }

    @Override
    public ChambreResponse getChambreById(long chambreId) {
        log.info("ChambreServiceImpl | getChambreById is called");
        log.info("ChambreServiceImpl | getChambreById | Get the chambre for chambreId: {}", chambreId);

        Chambre chambre
                = chambreRepository.findById(chambreId)
                .orElseThrow(
                        () -> new HospiCustomException("Chambre with given Id not found", NOT_FOUND));

        ChambreResponse chambreResponse = new ChambreResponse();

        copyProperties(chambre, chambreResponse);

        log.info("ChambreServiceImpl | getChambreById | chambreResponse :" + chambreResponse.toString());

        return chambreResponse;
    }

    @Override
    public void editChambre(ChambreRequest chambreRequest, long chambreId) {
        log.info("ChambreServiceImpl | editChambre is called");

        Chambre chambre
                = chambreRepository.findById(chambreId)
                .orElseThrow(() -> new HospiCustomException(
                        "Chambre with given Id not found",
                        NOT_FOUND
                ));
        chambre.setNom(chambreRequest.getNom());
        chambre.setSlug(Str.slug(chambreRequest.getNom()));
        chambre.setUpdated_at(LocalDateTime.now());
        chambreRepository.save(chambre);

        log.info("ChambreServiceImpl | editChambre | Chambre Updated");
        log.info("ChambreServiceImpl | editChambre | Chambre Id : " + chambre.getId());
    }

    @Override
    public void deleteChambreById(long chambreId) {
        log.info("Chambre id: {}", chambreId);

        if (!chambreRepository.existsById(chambreId)) {
            log.info("Im in this loop {}", !chambreRepository.existsById(chambreId));
            throw new HospiCustomException(
                    "Chambre with given with Id: " + chambreId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Chambre with id: {}", chambreId);
        chambreRepository.deleteById(chambreId);
    }

    @Override
    public List<Chambre> getChambreByNom(String nom) {
        log.info("ChambreServiceImpl | getChambreByNom is called");
        return chambreRepository.findByNomLike(nom);
    }

    @Override
    public List<Chambre> getAllChambresUntaken() {
        List<Chambre> chambres = chambreRepository.findAllUntaken();
        if(chambres != null && chambres.size()>0){
            chambres = chambres.stream().map(chambre -> {
                List<Lit> lits = litRepository.findAllUntakenByCHambreId(chambre.getId());
                chambre.setLits(lits);
                return chambre;
            }).collect(Collectors.toList());
        }
        return chambres;
    }
}
