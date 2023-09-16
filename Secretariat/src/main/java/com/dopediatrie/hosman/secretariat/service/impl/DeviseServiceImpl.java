package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Devise;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.DeviseRequest;
import com.dopediatrie.hosman.secretariat.payload.response.DeviseResponse;
import com.dopediatrie.hosman.secretariat.repository.DeviseRepository;
import com.dopediatrie.hosman.secretariat.service.DeviseService;
import com.dopediatrie.hosman.secretariat.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class DeviseServiceImpl implements DeviseService {
    private final DeviseRepository deviseRepository;
    private final String NOT_FOUND = "DEVISE_NOT_FOUND";

    @Override
    public List<Devise> getAllDevises() {
        return deviseRepository.findAll();
    }

    @Override
    public long addDevise(DeviseRequest deviseRequest) {
        log.info("DeviseServiceImpl | addDevise is called");

        Devise devise
                = Devise.builder()
                .nom(deviseRequest.getNom())
                .code(Str.slug(deviseRequest.getCode()))
                .symbol(deviseRequest.getSymbol())
                .taux(deviseRequest.getTaux())
                .build();

        devise = deviseRepository.save(devise);

        log.info("DeviseServiceImpl | addDevise | Devise Created");
        log.info("DeviseServiceImpl | addDevise | Devise Id : " + devise.getId());
        return devise.getId();
    }

    @Override
    public void addDevise(List<DeviseRequest> deviseRequests) {
        log.info("DeviseServiceImpl | addDevise is called");

        for (DeviseRequest deviseRequest: deviseRequests) {
            Devise devise
                    = Devise.builder()
                    .nom(deviseRequest.getNom())
                    .code(Str.slug(deviseRequest.getCode()))
                    .symbol(deviseRequest.getSymbol())
                    .taux(deviseRequest.getTaux())
                    .build();

            deviseRepository.save(devise);
        }

        log.info("DeviseServiceImpl | addDevise | Devise Created");
    }

    @Override
    public DeviseResponse getDeviseById(long deviseId) {
        log.info("DeviseServiceImpl | getDeviseById is called");
        log.info("DeviseServiceImpl | getDeviseById | Get the devise for deviseId: {}", deviseId);

        Devise devise
                = deviseRepository.findById(deviseId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Devise with given Id not found", NOT_FOUND));

        DeviseResponse deviseResponse = new DeviseResponse();

        copyProperties(devise, deviseResponse);

        log.info("DeviseServiceImpl | getDeviseById | deviseResponse :" + deviseResponse.toString());

        return deviseResponse;
    }

    @Override
    public void editDevise(DeviseRequest deviseRequest, long deviseId) {
        log.info("DeviseServiceImpl | editDevise is called");

        Devise devise
                = deviseRepository.findById(deviseId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Devise with given Id not found",
                        NOT_FOUND
                ));
        devise.setNom(deviseRequest.getNom());
        devise.setCode(Str.slug(deviseRequest.getCode()));
        devise.setSymbol(deviseRequest.getSymbol());
        devise.setTaux(deviseRequest.getTaux());
        deviseRepository.save(devise);

        log.info("DeviseServiceImpl | editDevise | Devise Updated");
        log.info("DeviseServiceImpl | editDevise | Devise Id : " + devise.getId());
    }

    @Override
    public void deleteDeviseById(long deviseId) {
        log.info("Devise id: {}", deviseId);

        if (!deviseRepository.existsById(deviseId)) {
            log.info("Im in this loop {}", !deviseRepository.existsById(deviseId));
            throw new SecretariatCustomException(
                    "Devise with given with Id: " + deviseId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Devise with id: {}", deviseId);
        deviseRepository.deleteById(deviseId);
    }
}
