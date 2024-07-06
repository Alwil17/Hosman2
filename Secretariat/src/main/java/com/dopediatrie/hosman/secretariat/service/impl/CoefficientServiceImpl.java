package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Coefficient;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.CoefficientRequest;
import com.dopediatrie.hosman.secretariat.payload.response.CoefficientResponse;
import com.dopediatrie.hosman.secretariat.repository.CoefficientRepository;
import com.dopediatrie.hosman.secretariat.service.CoefficientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class CoefficientServiceImpl implements CoefficientService {
    private final CoefficientRepository coefficientRepository;

    private final String NOT_FOUND = "COEFFICIENT_NOT_FOUND";

    @Override
    public List<Coefficient> getAllCoefficients() { return coefficientRepository.findAll(); }

    @Override
    public long addCoefficient(CoefficientRequest coefficientRequest) {
        log.info("CoefficientServiceImpl | addCoefficient is called");

        Coefficient coefficient;
        if(coefficientRepository.existsByPatientId(coefficientRequest.getPatient_id())){
            coefficient = coefficientRepository.findByPatientId(coefficientRequest.getPatient_id()).orElseThrow();
            editCoefficient(coefficientRequest, coefficient.getId());
        }else {
            coefficient = Coefficient.builder()
                    .nbe(coefficientRequest.getNbe())
                    .nim(coefficientRequest.getNim())
                    .nip(coefficientRequest.getNip())
                    .smf(coefficientRequest.getSmf())
                    .mni(coefficientRequest.getMni())
                    .mf(coefficientRequest.getMf())
                    .pf(coefficientRequest.getPf())
                    .ass(coefficientRequest.getAss())
                    .imv(coefficientRequest.getImv())
                    .coef(coefficientRequest.getCoef())
                    .commentaire(coefficientRequest.getCommentaire())
                    .build();

            coefficient = coefficientRepository.save(coefficient);

        }
        log.info("CoefficientServiceImpl | addCoefficient | Coefficient Created");
        log.info("CoefficientServiceImpl | addCoefficient | Coefficient Id : " + coefficient.getId());
        return coefficient.getId();
    }

    @Override
    public CoefficientResponse getCoefficientById(long coefficientId) {
        log.info("CoefficientServiceImpl | getCoefficientById is called");
        log.info("CoefficientServiceImpl | getCoefficientById | Get the coefficient for coefficientId: {}", coefficientId);

        Coefficient coefficient
                = coefficientRepository.findById(coefficientId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Coefficient with given Id not found", NOT_FOUND));

        CoefficientResponse coefficientResponse = new CoefficientResponse();
        copyProperties(coefficient, coefficientResponse);

        log.info("CoefficientServiceImpl | getCoefficientById | coefficientResponse :" + coefficientResponse.toString());

        return coefficientResponse;
    }

    @Override
    public void editCoefficient(CoefficientRequest coefficientRequest, long coefficientId) {
        log.info("CoefficientServiceImpl | editCoefficient is called");
        Coefficient coefficient
                = coefficientRepository.findById(coefficientId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Coefficient with given Id not found",
                        NOT_FOUND
                ));
        coefficient.setNbe(coefficientRequest.getNbe());
        coefficient.setNim(coefficientRequest.getNim());
        coefficient.setNip(coefficientRequest.getNip());
        coefficient.setSmf(coefficientRequest.getSmf());
        coefficient.setMni(coefficientRequest.getMni());
        coefficient.setMf(coefficientRequest.getMf());
        coefficient.setPf(coefficientRequest.getPf());
        coefficient.setAss(coefficientRequest.getAss());
        coefficient.setImv(coefficientRequest.getImv());
        coefficient.setCoef(coefficientRequest.getCoef());
        coefficient.setCommentaire(coefficientRequest.getCommentaire());

        coefficient = coefficientRepository.save(coefficient);

        log.info("CoefficientServiceImpl | editCoefficient | Coefficient Updated");
        log.info("CoefficientServiceImpl | editCoefficient | Coefficient Id : " + coefficient.getId());
    }

    @Override
    public void deleteCoefficientById(long coefficientId) {
        log.info("Coefficient id: {}", coefficientId);

        if (!coefficientRepository.existsById(coefficientId)) {
            log.info("Im in this loop {}", !coefficientRepository.existsById(coefficientId));
            throw new SecretariatCustomException(
                    "Coefficient with given with Id: " + coefficientId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Coefficient with id: {}", coefficientId);
        coefficientRepository.deleteById(coefficientId);
    }
}
