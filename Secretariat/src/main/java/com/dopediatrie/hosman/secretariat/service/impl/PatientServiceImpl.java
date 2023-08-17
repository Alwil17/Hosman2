package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Patient;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.PatientRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PatientResponse;
import com.dopediatrie.hosman.secretariat.repository.*;
import com.dopediatrie.hosman.secretariat.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final PaysRepository paysRepository;
    private final ProfessionRepository professionRepository;
    private final EmployeurRepository employeurRepository;
    private final PersonneAPrevenirRepository personneAPrevenirRepository;
    private final String NOT_FOUND = "PATIENT_NOT_FOUND";

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public long addPatient(PatientRequest patientRequest) {
        log.info("PatientServiceImpl | addPatient is called");

        Patient patient
                = Patient.builder()
                .nom(patientRequest.getNom())
                .prenoms(patientRequest.getPrenoms())
                .reference(patientRequest.getReference())
                .date_naissance(patientRequest.getDate_naissance())
                .lieu_naissance(patientRequest.getLieu_naissance())
                .sexe(patientRequest.getSexe())
                .tel1(patientRequest.getTel1())
                .tel2(patientRequest.getTel2())
                .email(patientRequest.getEmail())
                .type_piece(patientRequest.getType_piece())
                .no_piece(patientRequest.getNo_piece())
                .is_assure(patientRequest.getIs_assure())
                .pays_origine(paysRepository.findById(patientRequest.getPays_origine_id()).get())
                .profession(professionRepository.findById(patientRequest.getProfession_id()).get())
                .employeur(employeurRepository.findById(patientRequest.getEmployeur_id()).get())
                .personne_a_prevenir(personneAPrevenirRepository.findById(patientRequest.getPersonne_a_prevenir_id()).get())
                .structure_id(patientRequest.getStructure_id())
                .build();

        patient = patientRepository.save(patient);

        log.info("PatientServiceImpl | addPatient | Patient Created");
        log.info("PatientServiceImpl | addPatient | Patient Id : " + patient.getId());
        return patient.getId();
    }

    @Override
    public PatientResponse getPatientById(long patientId) {
        log.info("PatientServiceImpl | getPatientById is called");
        log.info("PatientServiceImpl | getPatientById | Get the patient for patientId: {}", patientId);

        Patient patient
                = patientRepository.findById(patientId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Patient with given Id not found", NOT_FOUND));

        PatientResponse patientResponse = new PatientResponse();

        copyProperties(patient, patientResponse);

        log.info("PatientServiceImpl | getPatientById | patientResponse :" + patientResponse.toString());

        return patientResponse;
    }

    @Override
    public void editPatient(PatientRequest patientRequest, long patientId) {
        log.info("PatientServiceImpl | editPatient is called");

        Patient patient
                = patientRepository.findById(patientId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Patient with given Id not found",
                        NOT_FOUND
                ));
        patient.setNom(patientRequest.getNom());
        patient.setPrenoms(patientRequest.getPrenoms());
        patient.setReference(patientRequest.getReference());
        patient.setDate_naissance(patientRequest.getDate_naissance());
        patient.setLieu_naissance(patientRequest.getLieu_naissance());
        patient.setSexe(patientRequest.getSexe());
        patient.setTel1(patientRequest.getTel1());
        patient.setTel2(patientRequest.getTel2());
        patient.setEmail(patientRequest.getEmail());
        patient.setType_piece(patientRequest.getType_piece());
        patient.setNo_piece(patientRequest.getNo_piece());
        patient.setIs_assure(patientRequest.getIs_assure());
        patient.setPays_origine(paysRepository.findById(patientRequest.getPays_origine_id()).get());
        patient.setProfession(professionRepository.findById(patientRequest.getProfession_id()).get());
        patient.setEmployeur(employeurRepository.findById(patientRequest.getEmployeur_id()).get());
        patient.setPersonne_a_prevenir(personneAPrevenirRepository.findById(patientRequest.getPersonne_a_prevenir_id()).get());
        patient.setStructure_id(patientRequest.getStructure_id());
        patientRepository.save(patient);

        log.info("PatientServiceImpl | editPatient | Patient Updated");
        log.info("PatientServiceImpl | editPatient | Patient Id : " + patient.getId());
    }

    @Override
    public void deletePatientById(long patientId) {
        log.info("Patient id: {}", patientId);

        if (!patientRepository.existsById(patientId)) {
            log.info("Im in this loop {}", !patientRepository.existsById(patientId));
            throw new SecretariatCustomException(
                    "Patient with given with Id: " + patientId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Patient with id: {}", patientId);
        patientRepository.deleteById(patientId);
    }
}
