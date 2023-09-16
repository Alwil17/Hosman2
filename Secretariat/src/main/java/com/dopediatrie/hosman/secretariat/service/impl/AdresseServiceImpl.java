package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Adresse;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.AdresseRequest;
import com.dopediatrie.hosman.secretariat.payload.response.AdresseResponse;
import com.dopediatrie.hosman.secretariat.repository.AdresseRepository;
import com.dopediatrie.hosman.secretariat.repository.QuartierRepository;
import com.dopediatrie.hosman.secretariat.repository.VilleRepository;
import com.dopediatrie.hosman.secretariat.service.AdresseService;
import com.dopediatrie.hosman.secretariat.service.QuartierService;
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
public class AdresseServiceImpl implements AdresseService {
    private final AdresseRepository adresseRepository;
    private final VilleRepository villeRepository;
    private final QuartierRepository quartierRepository;

    private final VilleService villeService;
    private final QuartierService quartierService;

    private final String NOT_FOUND = "ADRESSE_NOT_FOUND";

    @Override
    public List<Adresse> getAllAdresses() {
        return adresseRepository.findAll();
    }

    @Override
    public long addAdresse(AdresseRequest adresseRequest) {
        log.info("AdresseServiceImpl | addAdresse is called");

        long ville_id = (adresseRequest.getVille() != null) ? villeService.addVille(adresseRequest.getVille()) : 0;
        long quartier_id = (adresseRequest.getQuartier() != null) ? quartierService.addQuartier(adresseRequest.getQuartier()) : 0;

        Adresse adresse
                = Adresse.builder()
                .bp(adresseRequest.getBp())
                .arrondissement(adresseRequest.getArrondissement())
                .no_maison(adresseRequest.getNo_maison())
                .rue(adresseRequest.getRue())
                .build();

        if(ville_id != 0)
            adresse.setVille(villeRepository.findById(ville_id).orElseThrow());
        if(quartier_id != 0)
            adresse.setQuartier(quartierRepository.findById(quartier_id).orElseThrow());

        adresse = adresseRepository.save(adresse);

        log.info("AdresseServiceImpl | addAdresse | Adresse Created");
        log.info("AdresseServiceImpl | addAdresse | Adresse Id : " + adresse.getId());
        return adresse.getId();
    }

    @Override
    public AdresseResponse getAdresseById(long adresseId) {
        log.info("AdresseServiceImpl | getAdresseById is called");
        log.info("AdresseServiceImpl | getAdresseById | Get the adresse for adresseId: {}", adresseId);

        Adresse adresse
                = adresseRepository.findById(adresseId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Adresse with given Id not found", NOT_FOUND));

        AdresseResponse adresseResponse = new AdresseResponse();

        copyProperties(adresse, adresseResponse);

        log.info("AdresseServiceImpl | getAdresseById | adresseResponse :" + adresseResponse.toString());

        return adresseResponse;
    }

    @Override
    public void editAdresse(AdresseRequest adresseRequest, long adresseId) {
        log.info("AdresseServiceImpl | editAdresse is called");

        long ville_id = (adresseRequest.getVille() != null) ? villeService.addVille(adresseRequest.getVille()) : 0;
        long quartier_id = (adresseRequest.getQuartier() != null) ? quartierService.addQuartier(adresseRequest.getQuartier()) : 0;

        Adresse adresse
                = adresseRepository.findById(adresseId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Adresse with given Id not found",
                        NOT_FOUND
                ));
        adresse.setBp(adresseRequest.getBp());
        adresse.setArrondissement(adresseRequest.getArrondissement());
        adresse.setNo_maison(adresseRequest.getNo_maison());
        adresse.setRue(adresseRequest.getRue());
        adresse.setVille(villeRepository.findById(ville_id).orElseThrow());
        adresse.setQuartier(quartierRepository.findById(quartier_id).orElseThrow());
        adresseRepository.save(adresse);

        log.info("AdresseServiceImpl | editAdresse | Adresse Updated");
        log.info("AdresseServiceImpl | editAdresse | Adresse Id : " + adresse.getId());
    }

    @Override
    public void deleteAdresseById(long adresseId) {
        log.info("Adresse id: {}", adresseId);

        if (!adresseRepository.existsById(adresseId)) {
            log.info("Im in this loop {}", !adresseRepository.existsById(adresseId));
            throw new SecretariatCustomException(
                    "Adresse with given with Id: " + adresseId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Adresse with id: {}", adresseId);
        adresseRepository.deleteById(adresseId);
    }
}
