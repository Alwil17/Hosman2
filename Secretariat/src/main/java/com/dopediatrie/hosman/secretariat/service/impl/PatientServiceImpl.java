package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Patient;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.FiliationRequest;
import com.dopediatrie.hosman.secretariat.payload.request.PatientMaladieRequest;
import com.dopediatrie.hosman.secretariat.payload.request.PatientRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PatientResponse;
import com.dopediatrie.hosman.secretariat.repository.*;
import com.dopediatrie.hosman.secretariat.service.*;
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
    private final AdresseRepository adresseRepository;
    private final AssuranceRepository assuranceRepository;
    private final PatientRepository patientRepository;
    private final PaysRepository paysRepository;
    private final ProfessionRepository professionRepository;
    private final EmployeurRepository employeurRepository;
    private final PersonneAPrevenirRepository personneAPrevenirRepository;

    private final PersonneAPrevenirService personneAPrevenirService;
    private final AdresseService adresseService;
    private final AssuranceService assuranceService;
    private final ProfessionService professionService;
    private final EmployeurService employeurService;
    private final PatientMaladieService patientMaladieService;
    private final FiliationService filiationService;
    private final String NOT_FOUND = "PATIENT_NOT_FOUND";

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public long addPatient(PatientRequest patientRequest) {
        log.info("PatientServiceImpl | addPatient is called");

        long personne_a_prevenir_id = (patientRequest.getPersonne_a_prevenir() != null) ? personneAPrevenirService.addPersonneAPrevenir(patientRequest.getPersonne_a_prevenir()) : 0;
        long adresse_id = adresseService.addAdresse(patientRequest.getAdresse());
        long assurance_id = (patientRequest.getAssurance() != null) ? assuranceService.addAssurance(patientRequest.getAssurance()) : 0;
        long profession_id = (patientRequest.getProfession() != null) ? professionService.addProfession(patientRequest.getProfession()) : 0;
        long employeur_id = (patientRequest.getEmployeur() != null) ? employeurService.addEmployeur(patientRequest.getEmployeur()) : 0;

        Patient patient
                = Patient.builder()
                .nom(patientRequest.getNom())
                .prenoms(patientRequest.getPrenoms())
                .date_naissance(patientRequest.getDate_naissance())
                .lieu_naissance(patientRequest.getLieu_naissance())
                .sexe(patientRequest.getSexe())
                .tel1(patientRequest.getTel1())
                .tel2(patientRequest.getTel2())
                .email(patientRequest.getEmail())
                .type_piece(patientRequest.getType_piece())
                .no_piece(patientRequest.getNo_piece())
                .date_ajout(patientRequest.getDate_ajout())
                .is_assure(patientRequest.getIs_assure())
                .adresse(adresseRepository.findById(adresse_id).orElseThrow())
                .structure_id(patientRequest.getStructure_id())
                .taux_assurance(patientRequest.getTaux_assurance())
                .date_debut_assurance(patientRequest.getDate_debut_assurance())
                .date_fin_assurance(patientRequest.getDate_fin_assurance())
                .build();

        if(patientRequest.getPays_origine_id() != 0)
            patient.setPays_origine(paysRepository.findById(patientRequest.getPays_origine_id()).orElseThrow());
        if(assurance_id != 0)
            patient.setAssurance(assuranceRepository.findById(assurance_id).orElseThrow());
        if(patientRequest.getNationalite_id() != 0)
            patient.setNationalite(paysRepository.findById(patientRequest.getNationalite_id()).orElseThrow());
        if(profession_id != 0)
            patient.setProfession(professionRepository.findById(profession_id).orElseThrow());
        if(employeur_id != 0)
            patient.setEmployeur(employeurRepository.findById(employeur_id).orElseThrow());
        if(personne_a_prevenir_id != 0)
            patient.setPersonne_a_prevenir(personneAPrevenirRepository.findById(personne_a_prevenir_id).orElseThrow());

        patient = patientRepository.save(patient);
        patient.setReference(String.format("%06d", patient.getId()));
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

//        log.info("PatientServiceImpl | getPatientById | patientResponse :" + patientResponse.toString());

        return patientResponse;
    }

    @Override
    public List<Patient> getPatientByNomAndPrenoms(String nom) {
        log.info("PatientServiceImpl | getPatientByNomAndPrenoms is called");

        return patientRepository.findByNomAndPrenomsLike(nom);
    }

    @Override
    public List<Patient> getPatientByReference(String reference) {
        log.info("PatientServiceImpl | getPatientByReference is called");

        return patientRepository.findByReferenceLike(reference);
    }

    @Override
    public PatientResponse getPatientByReferenceUnique(String reference) {
        log.info("PatientServiceImpl | getPatientByReference is called");

        Patient patient
                = patientRepository.findByReferenceEquals(reference)
                .orElseThrow(
                        () -> new SecretariatCustomException("Patient with given Id not found", NOT_FOUND));

        PatientResponse patientResponse = new PatientResponse();

        copyProperties(patient, patientResponse);

        return patientResponse;
    }

    @Override
    public List<Patient> getPatientByPrenoms(String prenoms) {
        log.info("PatientServiceImpl | getPatientByPrenoms is called");

        return patientRepository.findByPrenomsLike(prenoms);
    }

    @Override
    public List<Patient> getPatientByDateNaissance(LocalDateTime dateNaissance, LocalDateTime dateNaissanceLimit) {
        log.info("PatientServiceImpl | getPatientByDateNaissance is called");
        return patientRepository.findAllByDate_naissance(dateNaissance, dateNaissanceLimit);
    }

    @Override
    public List<Patient> getPatientByDateEntree(LocalDateTime dateEntree, LocalDateTime dateEntreeLimit) {
        log.info("PatientServiceImpl | getPatientByDateEntree is called");
        return patientRepository.findAllByDate_ajout(dateEntree, dateEntreeLimit);
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

        long personne_a_prevenir_id = (patientRequest.getPersonne_a_prevenir() != null) ? personneAPrevenirService.addPersonneAPrevenir(patientRequest.getPersonne_a_prevenir()) : 0;
        adresseService.editAdresse(patientRequest.getAdresse(), patient.getAdresse().getId());
        long assurance_id = (patientRequest.getAssurance() != null) ? assuranceService.addAssurance(patientRequest.getAssurance()) : 0;
        long profession_id = (patientRequest.getProfession() != null) ? professionService.addProfession(patientRequest.getProfession()) : 0;
        long employeur_id = (patientRequest.getEmployeur() != null) ? employeurService.addEmployeur(patientRequest.getEmployeur()) : 0;


        patient.setNom(patientRequest.getNom());
        patient.setPrenoms(patientRequest.getPrenoms());
        patient.setDate_naissance(patientRequest.getDate_naissance());
        patient.setLieu_naissance(patientRequest.getLieu_naissance());
        patient.setSexe(patientRequest.getSexe());
        patient.setTel1(patientRequest.getTel1());
        patient.setTel2(patientRequest.getTel2());
        patient.setEmail(patientRequest.getEmail());
        patient.setType_piece(patientRequest.getType_piece());
        patient.setNo_piece(patientRequest.getNo_piece());
        patient.setDate_ajout(patientRequest.getDate_ajout());
        patient.setIs_assure(patientRequest.getIs_assure());
        patient.setTaux_assurance(patientRequest.getTaux_assurance());
        patient.setDate_debut_assurance(patientRequest.getDate_debut_assurance());
        patient.setDate_fin_assurance(patientRequest.getDate_fin_assurance());
        patient.setStructure_id(patientRequest.getStructure_id());

        if(patientRequest.getPays_origine_id() != 0)
            patient.setPays_origine(paysRepository.findById(patientRequest.getPays_origine_id()).orElseThrow());
        if(patientRequest.getNationalite_id() != 0)
            patient.setNationalite(paysRepository.findById(patientRequest.getNationalite_id()).orElseThrow());
        if(assurance_id != 0)
            patient.setAssurance(assuranceRepository.findById(assurance_id).orElseThrow());
        if(profession_id != 0)
            patient.setProfession(professionRepository.findById(profession_id).orElseThrow());
        if(employeur_id != 0)
            patient.setEmployeur(employeurRepository.findById(employeur_id).orElseThrow());
        if(personne_a_prevenir_id != 0)
            patient.setPersonne_a_prevenir(personneAPrevenirRepository.findById(personne_a_prevenir_id).orElseThrow());

        patientRepository.save(patient);

        log.info("PatientServiceImpl | editPatient | Patient Updated");
        log.info("PatientServiceImpl | editPatient | Patient Id : " + patient.getId());
    }

    @Override
    public void editPatientCaracs(PatientRequest patientRequest, long patientId) {
        log.info("PatientServiceImpl | editPatientCaracs is called");

        Patient patient
                = patientRepository.findById(patientId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Patient with given Id not found",
                        NOT_FOUND
                ));

        patient.setCommentaire(patientRequest.getCommentaire());
        patient.setAntecedent(patientRequest.getAntecedent());
        patientRepository.save(patient);

        if((patientRequest.getMaladies()) != null && (patientRequest.getMaladies().size() > 0)){
            patientMaladieService.deleteAllForPatientId(patientId);
            for (PatientMaladieRequest maladieRequest : patientRequest.getMaladies()) {
                maladieRequest.setPatient_id(patientId);
                patientMaladieService.addPatientMaladie(maladieRequest);
            }
        }

        if((patientRequest.getParents()) != null && (patientRequest.getParents().size() > 0)){
            for (FiliationRequest filiationRequest : patientRequest.getParents()) {
                filiationRequest.setPatient_id(patientId);
                filiationService.addFiliation(filiationRequest);
            }
        }

        log.info("PatientServiceImpl | editPatientCaracs | Patient Updated");
        log.info("PatientServiceImpl | editPatientCaracs | Patient Id : " + patient.getId());
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
