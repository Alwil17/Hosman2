package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.entity.Consultation;
import com.dopediatrie.hosman.bm.entity.Ordonnance;
import com.dopediatrie.hosman.bm.entity.Prescription;
import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.request.OrdonnancePrescriptionRequest;
import com.dopediatrie.hosman.bm.payload.request.OrdonnanceRequest;
import com.dopediatrie.hosman.bm.payload.request.PrescriptionRequest;
import com.dopediatrie.hosman.bm.payload.response.*;
import com.dopediatrie.hosman.bm.repository.ConstanteRepository;
import com.dopediatrie.hosman.bm.repository.ConsultationRepository;
import com.dopediatrie.hosman.bm.repository.OrdonnanceRepository;
import com.dopediatrie.hosman.bm.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrdonnanceServiceImpl implements OrdonnanceService {
    private final String NOT_FOUND = "ORDONNANCE_NOT_FOUND";
    private final OrdonnanceRepository ordonnanceRepository;
    private final ConsultationRepository consultationRepository;

    private final OrdonnancePrescriptionService ordonnancePrescriptionService;
    private final PrescriptionService prescriptionService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Ordonnance> getAllOrdonnances() {
        return ordonnanceRepository.findAll();
    }

    @Override
    public long addOrdonnance(OrdonnanceRequest ordonnanceRequest) {
        log.info("OrdonnanceServiceImpl | addOrdonnance is called");
        Ordonnance ordonnance
                = Ordonnance.builder()
                .indicateur1(ordonnanceRequest.getIndicateur1())
                .indicateur2(ordonnanceRequest.getIndicateur2())
                .stocked(ordonnanceRequest.isStocked())
                .prepositionned(ordonnanceRequest.isPrepositionned())
                .created_at(LocalDateTime.now())
                .build();

        ordonnance = ordonnanceRepository.save(ordonnance);
        ordonnance.setReference(String.format("%07d", ordonnance.getId()));
        ordonnance = ordonnanceRepository.save(ordonnance);

        if(ordonnanceRequest.getConsultation_id() != null && ordonnanceRequest.getConsultation_id() != 0){
            Consultation consultation = consultationRepository.findById(ordonnanceRequest.getConsultation_id()).orElseThrow();
            consultation.setOrdonnance(ordonnance);
            consultationRepository.save(consultation);
        }
        if((ordonnanceRequest.getPrescriptions() != null) && (ordonnanceRequest.getPrescriptions().size() > 0)){
            ordonnancePrescriptionService.deleteAllForOrdonnanceId(ordonnance.getId());
            for (PrescriptionRequest iPrescription : ordonnanceRequest.getPrescriptions()) {
                long pId = prescriptionService.addPrescription(iPrescription);
                OrdonnancePrescriptionRequest opr = OrdonnancePrescriptionRequest.builder()
                        .ordonnance_id(ordonnance.getId())
                        .prescription_id(pId)
                        .build();
                ordonnancePrescriptionService.addOrdonnancePrescription(opr);
            }
        }

        log.info("OrdonnanceServiceImpl | addOrdonnance | Ordonnance Created");
//        log.info("OrdonnanceServiceImpl | addOrdonnance | Ordonnance Id : " + ordonnance.getId());
        return ordonnance.getId();
    }

    @Override
    public OrdonnanceResponse getOrdonnanceById(long ordonnanceId) {
        log.info("OrdonnanceServiceImpl | getOrdonnanceById is called");
        log.info("OrdonnanceServiceImpl | getOrdonnanceById | Get the ordonnance for ordonnanceId: {}", ordonnanceId);

        Ordonnance ordonnance
                = ordonnanceRepository.findById(ordonnanceId)
                .orElseThrow(
                        () -> new BMCustomException("Ordonnance with given Id not found", NOT_FOUND));

        OrdonnanceResponse ordonnanceResponse = new OrdonnanceResponse();
        copyProperties(ordonnance, ordonnanceResponse);

        if(ordonnance.getPrescriptions() != null && ordonnance.getPrescriptions().size() > 0){
            List<PrescriptionResponse> prescriptionResponses = new ArrayList<PrescriptionResponse>();
            for (Prescription ordonnancePrescription : ordonnance.getPrescriptions()) {
                PrescriptionResponse prescriptionResponse = new PrescriptionResponse();
                copyProperties(ordonnancePrescription, prescriptionResponse);
                prescriptionResponses.add(prescriptionResponse);
            }
            ordonnanceResponse.setPrescriptions(prescriptionResponses);
        }

        log.info("OrdonnanceServiceImpl | getOrdonnanceById" );
        return ordonnanceResponse;
    }

    @Override
    public OrdonnanceResponse getOrdonnanceByRef(String ordonnanceRef) {
        log.info("OrdonnanceServiceImpl | getOrdonnanceByRef is called");
        log.info("OrdonnanceServiceImpl | getOrdonnanceByRef | Get the ordonnance for ordonnanceId: {}", ordonnanceRef);

        Ordonnance ordonnance
                = ordonnanceRepository.findByReference(ordonnanceRef)
                .orElseThrow(
                        () -> new BMCustomException("Ordonnance with given Ref not found", NOT_FOUND));

        OrdonnanceResponse ordonnanceResponse = new OrdonnanceResponse();
        copyProperties(ordonnance, ordonnanceResponse);
        //ordonnanceResponse.setPatient(patientService.getPatientByRef(ordonnance.getPatient_ref()));


        if(ordonnance.getPrescriptions() != null && ordonnance.getPrescriptions().size() > 0){
            List<PrescriptionResponse> prescriptionResponses = new ArrayList<PrescriptionResponse>();
            for (Prescription ordonnancePrescription : ordonnance.getPrescriptions()) {
                PrescriptionResponse prescriptionResponse = new PrescriptionResponse();
                copyProperties(ordonnancePrescription, prescriptionResponse);
                prescriptionResponses.add(prescriptionResponse);
            }
            ordonnanceResponse.setPrescriptions(prescriptionResponses);
        }

        log.info("OrdonnanceServiceImpl | getOrdonnanceById" );
        return ordonnanceResponse;
    }

    @Override
    public List<String> getOrdonnanceDiagnostics(String queryString) {
        log.info("OrdonnanceServiceImpl | getOrdonnanceDiagnostics is called");
        String baseQuery = "SELECT diagnostic " +
                "from ordonnance where prepositionned = 1 ";
        if(queryString != null && !queryString.isBlank()){
            baseQuery += "AND diagnostic like '%"+ queryString +"%'";
        }

        List<String> diagnostics = jdbcTemplate.query(baseQuery, (resultSet, rowNum) -> resultSet.getString("diagnostic"));
        return diagnostics;
    }

    @Override
    public List<Ordonnance> getOrdonnancesForDiagnostic(String diagnostic) {
        log.info("OrdonnanceServiceImpl | getOrdonnancesForDiagnostic is called");
        return ordonnanceRepository.findAllForDiagnostic(diagnostic);
    }

    @Override
    public List<Ordonnance> getOrdonnanceByPatientRef(String patient_ref) {
        log.info("OrdonnanceServiceImpl | getOrdonnanceByPatientRef is called");
        return ordonnanceRepository.findAllForPatient(patient_ref);
    }

    @Override
    public OrdonnanceResponse getOrdonnanceByConsultationRef(String consultaionRef) {
        log.info("OrdonnanceServiceImpl | getOrdonnanceByConsultationRef is called");
        Ordonnance ordonnance
                = ordonnanceRepository.findByConsultation_ref(consultaionRef)
                .orElseThrow(
                        () -> new BMCustomException("Ordonnance with given Ref not found", NOT_FOUND));

        OrdonnanceResponse ordonnanceResponse = new OrdonnanceResponse();
        copyProperties(ordonnance, ordonnanceResponse);
        //ordonnanceResponse.setPatient(patientService.getPatientByRef(ordonnance.getPatient_ref()));


        if(ordonnance.getPrescriptions() != null && ordonnance.getPrescriptions().size() > 0){
            List<PrescriptionResponse> prescriptionResponses = new ArrayList<PrescriptionResponse>();
            for (Prescription ordonnancePrescription : ordonnance.getPrescriptions()) {
                PrescriptionResponse prescriptionResponse = new PrescriptionResponse();
                copyProperties(ordonnancePrescription, prescriptionResponse);
                prescriptionResponses.add(prescriptionResponse);
            }
            ordonnanceResponse.setPrescriptions(prescriptionResponses);
        }

        return ordonnanceResponse;
    }

    @Override
    public OrdonnanceResponse getOrdonnanceByConsultationId(long consultationId) {
        log.info("OrdonnanceServiceImpl | getOrdonnanceByConsultationId is called");
        Ordonnance ordonnance
                = ordonnanceRepository.findById(consultationId)
                .orElseThrow(
                        () -> new BMCustomException("Ordonnance with given Id not found", NOT_FOUND));

        OrdonnanceResponse ordonnanceResponse = new OrdonnanceResponse();
        copyProperties(ordonnance, ordonnanceResponse);
        //ordonnanceResponse.setPatient(patientService.getPatientByRef(ordonnance.getPatient_ref()));


        if(ordonnance.getPrescriptions() != null && ordonnance.getPrescriptions().size() > 0){
            List<PrescriptionResponse> prescriptionResponses = new ArrayList<PrescriptionResponse>();
            for (Prescription ordonnancePrescription : ordonnance.getPrescriptions()) {
                PrescriptionResponse prescriptionResponse = new PrescriptionResponse();
                copyProperties(ordonnancePrescription, prescriptionResponse);
                prescriptionResponses.add(prescriptionResponse);
            }
            ordonnanceResponse.setPrescriptions(prescriptionResponses);
        }

        return ordonnanceResponse;
    }

    @Override
    public void editOrdonnance(OrdonnanceRequest ordonnanceRequest, long ordonnanceId) {
        log.info("OrdonnanceServiceImpl | editOrdonnance is called");

        Ordonnance ordonnance
                = ordonnanceRepository.findById(ordonnanceId)
                .orElseThrow(() -> new BMCustomException(
                        "Ordonnance with given Id not found",
                        NOT_FOUND
                ));
        ordonnance.setIndicateur1(ordonnanceRequest.getIndicateur1());
        ordonnance.setIndicateur2(ordonnanceRequest.getIndicateur2());
        ordonnance.setPatient_ref(ordonnanceRequest.getPatient_ref());
        ordonnance.setStocked(ordonnanceRequest.isStocked());
        ordonnance.setPrepositionned(ordonnanceRequest.isPrepositionned());
        ordonnance.setUpdated_at(LocalDateTime.now());
        ordonnanceRepository.save(ordonnance);

        log.info("OrdonnanceServiceImpl | editOrdonnance | Ordonnance Updated");
        log.info("OrdonnanceServiceImpl | editOrdonnance | Ordonnance Id : " + ordonnance.getId());
    }

    @Override
    public void deleteOrdonnanceById(long ordonnanceId) {
        log.info("Ordonnance id: {}", ordonnanceId);

        if (!ordonnanceRepository.existsById(ordonnanceId)) {
            log.info("Im in this loop {}", !ordonnanceRepository.existsById(ordonnanceId));
            throw new BMCustomException(
                    "Ordonnance with given with Id: " + ordonnanceId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Ordonnance with id: {}", ordonnanceId);
        ordonnanceRepository.deleteById(ordonnanceId);
    }
}
