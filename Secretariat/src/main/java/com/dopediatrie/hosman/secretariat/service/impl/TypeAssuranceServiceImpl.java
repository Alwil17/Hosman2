package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.TypeAssurance;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.NameRequest;
import com.dopediatrie.hosman.secretariat.payload.response.NameResponse;
import com.dopediatrie.hosman.secretariat.repository.TypeAssuranceRepository;
import com.dopediatrie.hosman.secretariat.service.TypeAssuranceService;
import com.dopediatrie.hosman.secretariat.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class TypeAssuranceServiceImpl implements TypeAssuranceService {
    private final TypeAssuranceRepository typeAssuranceRepository;
    private final String NOT_FOUND = "TYPE_ASSURANCE_NOT_FOUND";

    @Override
    public List<TypeAssurance> getAllTypeAssurances() {
        return typeAssuranceRepository.findAll();
    }

    @Override
    public long addTypeAssurance(NameRequest typeAssuranceRequest) {
        log.info("TypeAssuranceServiceImpl | addTypeAssurance is called");

        TypeAssurance typeAssurance;
        if(typeAssuranceRepository.existsByNom(typeAssuranceRequest.getNom())){
            typeAssurance = typeAssuranceRepository.findByNom(typeAssuranceRequest.getNom()).orElseThrow();
        }else {
            typeAssurance
                    = TypeAssurance.builder()
                    .nom(typeAssuranceRequest.getNom())
                    .slug(Str.slug(typeAssuranceRequest.getNom()))
                    .build();
            typeAssurance = typeAssuranceRepository.save(typeAssurance);
        }

        log.info("TypeAssuranceServiceImpl | addTypeAssurance | TypeAssurance Created");
        log.info("TypeAssuranceServiceImpl | addTypeAssurance | TypeAssurance Id : " + typeAssurance.getId());
        return typeAssurance.getId();
    }

    @Override
    public void addTypeAssurance(List<NameRequest> typeAssuranceRequests) {
        log.info("TypeAssuranceServiceImpl | addTypeAssurance is called");

        for (NameRequest typeAssuranceRequest : typeAssuranceRequests) {
            TypeAssurance typeAssurance
                    = TypeAssurance.builder()
                    .nom(typeAssuranceRequest.getNom())
                    .slug(Str.slug(typeAssuranceRequest.getNom()))
                    .build();

            typeAssuranceRepository.save(typeAssurance);
        }

        log.info("TypeAssuranceServiceImpl | addTypeAssurance | TypeAssurance Created");
    }

    @Override
    public NameResponse getTypeAssuranceById(long typeAssuranceId) {
        log.info("TypeAssuranceServiceImpl | getTypeAssuranceById is called");
        log.info("TypeAssuranceServiceImpl | getTypeAssuranceById | Get the typeAssurance for typeAssuranceId: {}", typeAssuranceId);

        TypeAssurance typeAssurance
                = typeAssuranceRepository.findById(typeAssuranceId)
                .orElseThrow(
                        () -> new SecretariatCustomException("TypeAssurance with given Id not found", NOT_FOUND));

        NameResponse typeAssuranceResponse = new NameResponse();

        copyProperties(typeAssurance, typeAssuranceResponse);

        log.info("TypeAssuranceServiceImpl | getTypeAssuranceById | typeAssuranceResponse :" + typeAssuranceResponse.toString());

        return typeAssuranceResponse;
    }

    @Override
    public void editTypeAssurance(NameRequest typeAssuranceRequest, long typeAssuranceId) {
        log.info("TypeAssuranceServiceImpl | editTypeAssurance is called");

        TypeAssurance typeAssurance
                = typeAssuranceRepository.findById(typeAssuranceId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "TypeAssurance with given Id not found",
                        NOT_FOUND
                ));
        typeAssurance.setNom(typeAssuranceRequest.getNom());
        typeAssurance.setSlug(Str.slug(typeAssuranceRequest.getNom()));
        typeAssuranceRepository.save(typeAssurance);

        log.info("TypeAssuranceServiceImpl | editTypeAssurance | TypeAssurance Updated");
        log.info("TypeAssuranceServiceImpl | editTypeAssurance | TypeAssurance Id : " + typeAssurance.getId());
    }

    @Override
    public void deleteTypeAssuranceById(long typeAssuranceId) {
        log.info("TypeAssurance id: {}", typeAssuranceId);

        if (!typeAssuranceRepository.existsById(typeAssuranceId)) {
            log.info("Im in this loop {}", !typeAssuranceRepository.existsById(typeAssuranceId));
            throw new SecretariatCustomException(
                    "TypeAssurance with given with Id: " + typeAssuranceId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting TypeAssurance with id: {}", typeAssuranceId);
        typeAssuranceRepository.deleteById(typeAssuranceId);
    }
}
