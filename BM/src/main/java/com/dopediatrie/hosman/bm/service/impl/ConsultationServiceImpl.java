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
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private final OrdonnanceRepository ordonnanceRepository;

    private final ConsultationMotifService consultationMotifService;
    private final ConsultationDiagnosticService consultationDiagnosticService;
    private final ConsultationActeService consultationActeService;
    private final ConstanteService constanteService;
    private final PatientService patientService;
    private final SecteurService secteurService;
    private final AttenteService attenteService;
    private final ActeService acteService;
    private final DiagnosticService diagnosticService;
    private final OrdonnanceService ordonnanceService;

    private final AddressedService addressedService;
    private final DecededService decededService;
    private final TransferedService transferedService;
    private final RefusedService refusedService;
    private final MedecinService medecinService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Consultation> getAllConsultations() {
        return consultationRepository.findAll();
    }

    @Override
    public long addConsultation(ConsultationRequest consultationRequest) {
        log.info("ConsultationServiceImpl | addConsultation is called");

        long constante_id = (consultationRequest.getConstante() != null) ? constanteService.addConstante(consultationRequest.getConstante()) : 0;
        long ordonnance_id = (consultationRequest.getOrdonnance_id() != null && consultationRequest.getOrdonnance_id() > 0) ? consultationRequest.getOrdonnance_id() : 0;

        MedecinResponse consulteur = medecinService.getMedecinForUser(consultationRequest.getConsulteur_id());

        Consultation consultation
                = Consultation.builder()
                .reference(consultationRequest.getReference())
                .type(consultationRequest.getType())
                .commentaire(consultationRequest.getCommentaire())
                .hdm(consultationRequest.getHdm())
                .secteur_code(consultationRequest.getSecteur_code())
                .patient_ref(consultationRequest.getPatient_ref())
                .attente_num(consultationRequest.getAttente_num())
                .consulteur_ref(consulteur.getMatricule())
                .date_consultation(consultationRequest.getDate_consultation())
                .build();

        if(constante_id != 0)
            consultation.setConstante(constanteRepository.findById(constante_id).orElseThrow());

        if(ordonnance_id != 0)
            consultation.setOrdonnance(ordonnanceRepository.findById(ordonnance_id).orElseThrow());

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

        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
        consultationResponse = mapper.convertValue(consultation, ConsultationResponse.class);

        //copyProperties(consultation, consultationResponse);
        consultationResponse.setPatient(patientService.getPatientByRef(consultation.getPatient_ref()));
        consultationResponse.setSecteur(secteurService.getSecteurByCode(consultation.getSecteur_code()));

        if(consultation.getConsulteur_ref() != null && !consultation.getConsulteur_ref().isBlank())
            consultationResponse.setConsulteur(medecinService.getMedecinByMatricule(consultation.getConsulteur_ref()));
        if(consultation.getAttente_num() != null && consultation.getAttente_num() != 0)
            consultationResponse.setAttente(attenteService.getAttenteByNum(consultation.getAttente_num()));
        if(consultation.getConstante() != null)
            consultationResponse.setConstante(constanteService.getConstanteById(consultation.getConstante().getId()));
        if(consultation.getOrdonnance() != null)
            consultationResponse.setOrdonnance(ordonnanceService.getOrdonnanceById(consultation.getOrdonnance().getId()));

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

        if(consultation.getAddressed() != null)
            consultationResponse.setAddressed(addressedService.getAddressedByConsultationId(consultationId));

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
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
        consultationResponse = mapper.convertValue(consultation, ConsultationResponse.class);

        //copyProperties(consultation, consultationResponse);
        consultationResponse.setPatient(patientService.getPatientByRef(consultation.getPatient_ref()));
        consultationResponse.setSecteur(secteurService.getSecteurByCode(consultation.getSecteur_code()));
        if(consultation.getConsulteur_ref() != null && !consultation.getConsulteur_ref().isBlank())
            consultationResponse.setConsulteur(medecinService.getMedecinByMatricule(consultation.getConsulteur_ref()));
        if(consultation.getAttente_num() != null && consultation.getAttente_num() != 0)
            consultationResponse.setAttente(attenteService.getAttenteByNum(consultation.getAttente_num()));
        if(consultation.getConstante() != null)
            consultationResponse.setConstante(constanteService.getConstanteById(consultation.getConstante().getId()));
        if(consultation.getOrdonnance() != null)
            consultationResponse.setOrdonnance(ordonnanceService.getOrdonnanceById(consultation.getOrdonnance().getId()));

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

        if(consultation.getAddressed() != null)
            consultationResponse.setAddressed(addressedService.getAddressedByConsultationId(consultation.getId()));


        log.info("ConsultationServiceImpl | getConsultationById" );
        return consultationResponse;
    }

    @Override
    public List<ConsultationResponse> getConsultationByDateRange(LocalDateTime datemin, LocalDateTime datemax) {
        log.info("ConsultationServiceImpl | getConsultationByDateRange is called");
        List<Consultation> consultations = consultationRepository.findAllByDateRange(datemin, datemax);

        log.info(consultations.size());
        List<ConsultationResponse> consultationResponses = new ArrayList<ConsultationResponse>();
        if ((consultations != null && consultations.size() > 0)){


            for (Consultation consultation : consultations) {
                ConsultationResponse consultationResponse = new ConsultationResponse();
                ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                mapper.registerModule(new JavaTimeModule());
                consultationResponse = mapper.convertValue(consultation, ConsultationResponse.class);

                //copyProperties(consultation, consultationResponse);
                PatientResponse patient = patientService.getPatientByRef(consultations.get(0).getPatient_ref());
                consultationResponse.setPatient(patient);
                consultationResponse.setSecteur(secteurService.getSecteurByCode(consultation.getSecteur_code()));
                if(consultation.getConsulteur_ref() != null && !consultation.getConsulteur_ref().isBlank())
                    consultationResponse.setConsulteur(medecinService.getMedecinByMatricule(consultation.getConsulteur_ref()));
                if(consultation.getAttente_num() != null && consultation.getAttente_num() != 0)
                    consultationResponse.setAttente(attenteService.getAttenteByNum(consultation.getAttente_num()));
                if(consultation.getActes() != null && consultation.getActes().size() > 0){
                    List<ActeResponse> acteResponses = new ArrayList<ActeResponse>();
                    for (ConsultationActe consultationActe : consultation.getActes()) {
                        ActeResponse acteResponse = acteService.getActeByCode(consultationActe.getActe());
                        acteResponses.add(acteResponse);
                    }
                    consultationResponse.setActes(acteResponses);
                }
                consultationResponses.add(consultationResponse);
            }
        }
        return consultationResponses;
    }

    @Override
    public List<ConsultationActeReportResponse> getConsultationActeByDateRange(LocalDateTime dateDebut, LocalDateTime dateFin) {
        log.info("ConsultationServiceImpl | getConsultationActeByDateRange is called");
        String sqlquery = "SELECT t.libelle AS acte,\n" +
                "COUNT(CASE WHEN c.attente_num IS NOT NULL THEN 1 ELSE 0 END) AS total,\n" +
                "SUM(CASE WHEN c.attente_num IS NOT NULL THEN f.total ELSE 0 END) AS montant_facture,\n" +
                "f.reference as ref_facture \n" +
                "FROM consultation c \n" +
                "JOIN consultation_acte ca ON c.id = ca.consultation_id \n" +
                "JOIN tarif t ON ca.acte = t.code \n" +
                "LEFT JOIN attente a on c.attente_num = a.num_attente \n" +
                "LEFT JOIN facture f ON a.facture_id = f.id \n" +
                "WHERE c.date_consultation >= '"+ dateDebut +"' \n" +
                "and c.date_consultation <= '"+ dateFin +"' \n" +
                "GROUP BY t.libelle order by c.date_consultation desc ";

        List<ConsultationActeReportResponse> consultations = jdbcTemplate.query(sqlquery, (resultSet, rowNum) -> new ConsultationActeReportResponse(
                resultSet.getString("acte"),
                resultSet.getInt("total"),
                resultSet.getDouble("montant_facture"),
                resultSet.getString("ref_facture")
        ));
        return consultations;
    }

    @Override
    public List<ConsultationResponse> getConsultationByDateRangeAndSecteurAndDocteur(LocalDateTime dateDebut, LocalDateTime dateFin, String secteur, String docteur) {
        log.info("ConsultationServiceImpl | getConsultationByDateRangeAndSecteurAndDocteur is called");


        List<Consultation> consultations = consultationRepository.findAllByDateRangeAndSecteurAndDocteur(dateDebut, dateFin, secteur, docteur);

        List<ConsultationResponse> consultationResponses = new ArrayList<ConsultationResponse>();
        if ((consultations != null && consultations.size() > 0)){
            PatientResponse patient = patientService.getPatientByRef(consultations.get(0).getPatient_ref());

            for (Consultation consultation : consultations) {
                List<ConsultationActe> actes = consultationActeService.getAllForConsultation(consultation.getId());
                consultation.setActes(actes);


                ConsultationResponse consultationResponse = new ConsultationResponse();
                ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                mapper.registerModule(new JavaTimeModule());
                consultationResponse = mapper.convertValue(consultation, ConsultationResponse.class);

                //copyProperties(consultation, consultationResponse);
                consultationResponse.setPatient(patient);
                consultationResponse.setSecteur(secteurService.getSecteurByCode(consultation.getSecteur_code()));
                if(consultation.getConsulteur_ref() != null && !consultation.getConsulteur_ref().isBlank())
                    consultationResponse.setConsulteur(medecinService.getMedecinByMatricule(consultation.getConsulteur_ref()));
                if(consultation.getAttente_num() != null && consultation.getAttente_num() != 0)
                    consultationResponse.setAttente(attenteService.getAttenteByNum(consultation.getAttente_num()));
                if(consultation.getActes() != null && consultation.getActes().size() > 0){
                    List<ActeResponse> acteResponses = new ArrayList<ActeResponse>();
                    for (ConsultationActe consultationActe : consultation.getActes()) {
                        ActeResponse acteResponse = acteService.getActeByCode(consultationActe.getActe());
                        acteResponses.add(acteResponse);
                    }
                    consultationResponse.setActes(acteResponses);
                }
                consultationResponses.add(consultationResponse);
            }
        }
        return consultationResponses;
    }

    @Override
    public List<ConsultationActeReportResponse> getConsultationActeByDateRangeAndSecteurAndDocteur(LocalDateTime dateDebut, LocalDateTime dateFin, String secteur, String docteur) {
        log.info("ConsultationServiceImpl | getConsultationActeByDateRangeAndSecteurAndDocteur is called");
        String sqlquery = "SELECT t.libelle AS acte,\n" +
                "COUNT(CASE WHEN c.attente_num IS NOT NULL THEN 1 ELSE 0 END) AS total,\n" +
                "SUM(CASE WHEN c.attente_num IS NOT NULL THEN f.total ELSE 0 END) AS montant_facture,\n" +
                "f.reference as ref_facture \n" +
                "FROM consultation c \n" +
                "JOIN consultation_acte ca ON c.id = ca.consultation_id \n" +
                "JOIN tarif t ON ca.acte = t.code \n" +
                "LEFT JOIN attente a on c.attente_num = a.num_attente \n" +
                "LEFT JOIN facture f ON a.facture_id = f.id \n" +
                "WHERE c.date_consultation >= '"+ dateDebut +"' \n" +
                "and c.date_consultation <= '"+ dateFin +"' \n" +
                "and c.secteur_code = '"+ secteur+"' \n" +
                "AND a.receveur = '"+ docteur +"' \n" +
                "GROUP BY t.libelle order by c.date_consultation desc ";

        List<ConsultationActeReportResponse> consultations = jdbcTemplate.query(sqlquery, (resultSet, rowNum) -> new ConsultationActeReportResponse(
                resultSet.getString("acte"),
                resultSet.getInt("total"),
                resultSet.getDouble("montant_facture"),
                resultSet.getString("ref_facture")
                ));
        return consultations;
    }

    @Override
    public List<ConsultationResponse> getConsultationByDateRangeAndSecteur(LocalDateTime dateDebut, LocalDateTime dateFin, String secteur) {
        log.info("ConsultationServiceImpl | getConsultationByDateRangeAndSecteur is called");
        List<Consultation> consultations = consultationRepository.findAllByDateRangeAndSecteur(dateDebut, dateFin, secteur);
        List<ConsultationResponse> consultationResponses = new ArrayList<ConsultationResponse>();
        if ((consultations != null && consultations.size() > 0)){

            for (Consultation consultation : consultations) {
                ConsultationResponse consultationResponse = new ConsultationResponse();
                //copyProperties(consultation, consultationResponse);

                ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                mapper.registerModule(new JavaTimeModule());
                consultationResponse = mapper.convertValue(consultation, ConsultationResponse.class);

                PatientResponse patient = patientService.getPatientByRef(consultation.getPatient_ref());
                consultationResponse.setPatient(patient);
                consultationResponse.setSecteur(secteurService.getSecteurByCode(consultation.getSecteur_code()));
                if(consultation.getConsulteur_ref() != null && !consultation.getConsulteur_ref().isBlank())
                    consultationResponse.setConsulteur(medecinService.getMedecinByMatricule(consultation.getConsulteur_ref()));
                if(consultation.getAttente_num() != null && consultation.getAttente_num() != 0)
                    consultationResponse.setAttente(attenteService.getAttenteByNum(consultation.getAttente_num()));
                if(consultation.getActes() != null && consultation.getActes().size() > 0){
                    List<ActeResponse> acteResponses = new ArrayList<ActeResponse>();
                    for (ConsultationActe consultationActe : consultation.getActes()) {
                        ActeResponse acteResponse = acteService.getActeByCode(consultationActe.getActe());
                        acteResponses.add(acteResponse);
                    }
                    consultationResponse.setActes(acteResponses);
                }
                consultationResponses.add(consultationResponse);
            }
        }
        return consultationResponses;
    }

    @Override
    public List<ConsultationActeReportResponse> getConsultationActeByDateRangeAndSecteur(LocalDateTime dateDebut, LocalDateTime dateFin, String secteur) {
        log.info("ConsultationServiceImpl | getConsultationActeByDateRangeAndSecteur is called");
        String sqlquery = "SELECT t.libelle AS acte,\n" +
                "COUNT(CASE WHEN c.attente_num IS NOT NULL THEN 1 ELSE 0 END) AS total,\n" +
                "SUM(CASE WHEN c.attente_num IS NOT NULL THEN f.total ELSE 0 END) AS montant_facture,\n" +
                "f.reference as ref_facture \n" +
                "FROM consultation c \n" +
                "JOIN consultation_acte ca ON c.id = ca.consultation_id \n" +
                "JOIN tarif t ON ca.acte = t.code \n" +
                "LEFT JOIN attente a on c.attente_num = a.num_attente \n" +
                "LEFT JOIN facture f ON a.facture_id = f.id \n" +
                "WHERE c.date_consultation >= '"+ dateDebut +"' \n" +
                "and c.date_consultation <= '"+ dateFin +"' \n" +
                "and c.secteur_code = '"+ secteur+"' \n" +
                "GROUP BY t.libelle order by c.date_consultation desc ";

        List<ConsultationActeReportResponse> consultations = jdbcTemplate.query(sqlquery, (resultSet, rowNum) -> new ConsultationActeReportResponse(
                resultSet.getString("acte"),
                resultSet.getInt("total"),
                resultSet.getDouble("montant_facture"),
                resultSet.getString("ref_facture")
        ));
        return consultations;
    }

    @Override
    public List<ConsultationResponse> getConsultationByDateRangeAndDocteur(LocalDateTime dateDebut, LocalDateTime dateFin, String docteur) {
        log.info("ConsultationServiceImpl | getConsultationByDateRangeAndDocteur is called");

        List<Consultation> consultations = consultationRepository.findAllByDateRangeAndDocteur(dateDebut, dateFin, docteur);


        List<ConsultationResponse> consultationResponses = new ArrayList<ConsultationResponse>();
        if (consultations.size() > 0){


            for (Consultation consultation : consultations) {
                List<ConsultationActe> actes = consultationActeService.getAllForConsultation(consultation.getId());
                consultation.setActes(actes);

                ConsultationResponse consultationResponse = new ConsultationResponse();
                //copyProperties(consultation, consultationResponse);
                ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                mapper.registerModule(new JavaTimeModule());
                consultationResponse = mapper.convertValue(consultation, ConsultationResponse.class);

                PatientResponse patient = patientService.getPatientByRef(consultation.getPatient_ref());
                consultationResponse.setPatient(patient);
                consultationResponse.setSecteur(secteurService.getSecteurByCode(consultation.getSecteur_code()));
                if(consultation.getConsulteur_ref() != null && !consultation.getConsulteur_ref().isBlank())
                    consultationResponse.setConsulteur(medecinService.getMedecinByMatricule(consultation.getConsulteur_ref()));
                if(consultation.getAttente_num() != null && consultation.getAttente_num() != 0)
                    consultationResponse.setAttente(attenteService.getAttenteByNum(consultation.getAttente_num()));
                if(consultation.getActes() != null && consultation.getActes().size() > 0){
                    List<ActeResponse> acteResponses = new ArrayList<ActeResponse>();
                    for (ConsultationActe consultationActe : consultation.getActes()) {
                        ActeResponse acteResponse = acteService.getActeByCode(consultationActe.getActe());
                        acteResponses.add(acteResponse);
                    }
                    consultationResponse.setActes(acteResponses);
                }
                consultationResponses.add(consultationResponse);
            }
        }
        return consultationResponses;
    }

    @Override
    public List<ConsultationActeReportResponse> getConsultationActeByDateRangeAndDocteur(LocalDateTime dateDebut, LocalDateTime dateFin, String docteur) {
        log.info("ConsultationServiceImpl | getConsultationActeByDateRangeAndDocteur is called");
        String sqlquery = "SELECT t.libelle AS acte,\n" +
                "COUNT(CASE WHEN c.attente_num IS NOT NULL THEN 1 ELSE 0 END) AS total,\n" +
                "SUM(CASE WHEN c.attente_num IS NOT NULL THEN f.total ELSE 0 END) AS montant_facture,\n" +
                "f.reference as ref_facture \n" +
                "FROM consultation c \n" +
                "JOIN consultation_acte ca ON c.id = ca.consultation_id \n" +
                "JOIN tarif t ON ca.acte = t.code \n" +
                "LEFT JOIN attente a on c.attente_num = a.num_attente \n" +
                "LEFT JOIN facture f ON a.facture_id = f.id \n" +
                "WHERE c.date_consultation >= '"+ dateDebut +"' \n" +
                "and c.date_consultation <= '"+ dateFin +"' \n" +
                "AND a.receveur = '"+ docteur +"' \n" +
                "GROUP BY t.libelle order by c.date_consultation desc ";

        List<ConsultationActeReportResponse> consultations = jdbcTemplate.query(sqlquery, (resultSet, rowNum) -> new ConsultationActeReportResponse(
                resultSet.getString("acte"),
                resultSet.getInt("total"),
                resultSet.getDouble("montant_facture"),
                resultSet.getString("ref_facture")
        ));
        return consultations;
    }

    @Override
    public List<ConsultationResponse> getConsultationByDateRangeAndMulti(LocalDateTime dateDebut, LocalDateTime dateFin, String acte, Long motif, String diagnostic) {
        log.info("ConsultationServiceImpl | getConsultationByDateRangeAndMulti is called");

        List<Consultation> consultations = new ArrayList<Consultation>();
        boolean checkActe = (acte != null && !acte.isBlank());
        boolean checkMotif = (motif != null && motif > 0);
        boolean checkDiagnostic = (diagnostic != null && !diagnostic.isBlank());

        if(checkMotif && !checkActe && !checkDiagnostic){
            consultations = consultationRepository.findAllByDateRangeAndMotif(dateDebut, dateFin, motif);
        }else if(checkDiagnostic && !checkActe && !checkMotif){
            consultations = consultationRepository.findAllByDateRangeAndDiagnostic(dateDebut, dateFin, diagnostic);
        }else if(checkActe && !checkMotif && !checkDiagnostic){
            consultations = consultationRepository.findAllByDateRangeAndActe(dateDebut, dateFin, acte);
        }else if(checkActe && checkMotif && !checkDiagnostic){
            consultations = consultationRepository.findAllByDateRangeAndActeAndMotif(dateDebut, dateFin, acte, motif);
        }else if(checkActe && checkDiagnostic && !checkMotif){
            consultations = consultationRepository.findAllByDateRangeAndActeAndDiagnostic(dateDebut, dateFin, acte, diagnostic);
        }else if(checkActe && checkMotif && checkDiagnostic){
            consultations = consultationRepository.findAllByDateRangeAndActeAndMotifAndDiagnostic(dateDebut, dateFin, acte, motif, diagnostic);
        }

        List<ConsultationResponse> consultationResponses = new ArrayList<ConsultationResponse>();
        if (consultations.size() > 0){


            for (Consultation consultation : consultations) {
                List<ConsultationActe> actes = consultationActeService.getAllForConsultation(consultation.getId());
                consultation.setActes(actes);

                ConsultationResponse consultationResponse = new ConsultationResponse();
                //copyProperties(consultation, consultationResponse);
                ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                mapper.registerModule(new JavaTimeModule());
                consultationResponse = mapper.convertValue(consultation, ConsultationResponse.class);

                PatientResponse patient = patientService.getPatientByRef(consultation.getPatient_ref());
                consultationResponse.setPatient(patient);
                consultationResponse.setSecteur(secteurService.getSecteurByCode(consultation.getSecteur_code()));
                if(consultation.getConsulteur_ref() != null && !consultation.getConsulteur_ref().isBlank())
                    consultationResponse.setConsulteur(medecinService.getMedecinByMatricule(consultation.getConsulteur_ref()));
                if(consultation.getAttente_num() != null && consultation.getAttente_num() != 0)
                    consultationResponse.setAttente(attenteService.getAttenteByNum(consultation.getAttente_num()));
                if(consultation.getActes() != null && consultation.getActes().size() > 0){
                    List<ActeResponse> acteResponses = new ArrayList<ActeResponse>();
                    for (ConsultationActe consultationActe : consultation.getActes()) {
                        ActeResponse acteResponse = acteService.getActeByCode(consultationActe.getActe());
                        acteResponses.add(acteResponse);
                    }
                    consultationResponse.setActes(acteResponses);
                }
                consultationResponses.add(consultationResponse);
            }
        }
        return consultationResponses;
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
                ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                mapper.registerModule(new JavaTimeModule());
                consultationResponse = mapper.convertValue(consultation, ConsultationResponse.class);

                //copyProperties(consultation, consultationResponse);
                consultationResponse.setPatient(patient);
                consultationResponse.setSecteur(secteurService.getSecteurByCode(consultation.getSecteur_code()));
                if(consultation.getConsulteur_ref() != null && !consultation.getConsulteur_ref().isBlank())
                    consultationResponse.setConsulteur(medecinService.getMedecinByMatricule(consultation.getConsulteur_ref()));
                if(consultation.getAttente_num() != null && consultation.getAttente_num() != 0)
                    consultationResponse.setAttente(attenteService.getAttenteByNum(consultation.getAttente_num()));
                if(consultation.getConstante() != null)
                    consultationResponse.setConstante(constanteService.getConstanteById(consultation.getConstante().getId()));
                if(consultation.getOrdonnance() != null)
                    consultationResponse.setOrdonnance(ordonnanceService.getOrdonnanceById(consultation.getOrdonnance().getId()));

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
        long ordonnance_id = (consultationRequest.getOrdonnance_id() != null && consultationRequest.getOrdonnance_id() > 0) ? consultationRequest.getOrdonnance_id() : 0;

        MedecinResponse consulteur = medecinService.getMedecinForUser(consultationRequest.getConsulteur_id());

        consultation.setReference(consultationRequest.getReference());
        consultation.setType(consultationRequest.getType());
        consultation.setCommentaire(consultationRequest.getCommentaire());
        consultation.setHdm(consultationRequest.getHdm());
        consultation.setSecteur_code(consultationRequest.getSecteur_code());
        consultation.setPatient_ref(consultationRequest.getPatient_ref());
        consultation.setAttente_num(consultationRequest.getAttente_num());
        consultation.setConsulteur_ref(consulteur.getMatricule());
        consultation.setDate_consultation(consultationRequest.getDate_consultation());
        if(ordonnance_id != 0)
            consultation.setOrdonnance(ordonnanceRepository.findById(ordonnance_id).orElseThrow());


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
