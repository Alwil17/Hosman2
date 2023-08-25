package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Assurance;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.AssuranceRequest;
import com.dopediatrie.hosman.secretariat.payload.response.AssuranceResponse;
import com.dopediatrie.hosman.secretariat.repository.AssuranceRepository;
import com.dopediatrie.hosman.secretariat.repository.TypeAssuranceRepository;
import com.dopediatrie.hosman.secretariat.service.AssuranceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class AssuranceServiceImpl implements AssuranceService {
    private final AssuranceRepository assuranceRepository;
    private final TypeAssuranceRepository typeAssuranceRepository;
    private final String NOT_FOUND = "ASSURANCE_NOT_FOUND";

    @Override
    public List<Assurance> getAllAssurances() {
        return assuranceRepository.findAll();
    }

    @Override
    public long addAssurance(AssuranceRequest assuranceRequest) {
        log.info("AssuranceServiceImpl | addAssurance is called");
        Assurance assurance;

        if(!assuranceRepository.existsByNom(assuranceRequest.getNom())){
            assurance
                    = Assurance.builder()
                    .nom(assuranceRequest.getNom())
                    .representant(assuranceRequest.getRepresentant())
                    .email(assuranceRequest.getEmail())
                    .tel1(assuranceRequest.getTel1())
                    .tel2(assuranceRequest.getTel2())
                    .type_assurance(typeAssuranceRepository.findById(assuranceRequest.getType_assurance_id()).get())
                    .build();

            assurance = assuranceRepository.save(assurance);
        }else{
            assurance = assuranceRepository.findByNom(assuranceRequest.getNom()).get();
        }


        log.info("AssuranceServiceImpl | addAssurance | Assurance Created");
        log.info("AssuranceServiceImpl | addAssurance | Assurance Id : " + assurance.getId());
        return assurance.getId();
    }

    @Override
    public AssuranceResponse getAssuranceById(long assuranceId) {
        log.info("AssuranceServiceImpl | getAssuranceById is called");
        log.info("AssuranceServiceImpl | getAssuranceById | Get the assurance for assuranceId: {}", assuranceId);

        Assurance assurance
                = assuranceRepository.findById(assuranceId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Assurance with given Id not found", NOT_FOUND));

        AssuranceResponse assuranceResponse = new AssuranceResponse();

        copyProperties(assurance, assuranceResponse);

        log.info("AssuranceServiceImpl | getAssuranceById | assuranceResponse :" + assuranceResponse.toString());

        return assuranceResponse;
    }

    @Override
    public void editAssurance(AssuranceRequest assuranceRequest, long assuranceId) {
        log.info("AssuranceServiceImpl | editAssurance is called");

        Assurance assurance
                = assuranceRepository.findById(assuranceId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Assurance with given Id not found",
                        NOT_FOUND
                ));
        assurance.setNom(assuranceRequest.getNom());
        assurance.setRepresentant(assuranceRequest.getRepresentant());
        assurance.setEmail(assuranceRequest.getEmail());
        assurance.setTel1(assuranceRequest.getTel1());
        assurance.setTel2(assuranceRequest.getTel2());
        assurance.setType_assurance(typeAssuranceRepository.findById(assuranceRequest.getType_assurance_id()).get());
        assuranceRepository.save(assurance);

        log.info("AssuranceServiceImpl | editAssurance | Assurance Updated");
        log.info("AssuranceServiceImpl | editAssurance | Assurance Id : " + assurance.getId());
    }

    @Override
    public void deleteAssuranceById(long assuranceId) {
        log.info("Assurance id: {}", assuranceId);

        if (!assuranceRepository.existsById(assuranceId)) {
            log.info("Im in this loop {}", !assuranceRepository.existsById(assuranceId));
            throw new SecretariatCustomException(
                    "Assurance with given with Id: " + assuranceId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Assurance with id: {}", assuranceId);
        assuranceRepository.deleteById(assuranceId);
    }
}
