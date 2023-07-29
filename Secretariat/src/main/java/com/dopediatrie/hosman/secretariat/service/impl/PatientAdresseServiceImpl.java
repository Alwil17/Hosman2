package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.PatientAdresse;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.PatientAdresseRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PatientAdresseResponse;
import com.dopediatrie.hosman.secretariat.repository.PatientAdresseRepository;
import com.dopediatrie.hosman.secretariat.service.PatientAdresseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class PatientAdresseServiceImpl implements PatientAdresseService {
    private final PatientAdresseRepository patientAdresseRepository;
    private final String NOT_FOUND = "PATIENT_ADRESSE_NOT_FOUND";

    @Override
    public List<PatientAdresse> getAllPatientAdresses() {
        return patientAdresseRepository.findAll();
    }

    @Override
    public long addPatientAdresse(PatientAdresseRequest patientAdresseRequest) {
        log.info("PatientAdresseServiceImpl | addPatientAdresse is called");

        PatientAdresse patientAdresse
                = PatientAdresse.builder()
                .patient_id(patientAdresseRequest.getPatient_id())
                .adresse_id(patientAdresseRequest.getAdresse_id())
                .build();

        patientAdresse = patientAdresseRepository.save(patientAdresse);

        log.info("PatientAdresseServiceImpl | addPatientAdresse | PatientAdresse Created");
        log.info("PatientAdresseServiceImpl | addPatientAdresse | PatientAdresse Id : " + patientAdresse.getId());
        return patientAdresse.getId();
    }

    @Override
    public PatientAdresseResponse getPatientAdresseById(long patientAdresseId) {
        log.info("PatientAdresseServiceImpl | getPatientAdresseById is called");
        log.info("PatientAdresseServiceImpl | getPatientAdresseById | Get the patientAdresse for patientAdresseId: {}", patientAdresseId);

        PatientAdresse patientAdresse
                = patientAdresseRepository.findById(patientAdresseId)
                .orElseThrow(
                        () -> new SecretariatCustomException("PatientAdresse with given Id not found", NOT_FOUND));

        PatientAdresseResponse patientAdresseResponse = new PatientAdresseResponse();

        copyProperties(patientAdresse, patientAdresseResponse);

        log.info("PatientAdresseServiceImpl | getPatientAdresseById | patientAdresseResponse :" + patientAdresseResponse.toString());

        return patientAdresseResponse;
    }

    @Override
    public void editPatientAdresse(PatientAdresseRequest patientAdresseRequest, long patientAdresseId) {
        log.info("PatientAdresseServiceImpl | editPatientAdresse is called");

        PatientAdresse patientAdresse
                = patientAdresseRepository.findById(patientAdresseId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "PatientAdresse with given Id not found",
                        NOT_FOUND
                ));
        patientAdresse.setPatient_id(patientAdresseRequest.getPatient_id());
        patientAdresse.setAdresse_id(patientAdresseRequest.getAdresse_id());
        patientAdresseRepository.save(patientAdresse);

        log.info("PatientAdresseServiceImpl | editPatientAdresse | PatientAdresse Updated");
        log.info("PatientAdresseServiceImpl | editPatientAdresse | PatientAdresse Id : " + patientAdresse.getId());
    }

    @Override
    public void deletePatientAdresseById(long patientAdresseId) {
        log.info("PatientAdresse id: {}", patientAdresseId);

        if (!patientAdresseRepository.existsById(patientAdresseId)) {
            log.info("Im in this loop {}", !patientAdresseRepository.existsById(patientAdresseId));
            throw new SecretariatCustomException(
                    "PatientAdresse with given with Id: " + patientAdresseId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting PatientAdresse with id: {}", patientAdresseId);
        patientAdresseRepository.deleteById(patientAdresseId);
    }
}
