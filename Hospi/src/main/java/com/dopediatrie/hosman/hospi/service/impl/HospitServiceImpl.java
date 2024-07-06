package com.dopediatrie.hosman.hospi.service.impl;

import com.dopediatrie.hosman.hospi.entity.Hospit;
import com.dopediatrie.hosman.hospi.exception.HospiCustomException;
import com.dopediatrie.hosman.hospi.payload.request.HospitRequest;
import com.dopediatrie.hosman.hospi.payload.response.HospitResponse;
import com.dopediatrie.hosman.hospi.repository.HospitRepository;
import com.dopediatrie.hosman.hospi.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class HospitServiceImpl implements HospitService {
    private final String NOT_FOUND = "HOSPIT_NOT_FOUND";

    private final HospitRepository hospitRepository;
    private final PatientService patientService;
    private final MotifService motifService;
    private final SecteurService secteurService;
    private final DiagnosticService diagnosticService;
    private final ConsultationService consultationService;

    @Override
    public List<Hospit> getAllHospits() {
        return hospitRepository.findAll();
    }

    @Override
    public long addHospit(HospitRequest hospitRequest) {
        log.info("HospitServiceImpl | addHospit is called");

        Hospit hospit = Hospit.builder()
                .motif_libelle(hospitRequest.getMotif())
                .diagnostic_code(hospitRequest.getDiagnostic())
                .hdm(hospitRequest.getHdm())
                .patient_ref(hospitRequest.getPatient_ref())
                .secteur_code(hospitRequest.getSecteur_code())
                .arrive(hospitRequest.getArrive())
                .extras(hospitRequest.getExtras())
                .status(hospitRequest.getStatus())
                .consultation_ref(hospitRequest.getConsultation_ref())
                .date_hospit(hospitRequest.getDate_hospit())
                .created_at(LocalDateTime.now())
                .build();

        hospit = hospitRepository.save(hospit);

        log.info("HospitServiceImpl | addHospit | Hospit Created");
        log.info("HospitServiceImpl | addHospit | Hospit Id : " + hospit.getId());
        return hospit.getId();
    }

    @Override
    public void addHospit(List<HospitRequest> hospitRequests) {
        log.info("HospitServiceImpl | addHospit is called");

        for (HospitRequest hospitRequest : hospitRequests) {
            Hospit hospit = Hospit.builder()
                    .motif_libelle(hospitRequest.getMotif())
                    .diagnostic_code(hospitRequest.getDiagnostic())
                    .hdm(hospitRequest.getHdm())
                    .patient_ref(hospitRequest.getPatient_ref())
                    .secteur_code(hospitRequest.getSecteur_code())
                    .arrive(hospitRequest.getArrive())
                    .extras(hospitRequest.getExtras())
                    .status(hospitRequest.getStatus())
                    .consultation_ref(hospitRequest.getConsultation_ref())
                    .date_hospit(hospitRequest.getDate_hospit())
                    .created_at(LocalDateTime.now())
                    .build();
            hospitRepository.save(hospit);
        }

        log.info("HospitServiceImpl | addHospit | Hospit Created");
    }

    @Override
    public HospitResponse getHospitById(long hospitId) {
        log.info("HospitServiceImpl | getHospitById is called");
        log.info("HospitServiceImpl | getHospitById | Get the hospit for hospitId: {}", hospitId);

        Hospit hospit
                = hospitRepository.findById(hospitId)
                .orElseThrow(
                        () -> new HospiCustomException("Hospit with given Id not found", NOT_FOUND));

        HospitResponse hospitResponse = new HospitResponse();
        copyProperties(hospit, hospitResponse);
        if(hospit.getMotif_libelle() != null && !hospit.getMotif_libelle().isBlank())
            hospitResponse.setMotif(motifService.getMotifByLibelle(hospit.getMotif_libelle()));
        if(hospit.getDiagnostic_code() != null && !hospit.getDiagnostic_code().isBlank())
            hospitResponse.setDiagnostic(diagnosticService.getDiagnosticByCode(hospit.getDiagnostic_code()));
        if(hospit.getPatient_ref() != null && !hospit.getPatient_ref().isBlank())
            hospitResponse.setPatient(patientService.getPatientByRef(hospit.getPatient_ref()));
        if(hospit.getSecteur_code() != null && !hospit.getSecteur_code().isBlank())
            hospitResponse.setSecteur(secteurService.getSecteurByCode(hospit.getSecteur_code()));
        hospitResponse.setConsultation(consultationService.getConsultationByRef(hospit.getConsultation_ref()));
        //log.info("HospitServiceImpl | getHospitById | hospitResponse :" + hospitResponse.toString());

        return hospitResponse;
    }

    @Override
    public void editHospit(HospitRequest hospitRequest, long hospitId) {
        log.info("HospitServiceImpl | editHospit is called");

        Hospit hospit
                = hospitRepository.findById(hospitId)
                .orElseThrow(() -> new HospiCustomException(
                        "Hospit with given Id not found",
                        NOT_FOUND
                ));
        hospit.setMotif_libelle(hospitRequest.getMotif());
        hospit.setDiagnostic_code(hospitRequest.getDiagnostic());
        hospit.setHdm(hospitRequest.getHdm());
        hospit.setPatient_ref(hospitRequest.getPatient_ref());
        hospit.setSecteur_code(hospitRequest.getSecteur_code());
        hospit.setArrive(hospitRequest.getArrive());
        hospit.setExtras(hospitRequest.getExtras());
        hospit.setStatus(hospitRequest.getStatus());
        hospit.setConsultation_ref(hospitRequest.getConsultation_ref());
        hospit.setDate_hospit(hospitRequest.getDate_hospit());
        hospit.setUpdated_at(LocalDateTime.now());
        hospitRepository.save(hospit);

        log.info("HospitServiceImpl | editHospit | Hospit Updated");
        log.info("HospitServiceImpl | editHospit | Hospit Id : " + hospit.getId());
    }

    @Override
    public void updateStatus(long hospitId, int status) {
        log.info("HospitServiceImpl | updateStatus is called");

        Hospit hospit
                = hospitRepository.findById(hospitId)
                .orElseThrow(() -> new HospiCustomException(
                        "Hospit with given Id not found",
                        NOT_FOUND
                ));
        hospit.setStatus(status);
        log.info("HospitServiceImpl | updateStatus | Hospit Updated");
    }

    @Override
    public void deleteHospitById(long hospitId) {
        log.info("Hospit id: {}", hospitId);

        if (!hospitRepository.existsById(hospitId)) {
            log.info("Im in this loop {}", !hospitRepository.existsById(hospitId));
            throw new HospiCustomException(
                    "Hospit with given with Id: " + hospitId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Hospit with id: {}", hospitId);
        hospitRepository.deleteById(hospitId);
    }

    @Override
    public List<Hospit> getHospitByStatus(int status) {
        log.info("HospitServiceImpl | getHospitByStatus is called");
        return hospitRepository.findByStatus(status);
    }
}
