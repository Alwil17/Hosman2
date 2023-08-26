package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.PatientAssurance;
import com.dopediatrie.hosman.secretariat.entity.PatientAssurancePK;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.PatientAssuranceRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PatientAssuranceResponse;
import com.dopediatrie.hosman.secretariat.repository.AssuranceRepository;
import com.dopediatrie.hosman.secretariat.repository.PatientAssuranceRepository;
import com.dopediatrie.hosman.secretariat.repository.PatientRepository;
import com.dopediatrie.hosman.secretariat.service.PatientAssuranceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class PatientAssuranceServiceImpl implements PatientAssuranceService {
    private final PatientAssuranceRepository patientAssuranceRepository;
    private final PatientRepository patientRepository;
    private final AssuranceRepository assuranceRepository;
    private final String NOT_FOUND = "PATIENT_ASSURANCE_NOT_FOUND";

    @Override
    public List<PatientAssurance> getAllPatientAssurances() {
        return patientAssuranceRepository.findAll();
    }

    @Override
    public PatientAssurancePK addPatientAssurance(PatientAssuranceRequest patientAssuranceRequest) {
        log.info("PatientAssuranceServiceImpl | addPatientAssurance is called");

        PatientAssurancePK pk = new PatientAssurancePK();
        pk.patient_id = patientAssuranceRequest.getPatient_id();
        pk.assurance_id = patientAssuranceRequest.getAssurance_id();

        PatientAssurance patientAssurance
                = PatientAssurance.builder()
                .id(pk)
                .patient(patientRepository.findById(patientAssuranceRequest.getPatient_id()).orElseThrow())
                .assurance(assuranceRepository.findById(patientAssuranceRequest.getAssurance_id()).orElseThrow())
                .date_debut(patientAssuranceRequest.getDate_debut())
                .date_fin(patientAssuranceRequest.getDate_fin())
                .taux(patientAssuranceRequest.getTaux())
                .build();

        patientAssurance = patientAssuranceRepository.save(patientAssurance);

        log.info("PatientAssuranceServiceImpl | addPatientAssurance | PatientAssurance Created");
        log.info("PatientAssuranceServiceImpl | addPatientAssurance | PatientAssurance Id : " + patientAssurance.getId());
        return patientAssurance.getId();
    }

    @Override
    public PatientAssuranceResponse getPatientAssuranceById(long patientAssuranceId) {
        log.info("PatientAssuranceServiceImpl | getPatientAssuranceById is called");
        log.info("PatientAssuranceServiceImpl | getPatientAssuranceById | Get the patientAssurance for patientAssuranceId: {}", patientAssuranceId);

        PatientAssurance patientAssurance
                = patientAssuranceRepository.findById(patientAssuranceId)
                .orElseThrow(
                        () -> new SecretariatCustomException("PatientAssurance with given Id not found", NOT_FOUND));

        PatientAssuranceResponse patientAssuranceResponse = new PatientAssuranceResponse();

        copyProperties(patientAssurance, patientAssuranceResponse);

        log.info("PatientAssuranceServiceImpl | getPatientAssuranceById | patientAssuranceResponse :" + patientAssuranceResponse.toString());

        return patientAssuranceResponse;
    }

    @Override
    public void editPatientAssurance(PatientAssuranceRequest patientAssuranceRequest, long patientAssuranceId) {
        log.info("PatientAssuranceServiceImpl | editPatientAssurance is called");

        PatientAssurance patientAssurance
                = patientAssuranceRepository.findById(patientAssuranceId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "PatientAssurance with given Id not found",
                        NOT_FOUND
                ));
        patientAssurance.setPatient(patientRepository.findById(patientAssuranceRequest.getPatient_id()).get());
        patientAssurance.setAssurance(assuranceRepository.findById(patientAssuranceRequest.getAssurance_id()).get());
        patientAssurance.setDate_debut(patientAssuranceRequest.getDate_debut());
        patientAssurance.setDate_fin(patientAssuranceRequest.getDate_fin());
        patientAssurance.setTaux(patientAssuranceRequest.getTaux());
        patientAssuranceRepository.save(patientAssurance);

        log.info("PatientAssuranceServiceImpl | editPatientAssurance | PatientAssurance Updated");
        log.info("PatientAssuranceServiceImpl | editPatientAssurance | PatientAssurance Id : " + patientAssurance.getId());
    }

    @Override
    public void deletePatientAssuranceById(long patientAssuranceId) {
        log.info("PatientAssurance id: {}", patientAssuranceId);

        if (!patientAssuranceRepository.existsById(patientAssuranceId)) {
            log.info("Im in this loop {}", !patientAssuranceRepository.existsById(patientAssuranceId));
            throw new SecretariatCustomException(
                    "PatientAssurance with given with Id: " + patientAssuranceId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting PatientAssurance with id: {}", patientAssuranceId);
        patientAssuranceRepository.deleteById(patientAssuranceId);
    }
}
