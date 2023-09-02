package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Ville;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.NameRequest;
import com.dopediatrie.hosman.secretariat.payload.response.NameResponse;
import com.dopediatrie.hosman.secretariat.repository.VilleRepository;
import com.dopediatrie.hosman.secretariat.service.VilleService;
import com.dopediatrie.hosman.secretariat.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class VilleServiceImpl implements VilleService {
    private final VilleRepository villeRepository;
    private final String NOT_FOUND = "VILLE_NOT_FOUND";

    @Override
    public List<Ville> getAllVilles() {
        return villeRepository.findAll();
    }

    @Override
    public long addVille(NameRequest villeRequest) {
        log.info("VilleServiceImpl | addVille is called");

        Ville ville
                = Ville.builder()
                .nom(villeRequest.getNom())
                .slug(Str.slug(villeRequest.getNom()))
                .build();

        ville = villeRepository.save(ville);

        log.info("VilleServiceImpl | addVille | Ville Created");
        log.info("VilleServiceImpl | addVille | Ville Id : " + ville.getId());
        return ville.getId();
    }

    @Override
    public NameResponse getVilleById(long villeId) {
        log.info("VilleServiceImpl | getVilleById is called");
        log.info("VilleServiceImpl | getVilleById | Get the ville for villeId: {}", villeId);

        Ville ville
                = villeRepository.findById(villeId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Ville with given Id not found", NOT_FOUND));

        NameResponse villeResponse = new NameResponse();

        copyProperties(ville, villeResponse);

        log.info("VilleServiceImpl | getVilleById | villeResponse :" + villeResponse.toString());

        return villeResponse;
    }

    @Override
    public void editVille(NameRequest villeRequest, long villeId) {
        log.info("VilleServiceImpl | editVille is called");

        Ville ville
                = villeRepository.findById(villeId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Ville with given Id not found",
                        NOT_FOUND
                ));
        ville.setNom(villeRequest.getNom());
        ville.setSlug(Str.slug(villeRequest.getNom()));
        villeRepository.save(ville);

        log.info("VilleServiceImpl | editVille | Ville Updated");
        log.info("VilleServiceImpl | editVille | Ville Id : " + ville.getId());
    }

    @Override
    public void deleteVilleById(long villeId) {
        log.info("Ville id: {}", villeId);

        if (!villeRepository.existsById(villeId)) {
            log.info("Im in this loop {}", !villeRepository.existsById(villeId));
            throw new SecretariatCustomException(
                    "Ville with given with Id: " + villeId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Ville with id: {}", villeId);
        villeRepository.deleteById(villeId);
    }
}
