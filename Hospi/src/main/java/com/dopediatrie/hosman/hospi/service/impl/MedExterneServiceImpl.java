package com.dopediatrie.hosman.hospi.service.impl;

import com.dopediatrie.hosman.hospi.entity.MedExterne;
import com.dopediatrie.hosman.hospi.exception.HospiCustomException;
import com.dopediatrie.hosman.hospi.payload.request.MedExterneRequest;
import com.dopediatrie.hosman.hospi.payload.response.MedExterneResponse;
import com.dopediatrie.hosman.hospi.payload.response.MedecinResponse;
import com.dopediatrie.hosman.hospi.repository.HospitRepository;
import com.dopediatrie.hosman.hospi.repository.MedExterneRepository;
import com.dopediatrie.hosman.hospi.service.MedExterneService;
import com.dopediatrie.hosman.hospi.service.MedecinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class MedExterneServiceImpl implements MedExterneService {
    private final String NOT_FOUND = "MEDEXTERNE_NOT_FOUND";

    private final MedExterneRepository medExterneRepository;
    private final HospitRepository hospitRepository;
    private final MedecinService medecinService;

    @Override
    public List<MedExterne> getAllMedExternes() {
        return medExterneRepository.findAll();
    }

    @Override
    public long addMedExterne(MedExterneRequest medExterneRequest) {
        log.info("MedExterneServiceImpl | addMedExterne is called");
        MedExterne medExterne;
        if(medExterneRepository.existsByMed_refAndType_opAndHospitId(medExterneRequest.getMed_ref(), medExterneRequest.getType_op(), medExterneRequest.getHospit_id())){
            medExterne = medExterneRepository.findByMed_refAndType_opAndHospitId(medExterneRequest.getMed_ref(), medExterneRequest.getType_op(), medExterneRequest.getHospit_id()).orElseThrow();
            editMedExterne(medExterneRequest, medExterne.getId());
        }else {
            medExterne = MedExterne.builder()
                    .med_ref(medExterneRequest.getMed_ref())
                    .type_op(medExterneRequest.getType_op())
                    .comments(medExterneRequest.getComments())
                    .date_op(medExterneRequest.getDate_op())
                    .hospit(hospitRepository.findById(medExterneRequest.getHospit_id()).orElseThrow())
                    .build();
            medExterne = medExterneRepository.save(medExterne);
        }

        log.info("MedExterneServiceImpl | addMedExterne | MedExterne Created/Updated");
        log.info("MedExterneServiceImpl | addMedExterne | MedExterne Id : " + medExterne.getId());
        return medExterne.getId();
    }

    @Override
    public void addMedExterne(List<MedExterneRequest> medExterneRequests) {
        log.info("MedExterneServiceImpl | addMedExterne is called");

        for (MedExterneRequest medExterneRequest : medExterneRequests) {
            MedExterne medExterne;
            if(medExterneRepository.existsByMed_refAndType_opAndHospitId(medExterneRequest.getMed_ref(), medExterneRequest.getType_op(), medExterneRequest.getHospit_id())){
                medExterne = medExterneRepository.findByMed_refAndType_opAndHospitId(medExterneRequest.getMed_ref(), medExterneRequest.getType_op(), medExterneRequest.getHospit_id()).orElseThrow();
                editMedExterne(medExterneRequest, medExterne.getId());
            }else {
                medExterne = MedExterne.builder()
                        .med_ref(medExterneRequest.getMed_ref())
                        .type_op(medExterneRequest.getType_op())
                        .comments(medExterneRequest.getComments())
                        .date_op(medExterneRequest.getDate_op())
                        .hospit(hospitRepository.findById(medExterneRequest.getHospit_id()).orElseThrow())
                        .build();
                medExterneRepository.save(medExterne);
            }
        }

        log.info("MedExterneServiceImpl | addMedExterne | MedExterne Created");
    }

    @Override
    public MedExterneResponse getMedExterneById(long medExterneId) {
        log.info("MedExterneServiceImpl | getMedExterneById is called");
        log.info("MedExterneServiceImpl | getMedExterneById | Get the medExterne for medExterneId: {}", medExterneId);

        MedExterne medExterne
                = medExterneRepository.findById(medExterneId)
                .orElseThrow(
                        () -> new HospiCustomException("MedExterne with given Id not found", NOT_FOUND));

        MedExterneResponse medExterneResponse = new MedExterneResponse();
        copyProperties(medExterne, medExterneResponse);
        MedecinResponse mr = medecinService.getMedecinByMatricule(medExterneResponse.getMed_ref());
        medExterneResponse.setMedecin(mr);

        log.info("MedExterneServiceImpl | getMedExterneById | medExterneResponse :" + medExterneResponse.toString());

        return medExterneResponse;
    }

    @Override
    public List<MedExterneResponse> getMedExterneByHospitId(long hospitId) {
        log.info("MedExterneServiceImpl | getMedExterneByHospitId is called");
        List<MedExterne> medExternes = medExterneRepository.findAllByHospitId(hospitId);
        List<MedExterneResponse> medExterneResponses = new ArrayList<>();
        for (MedExterne s : medExternes) {
            MedExterneResponse sr = new MedExterneResponse();
            copyProperties(s, sr);
            MedecinResponse mr = medecinService.getMedecinByMatricule(sr.getMed_ref());
            sr.setMedecin(mr);
            medExterneResponses.add(sr);
        }
        return medExterneResponses;
    }

    @Override
    public void editMedExterne(MedExterneRequest medExterneRequest, long medExterneId) {
        log.info("MedExterneServiceImpl | editMedExterne is called");

        MedExterne medExterne
                = medExterneRepository.findById(medExterneId)
                .orElseThrow(() -> new HospiCustomException(
                        "MedExterne with given Id not found",
                        NOT_FOUND
                ));
        medExterne.setMed_ref(medExterneRequest.getMed_ref());
        medExterne.setType_op(medExterneRequest.getType_op());
        medExterne.setComments(medExterneRequest.getComments());
        medExterne.setDate_op(medExterneRequest.getDate_op());
        medExterne.setHospit(hospitRepository.findById(medExterneRequest.getHospit_id()).orElseThrow());
        medExterneRepository.save(medExterne);

        log.info("MedExterneServiceImpl | editMedExterne | MedExterne Updated");
        log.info("MedExterneServiceImpl | editMedExterne | MedExterne Id : " + medExterne.getId());
    }

    @Override
    public void deleteMedExterneById(long medExterneId) {
        log.info("MedExterne id: {}", medExterneId);

        if (!medExterneRepository.existsById(medExterneId)) {
            log.info("Im in this loop {}", !medExterneRepository.existsById(medExterneId));
            throw new HospiCustomException(
                    "MedExterne with given with Id: " + medExterneId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting MedExterne with id: {}", medExterneId);
        medExterneRepository.deleteById(medExterneId);
    }
}
