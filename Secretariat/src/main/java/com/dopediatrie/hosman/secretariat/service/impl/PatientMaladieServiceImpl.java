package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.PatientMaladie;
import com.dopediatrie.hosman.secretariat.entity.PatientMaladiePK;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.NameRequest;
import com.dopediatrie.hosman.secretariat.payload.request.PatientMaladieRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PatientMaladieResponse;
import com.dopediatrie.hosman.secretariat.repository.PatientMaladieRepository;
import com.dopediatrie.hosman.secretariat.repository.PatientRepository;
import com.dopediatrie.hosman.secretariat.repository.MaladieRepository;
import com.dopediatrie.hosman.secretariat.service.MaladieService;
import com.dopediatrie.hosman.secretariat.service.PatientMaladieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class PatientMaladieServiceImpl implements PatientMaladieService {
    private final PatientMaladieRepository patientMaladieRepository;
    private final PatientRepository patientRepository;
    private final MaladieRepository maladieRepository;
    private final MaladieService maladieService;
    private final String NOT_FOUND = "PATIENT_MALADIE_NOT_FOUND";

    @Override
    public List<PatientMaladie> getAllPatientMaladies() {
        return patientMaladieRepository.findAll();
    }

    @Override
    public PatientMaladiePK addPatientMaladie(PatientMaladieRequest patientMaladieRequest) {
        log.info("PatientMaladieServiceImpl | addPatientMaladie is called");

        PatientMaladie patientMaladie;
        if(!patientMaladieRepository.existsByPatient_IdAndMaladie(patientMaladieRequest.getPatient_id(), patientMaladieRequest.getMaladie())){
            NameRequest nameRequest = NameRequest.builder().nom(patientMaladieRequest.getMaladie()).build();
            long maladie_id = maladieService.addMaladie(nameRequest);

            PatientMaladiePK pk = new PatientMaladiePK();
            pk.patient_id = patientMaladieRequest.getPatient_id();
            pk.maladie_id = maladie_id;

            patientMaladie
                    = PatientMaladie.builder()
                    .id(pk)
                    .patient(patientRepository.findById(patientMaladieRequest.getPatient_id()).orElseThrow())
                    .maladie(maladieRepository.findById(maladie_id).orElseThrow())
                    .build();

            patientMaladie = patientMaladieRepository.save(patientMaladie);
        }else {
            patientMaladie = patientMaladieRepository.findByPatient_IdAndMaladie(patientMaladieRequest.getPatient_id(), patientMaladieRequest.getMaladie()).orElseThrow();
        }


        log.info("PatientMaladieServiceImpl | addPatientMaladie | PatientMaladie Created");
        log.info("PatientMaladieServiceImpl | addPatientMaladie | PatientMaladie Id : " + patientMaladie.getId());
        return patientMaladie.getId();
    }

    @Override
    public void addPatientMaladie(List<PatientMaladieRequest> patientMaladieRequests) {
        log.info("PatientMaladieServiceImpl | addPatientMaladie is called");

        for (PatientMaladieRequest patientMaladieRequest : patientMaladieRequests) {
            NameRequest nameRequest = NameRequest.builder().nom(patientMaladieRequest.getMaladie()).build();
            long maladie_id = maladieService.addMaladie(nameRequest);

            PatientMaladiePK pk = new PatientMaladiePK();
            pk.patient_id = patientMaladieRequest.getPatient_id();
            pk.maladie_id = maladie_id;

            PatientMaladie patientMaladie
                    = PatientMaladie.builder()
                    .id(pk)
                    .patient(patientRepository.findById(patientMaladieRequest.getPatient_id()).orElseThrow())
                    .maladie(maladieRepository.findById(maladie_id).orElseThrow())
                    .build();

            patientMaladieRepository.save(patientMaladie);
        }
        log.info("PatientMaladieServiceImpl | addPatientMaladie | PatientMaladie Created");
    }

    @Override
    public PatientMaladieResponse getPatientMaladieById(long patientMaladieId) {
        log.info("PatientMaladieServiceImpl | getPatientMaladieById is called");
        log.info("PatientMaladieServiceImpl | getPatientMaladieById | Get the patientMaladie for patientMaladieId: {}", patientMaladieId);

        PatientMaladie patientMaladie
                = patientMaladieRepository.findById(patientMaladieId)
                .orElseThrow(
                        () -> new SecretariatCustomException("PatientMaladie with given Id not found", NOT_FOUND));

        PatientMaladieResponse patientMaladieResponse = new PatientMaladieResponse();

        copyProperties(patientMaladie, patientMaladieResponse);

        log.info("PatientMaladieServiceImpl | getPatientMaladieById | patientMaladieResponse :" + patientMaladieResponse.toString());

        return patientMaladieResponse;
    }

    @Override
    public void editPatientMaladie(PatientMaladieRequest patientMaladieRequest, long patientMaladieId) {
        log.info("PatientMaladieServiceImpl | editPatientMaladie is called");

        PatientMaladie patientMaladie
                = patientMaladieRepository.findById(patientMaladieId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "PatientMaladie with given Id not found",
                        NOT_FOUND
                ));

        NameRequest nameRequest = NameRequest.builder().nom(patientMaladieRequest.getMaladie()).build();
        long maladie_id = maladieService.addMaladie(nameRequest);

        patientMaladie.setPatient(patientRepository.findById(patientMaladieRequest.getPatient_id()).orElseThrow());
        patientMaladie.setMaladie(maladieRepository.findById(maladie_id).orElseThrow());
        patientMaladieRepository.save(patientMaladie);

        log.info("PatientMaladieServiceImpl | editPatientMaladie | PatientMaladie Updated");
        log.info("PatientMaladieServiceImpl | editPatientMaladie | PatientMaladie Id : " + patientMaladie.getId());
    }

    @Override
    public void deletePatientMaladieById(long patientMaladieId) {
        log.info("PatientMaladie id: {}", patientMaladieId);

        if (!patientMaladieRepository.existsById(patientMaladieId)) {
            log.info("Im in this loop {}", !patientMaladieRepository.existsById(patientMaladieId));
            throw new SecretariatCustomException(
                    "PatientMaladie with given with Id: " + patientMaladieId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting PatientMaladie with id: {}", patientMaladieId);
        patientMaladieRepository.deleteById(patientMaladieId);
    }

    @Override
    public void deleteAllForPatientId(long patientId) {
        log.info("PatientMaladie delete all");
        patientMaladieRepository.deleteByPatientId(patientId);
    }
}
