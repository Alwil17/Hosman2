package com.dopediatrie.hosman.hospi.service.impl;

import com.dopediatrie.hosman.hospi.entity.Hospit;
import com.dopediatrie.hosman.hospi.exception.HospiCustomException;
import com.dopediatrie.hosman.hospi.payload.request.HospitRequest;
import com.dopediatrie.hosman.hospi.payload.response.HospitResponse;
import com.dopediatrie.hosman.hospi.repository.HospitRepository;
import com.dopediatrie.hosman.hospi.service.HospitService;
import com.dopediatrie.hosman.hospi.service.PatientService;
import com.dopediatrie.hosman.hospi.service.SecteurService;
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
    private final SecteurService secteurService;

    @Override
    public List<Hospit> getAllHospits() {
        return hospitRepository.findAll();
    }

    @Override
    public long addHospit(HospitRequest hospitRequest) {
        log.info("HospitServiceImpl | addHospit is called");

        Hospit hospit = Hospit.builder()
                .motif(hospitRequest.getMotif())
                .diagnostic(hospitRequest.getDiagnostic())
                .hdm(hospitRequest.getHdm())
                .patient_id(hospitRequest.getPatient_id())
                .secteur_id(hospitRequest.getSecteur_id())
                .consultation_id(hospitRequest.getConsultation_id())
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
                    .motif(hospitRequest.getMotif())
                    .diagnostic(hospitRequest.getDiagnostic())
                    .hdm(hospitRequest.getHdm())
                    .patient_id(hospitRequest.getPatient_id())
                    .secteur_id(hospitRequest.getSecteur_id())
                    .consultation_id(hospitRequest.getConsultation_id())
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
        hospitResponse.setPatient(patientService.getPatientById(hospit.getPatient_id()));
        hospitResponse.setSecteur(secteurService.getSecteurById(hospit.getSecteur_id()));


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
        hospit.setMotif(hospitRequest.getMotif());
        hospit.setDiagnostic(hospitRequest.getDiagnostic());
        hospit.setHdm(hospitRequest.getHdm());
        hospit.setPatient_id(hospitRequest.getPatient_id());
        hospit.setSecteur_id(hospitRequest.getSecteur_id());
        hospit.setConsultation_id(hospitRequest.getConsultation_id());
        hospit.setDate_hospit(hospitRequest.getDate_hospit());
        hospit.setUpdated_at(LocalDateTime.now());
        hospitRepository.save(hospit);

        log.info("HospitServiceImpl | editHospit | Hospit Updated");
        log.info("HospitServiceImpl | editHospit | Hospit Id : " + hospit.getId());
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
}
