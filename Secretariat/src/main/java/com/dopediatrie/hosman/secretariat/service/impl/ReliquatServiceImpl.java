package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Reliquat;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.ReliquatRequest;
import com.dopediatrie.hosman.secretariat.payload.response.ReliquatResponse;
import com.dopediatrie.hosman.secretariat.repository.ReliquatRepository;
import com.dopediatrie.hosman.secretariat.repository.EtatRepository;
import com.dopediatrie.hosman.secretariat.repository.FactureRepository;
import com.dopediatrie.hosman.secretariat.service.ReliquatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReliquatServiceImpl implements ReliquatService {
    private final ReliquatRepository reliquatRepository;
    private final EtatRepository etatRepository;
    private final FactureRepository factureRepository;
    private final String NOT_FOUND = "RELIQUAT_NOT_FOUND";

    @Override
    public List<Reliquat> getAllReliquats() {
        return reliquatRepository.findAll();
    }

    @Override
    public long addReliquat(ReliquatRequest reliquatRequest) {
        log.info("ReliquatServiceImpl | addReliquat is called");

        Reliquat reliquat = Reliquat.builder()
                .montant(reliquatRequest.getMontant())
                .etat(etatRepository.findById(reliquatRequest.getEtat_id()).orElseThrow())
                .build();

        log.info("ReliquatServiceImpl | addReliquat | Reliquat Created");
        log.info("ReliquatServiceImpl | addReliquat | Reliquat Id : " + reliquat.getId());
        return reliquat.getId();
    }

    @Override
    public ReliquatResponse getReliquatById(long reliquatId) {
        log.info("ReliquatServiceImpl | getReliquatById is called");
        log.info("ReliquatServiceImpl | getReliquatById | Get the reliquat for reliquatId: {}", reliquatId);

        Reliquat reliquat
                = reliquatRepository.findById(reliquatId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Reliquat with given Id not found", NOT_FOUND));

        ReliquatResponse reliquatResponse = new ReliquatResponse();

        copyProperties(reliquat, reliquatResponse);

        log.info("ReliquatServiceImpl | getReliquatById | reliquatResponse :" + reliquatResponse.toString());

        return reliquatResponse;
    }

    @Override
    public void editReliquat(ReliquatRequest reliquatRequest, long reliquatId) {
        log.info("ReliquatServiceImpl | editReliquat is called");

        Reliquat reliquat
                = reliquatRepository.findById(reliquatId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Reliquat with given Id not found",
                        NOT_FOUND
                ));
        reliquat.setMontant(reliquatRequest.getMontant());
        reliquat.setEtat(etatRepository.findById(reliquatRequest.getEtat_id()).get());
        reliquatRepository.save(reliquat);

        log.info("ReliquatServiceImpl | editReliquat | Reliquat Updated");
        log.info("ReliquatServiceImpl | editReliquat | Reliquat Id : " + reliquat.getId());
    }

    @Override
    public void deleteReliquatById(long reliquatId) {
        log.info("Reliquat id: {}", reliquatId);

        if (!reliquatRepository.existsById(reliquatId)) {
            log.info("Im in this loop {}", !reliquatRepository.existsById(reliquatId));
            throw new SecretariatCustomException(
                    "Reliquat with given with Id: " + reliquatId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Reliquat with id: {}", reliquatId);
        reliquatRepository.deleteById(reliquatId);
    }
}
