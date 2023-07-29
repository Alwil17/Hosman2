package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Quartier;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.NameRequest;
import com.dopediatrie.hosman.secretariat.payload.response.NameResponse;
import com.dopediatrie.hosman.secretariat.repository.QuartierRepository;
import com.dopediatrie.hosman.secretariat.service.QuartierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class QuartierServiceImpl implements QuartierService {
    private final QuartierRepository quartierRepository;
    private final String NOT_FOUND = "QUARTIER_NOT_FOUND";

    @Override
    public List<Quartier> getAllQuartiers() {
        return quartierRepository.findAll();
    }

    @Override
    public long addQuartier(NameRequest quartierRequest) {
        log.info("QuartierServiceImpl | addQuartier is called");

        Quartier quartier
                = Quartier.builder()
                .nom(quartierRequest.getNom())
                .build();

        quartier = quartierRepository.save(quartier);

        log.info("QuartierServiceImpl | addQuartier | Quartier Created");
        log.info("QuartierServiceImpl | addQuartier | Quartier Id : " + quartier.getId());
        return quartier.getId();
    }

    @Override
    public NameResponse getQuartierById(long quartierId) {
        log.info("QuartierServiceImpl | getQuartierById is called");
        log.info("QuartierServiceImpl | getQuartierById | Get the quartier for quartierId: {}", quartierId);

        Quartier quartier
                = quartierRepository.findById(quartierId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Quartier with given Id not found", NOT_FOUND));

        NameResponse quartierResponse = new NameResponse();

        copyProperties(quartier, quartierResponse);

        log.info("QuartierServiceImpl | getQuartierById | quartierResponse :" + quartierResponse.toString());

        return quartierResponse;
    }

    @Override
    public void editQuartier(NameRequest quartierRequest, long quartierId) {
        log.info("QuartierServiceImpl | editQuartier is called");

        Quartier quartier
                = quartierRepository.findById(quartierId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Quartier with given Id not found",
                        NOT_FOUND
                ));
        quartier.setNom(quartierRequest.getNom());
        quartierRepository.save(quartier);

        log.info("QuartierServiceImpl | editQuartier | Quartier Updated");
        log.info("QuartierServiceImpl | editQuartier | Quartier Id : " + quartier.getId());
    }

    @Override
    public void deleteQuartierById(long quartierId) {
        log.info("Quartier id: {}", quartierId);

        if (!quartierRepository.existsById(quartierId)) {
            log.info("Im in this loop {}", !quartierRepository.existsById(quartierId));
            throw new SecretariatCustomException(
                    "Quartier with given with Id: " + quartierId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Quartier with id: {}", quartierId);
        quartierRepository.deleteById(quartierId);
    }
}
