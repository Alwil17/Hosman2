package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.entity.*;
import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.request.ConsultationActeRequest;
import com.dopediatrie.hosman.bm.payload.request.ConsultationDiagnosticRequest;
import com.dopediatrie.hosman.bm.payload.request.ConsultationMotifRequest;
import com.dopediatrie.hosman.bm.payload.request.ConsultationRequest;
import com.dopediatrie.hosman.bm.payload.response.*;
import com.dopediatrie.hosman.bm.repository.*;
import com.dopediatrie.hosman.bm.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class ConsultationServiceImpl implements ConsultationService {
    private final String NOT_FOUND = "CONSULTATION_NOT_FOUND";
    private final ConsultationRepository consultationRepository;
    private final ConstanteRepository constanteRepository;

    private final ConsultationMotifService consultationMotifService;
    private final ConsultationDiagnosticService consultationDiagnosticService;
    private final ConsultationActeService consultationActeService;
    private final ConstanteService constanteService;
    private final PatientService patientService;
    private final SecteurService secteurService;
    private final AttenteService attenteService;
    private final ActeService acteService;
    private final DiagnosticService diagnosticService;

    @Override
    public List<Consultation> getAllConsultations() {
        return consultationRepository.findAll();
    }

    @Override
    public long addConsultation(ConsultationRequest consultationRequest) {
        log.info("ConsultationServiceImpl | addConsultation is called");

        long constante_id = (consultationRequest.getConstante() != null) ? constanteService.addConstante(consultationRequest.getConstante()) : 0;

        Consultation consultation
                = Consultation.builder()
                .reference(consultationRequest.getReference())
                .type(consultationRequest.getType())
                .commentaire(consultationRequest.getCommentaire())
                .hdm(consultationRequest.getHdm())
                .secteur_code(consultationRequest.getSecteur_code())
                .patient_ref(consultationRequest.getPatient_ref())
                .attente_num(consultationRequest.getAttente_num())
                .date_consultation(consultationRequest.getDate_consultation())
                .build();

        if(constante_id != 0)
            consultation.setConstante(constanteRepository.findById(constante_id).orElseThrow());

        consultation = consultationRepository.save(consultation);
        consultation.setReference(String.format("%06d", consultation.getId()));
        consultation = consultationRepository.save(consultation);

        if((consultationRequest.getMotifs() != null) && (consultationRequest.getMotifs().size() > 0)){
            consultationMotifService.deleteAllForConsultationId(consultation.getId());
            for (ConsultationMotifRequest iMotif : consultationRequest.getMotifs()) {
                iMotif.setConsultation_id(consultation.getId());
                consultationMotifService.addConsultationMotif(iMotif);
            }
        }

        if((consultationRequest.getDiagnostics() != null) && (consultationRequest.getDiagnostics().size() > 0)){
            consultationDiagnosticService.deleteAllForConsultationId(consultation.getId());
            for (ConsultationDiagnosticRequest iDiagnostic : consultationRequest.getDiagnostics()) {
                iDiagnostic.setConsultation_id(consultation.getId());
                consultationDiagnosticService.addConsultationDiagnostic(iDiagnostic);
            }
        }

        if((consultationRequest.getActes() != null) && (consultationRequest.getActes().size() > 0)){
            consultationActeService.deleteAllForConsultationId(consultation.getId());
            for (ConsultationActeRequest iActe : consultationRequest.getActes()) {
                iActe.setConsultation_id(consultation.getId());
                consultationActeService.addConsultationActe(iActe);
            }
        }

        if((consultationRequest.getAttente_num() != null) && (consultationRequest.getAttente_num() != 0)){
            attenteService.deleteAttenteByNum(consultationRequest.getAttente_num());
        }

        log.info("ConsultationServiceImpl | addConsultation | Consultation Created");
//        log.info("ConsultationServiceImpl | addConsultation | Consultation Id : " + consultation.getId());
        return consultation.getId();
    }

    @Override
    public ConsultationResponse getConsultationById(long consultationId) {
        log.info("ConsultationServiceImpl | getConsultationById is called");
        log.info("ConsultationServiceImpl | getConsultationById | Get the consultation for consultationId: {}", consultationId);

        Consultation consultation
                = consultationRepository.findById(consultationId)
                .orElseThrow(
                        () -> new BMCustomException("Consultation with given Id not found", NOT_FOUND));

        ConsultationResponse consultationResponse = new ConsultationResponse();
        copyProperties(consultation, consultationResponse);
        consultationResponse.setPatient(patientService.getPatientByRef(consultation.getPatient_ref()));
        consultationResponse.setSecteur(secteurService.getSecteurByCode(consultation.getSecteur_code()));
        if(consultation.getAttente_num() != null && consultation.getAttente_num() != 0)
            consultationResponse.setAttente(attenteService.getAttenteByNum(consultation.getAttente_num()));
        if(consultation.getConstante() != null)
            consultationResponse.setConstante(constanteService.getConstanteById(consultation.getConstante().getId()));

        if(consultation.getActes() != null && consultation.getActes().size() > 0){
            List<ActeResponse> acteResponses = new ArrayList<ActeResponse>();
            for (ConsultationActe consultationActe : consultation.getActes()) {
                ActeResponse acteResponse = acteService.getActeByCode(consultationActe.getActe());
                acteResponses.add(acteResponse);
            }
            consultationResponse.setActes(acteResponses);
        }
        if(consultation.getDiagnostics() != null && consultation.getDiagnostics().size() > 0){
            List<DiagnosticResponse> diagnosticResponses = new ArrayList<DiagnosticResponse>();
            for (ConsultationDiagnostic consultationDiagnostic : consultation.getDiagnostics()) {
                DiagnosticResponse diagnosticResponse = diagnosticService.getDiagnosticByCode(consultationDiagnostic.getDiagnostic());
                diagnosticResponses.add(diagnosticResponse);
            }
            consultationResponse.setDiagnostics(diagnosticResponses);
        }

        if(consultation.getMotifs() != null && consultation.getMotifs().size() > 0){
            List<MotifResponse> motifResponses = new ArrayList<MotifResponse>();
            for (Motif consultationMotif : consultation.getMotifs()) {
                MotifResponse motifResponse = new MotifResponse();
                copyProperties(consultationMotif, motifResponse);
                motifResponses.add(motifResponse);
            }
            consultationResponse.setMotifs(motifResponses);
        }

        log.info("ConsultationServiceImpl | getConsultationById" );
        return consultationResponse;
    }

    @Override
    public ConsultationResponse getConsultationByRef(String consultationRef) {
        log.info("ConsultationServiceImpl | getConsultationByRef is called");
        log.info("ConsultationServiceImpl | getConsultationByRef | Get the consultation for consultationId: {}", consultationRef);

        Consultation consultation
                = consultationRepository.findByRef(consultationRef)
                .orElseThrow(
                        () -> new BMCustomException("Consultation with given Id not found", NOT_FOUND));

        ConsultationResponse consultationResponse = new ConsultationResponse();
        copyProperties(consultation, consultationResponse);
        consultationResponse.setPatient(patientService.getPatientByRef(consultation.getPatient_ref()));
        consultationResponse.setSecteur(secteurService.getSecteurByCode(consultation.getSecteur_code()));
        if(consultation.getAttente_num() != null && consultation.getAttente_num() != 0)
            consultationResponse.setAttente(attenteService.getAttenteByNum(consultation.getAttente_num()));
        if(consultation.getConstante() != null)
            consultationResponse.setConstante(constanteService.getConstanteById(consultation.getConstante().getId()));

        if(consultation.getActes() != null && consultation.getActes().size() > 0){
            List<ActeResponse> acteResponses = new ArrayList<ActeResponse>();
            for (ConsultationActe consultationActe : consultation.getActes()) {
                ActeResponse acteResponse = acteService.getActeByCode(consultationActe.getActe());
                acteResponses.add(acteResponse);
            }
            consultationResponse.setActes(acteResponses);
        }
        if(consultation.getDiagnostics() != null && consultation.getDiagnostics().size() > 0){
            List<DiagnosticResponse> diagnosticResponses = new ArrayList<DiagnosticResponse>();
            for (ConsultationDiagnostic consultationDiagnostic : consultation.getDiagnostics()) {
                DiagnosticResponse diagnosticResponse = diagnosticService.getDiagnosticByCode(consultationDiagnostic.getDiagnostic());
                diagnosticResponses.add(diagnosticResponse);
            }
            consultationResponse.setDiagnostics(diagnosticResponses);
        }

        if(consultation.getMotifs() != null && consultation.getMotifs().size() > 0){
            List<MotifResponse> motifResponses = new ArrayList<MotifResponse>();
            for (Motif consultationMotif : consultation.getMotifs()) {
                MotifResponse motifResponse = new MotifResponse();
                copyProperties(consultationMotif, motifResponse);
                motifResponses.add(motifResponse);
            }
            consultationResponse.setMotifs(motifResponses);
        }

        log.info("ConsultationServiceImpl | getConsultationById" );
        return consultationResponse;
    }

    @Override
    public List<ConsultationResponse> getConsultationByPatientRef(String patientRef) {
        log.info("ConsultationServiceImpl | getConsultationByPatientId is called");
        List<Consultation> consultations = consultationRepository.findAllByPatient_ref(patientRef);
        List<ConsultationResponse> consultationResponses = new ArrayList<ConsultationResponse>();
        if ((consultations != null && consultations.size() >0)){
            PatientResponse patient = patientService.getPatientByRef(consultations.get(0).getPatient_ref());

            for (Consultation consultation : consultations) {
                ConsultationResponse consultationResponse = new ConsultationResponse();
                copyProperties(consultation, consultationResponse);
                consultationResponse.setPatient(patient);
                consultationResponse.setSecteur(secteurService.getSecteurByCode(consultation.getSecteur_code()));
                if(consultation.getAttente_num() != null && consultation.getAttente_num() != 0)
                    consultationResponse.setAttente(attenteService.getAttenteByNum(consultation.getAttente_num()));
                if(consultation.getConstante() != null)
                    consultationResponse.setConstante(constanteService.getConstanteById(consultation.getConstante().getId()));
                if(consultation.getActes() != null && consultation.getActes().size() > 0){
                    List<ActeResponse> acteResponses = new ArrayList<ActeResponse>();
                    for (ConsultationActe consultationActe : consultation.getActes()) {
                        ActeResponse acteResponse = acteService.getActeByCode(consultationActe.getActe());
                        acteResponses.add(acteResponse);
                    }
                    consultationResponse.setActes(acteResponses);
                }
                if(consultation.getDiagnostics() != null && consultation.getDiagnostics().size() > 0){
                    List<DiagnosticResponse> diagnosticResponses = new ArrayList<DiagnosticResponse>();
                    for (ConsultationDiagnostic consultationDiagnostic : consultation.getDiagnostics()) {
                        DiagnosticResponse diagnosticResponse = diagnosticService.getDiagnosticByCode(consultationDiagnostic.getDiagnostic());
                        diagnosticResponses.add(diagnosticResponse);
                    }
                    consultationResponse.setDiagnostics(diagnosticResponses);
                }

                if(consultation.getMotifs() != null && consultation.getMotifs().size() > 0){
                    List<MotifResponse> motifResponses = new ArrayList<MotifResponse>();
                    for (Motif consultationMotif : consultation.getMotifs()) {
                        MotifResponse motifResponse = new MotifResponse();
                        copyProperties(consultationMotif, motifResponse);
                        motifResponses.add(motifResponse);
                    }
                    consultationResponse.setMotifs(motifResponses);
                }

                consultationResponses.add(consultationResponse);
            }
        }
        return consultationResponses;
    }

    @Override
    public void editConsultation(ConsultationRequest consultationRequest, long consultationId) {
        log.info("ConsultationServiceImpl | editConsultation is called");

        Consultation consultation
                = consultationRepository.findById(consultationId)
                .orElseThrow(() -> new BMCustomException(
                        "Consultation with given Id not found",
                        NOT_FOUND
                ));
        consultation.setReference(consultationRequest.getReference());
        consultation.setType(consultationRequest.getType());
        consultation.setCommentaire(consultationRequest.getCommentaire());
        consultation.setHdm(consultationRequest.getHdm());
        consultation.setSecteur_code(consultationRequest.getSecteur_code());
        consultation.setPatient_ref(consultationRequest.getPatient_ref());
        consultation.setAttente_num(consultationRequest.getAttente_num());
        consultation.setDate_consultation(consultationRequest.getDate_consultation());
        consultationRepository.save(consultation);

        log.info("ConsultationServiceImpl | editConsultation | Consultation Updated");
        log.info("ConsultationServiceImpl | editConsultation | Consultation Id : " + consultation.getId());
    }

    @Override
    public void deleteConsultationById(long consultationId) {
        log.info("Consultation id: {}", consultationId);

        if (!consultationRepository.existsById(consultationId)) {
            log.info("Im in this loop {}", !consultationRepository.existsById(consultationId));
            throw new BMCustomException(
                    "Consultation with given with Id: " + consultationId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Consultation with id: {}", consultationId);
        consultationRepository.deleteById(consultationId);
    }
}
