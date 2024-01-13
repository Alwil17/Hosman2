package com.dopediatrie.hosman.hospi.service.impl;

import com.dopediatrie.hosman.hospi.entity.Transfused;
import com.dopediatrie.hosman.hospi.exception.HospiCustomException;
import com.dopediatrie.hosman.hospi.payload.request.TransfusedRequest;
import com.dopediatrie.hosman.hospi.payload.response.PatientResponse;
import com.dopediatrie.hosman.hospi.payload.response.TransfusedResponse;
import com.dopediatrie.hosman.hospi.repository.HospitRepository;
import com.dopediatrie.hosman.hospi.repository.TransfusedRepository;
import com.dopediatrie.hosman.hospi.service.PatientService;
import com.dopediatrie.hosman.hospi.service.TransfusedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class TransfusedServiceImpl implements TransfusedService {
    private final String NOT_FOUND = "TRANSFUSED_NOT_FOUND";

    private final TransfusedRepository suiviRepository;
    private final HospitRepository hospitRepository;
    private final PatientService patientService;

    @Override
    public List<Transfused> getAllTransfuseds() {
        return suiviRepository.findAll();
    }

    @Override
    public long addTransfused(TransfusedRequest suiviRequest) {
        log.info("TransfusedServiceImpl | addTransfused is called");
        Transfused suivi = Transfused.builder()
                .provenance(suiviRequest.getProvenance())
                .hemoglobine(suiviRequest.getHemoglobine())
                .comments(suiviRequest.getComments())
                .date_op(suiviRequest.getDate_op())
                .donneur_ref(suiviRequest.getDonneur_ref())
                .receveur_ref(suiviRequest.getReceveur_ref())
                .motif(suiviRequest.getMotif())
                .hospit(hospitRepository.findById(suiviRequest.getHospit_id()).orElseThrow())
                .build();
        suivi = suiviRepository.save(suivi);

        log.info("TransfusedServiceImpl | addTransfused | Transfused Created/Updated");
        log.info("TransfusedServiceImpl | addTransfused | Transfused Id : " + suivi.getId());
        return suivi.getId();
    }

    @Override
    public void addTransfused(List<TransfusedRequest> suiviRequests) {
        log.info("TransfusedServiceImpl | addTransfused is called");

        for (TransfusedRequest suiviRequest : suiviRequests) {
            Transfused suivi = Transfused.builder()
                    .provenance(suiviRequest.getProvenance())
                    .hemoglobine(suiviRequest.getHemoglobine())
                    .comments(suiviRequest.getComments())
                    .date_op(suiviRequest.getDate_op())
                    .donneur_ref(suiviRequest.getDonneur_ref())
                    .receveur_ref(suiviRequest.getReceveur_ref())
                    .motif(suiviRequest.getMotif())
                    .hospit(hospitRepository.findById(suiviRequest.getHospit_id()).orElseThrow())
                    .build();
            suiviRepository.save(suivi);

        }

        log.info("TransfusedServiceImpl | addTransfused | Transfused Created");
    }

    @Override
    public TransfusedResponse getTransfusedById(long suiviId) {
        log.info("TransfusedServiceImpl | getTransfusedById is called");
        log.info("TransfusedServiceImpl | getTransfusedById | Get the suivi for suiviId: {}", suiviId);

        Transfused suivi
                = suiviRepository.findById(suiviId)
                .orElseThrow(
                        () -> new HospiCustomException("Transfused with given Id not found", NOT_FOUND));

        TransfusedResponse suiviResponse = new TransfusedResponse();
        copyProperties(suivi, suiviResponse);
        PatientResponse donneur = patientService.getPatientByRef(suivi.getDonneur_ref());
        suiviResponse.setDonneur(donneur);
        PatientResponse receveur = patientService.getPatientByRef(suivi.getReceveur_ref());
        suiviResponse.setReceveur(receveur);

        log.info("TransfusedServiceImpl | getTransfusedById | suiviResponse :" + suiviResponse.toString());

        return suiviResponse;
    }

    @Override
    public List<TransfusedResponse> getTransfusedByHospitId(long hospitId) {
        log.info("TransfusedServiceImpl | getTransfusedByHospitId is called");
        List<Transfused> suivis = suiviRepository.findAllByHospitId(hospitId);
        List<TransfusedResponse> suiviResponses = new ArrayList<>();
        for (Transfused s : suivis) {
            TransfusedResponse sr = new TransfusedResponse();
            copyProperties(s, sr);
            PatientResponse donneur = patientService.getPatientByRef(s.getDonneur_ref());
            sr.setDonneur(donneur);
            PatientResponse receveur = patientService.getPatientByRef(s.getReceveur_ref());
            sr.setReceveur(receveur);
            suiviResponses.add(sr);
        }
        return suiviResponses;
    }

    @Override
    public void editTransfused(TransfusedRequest suiviRequest, long suiviId) {
        log.info("TransfusedServiceImpl | editTransfused is called");

        Transfused suivi
                = suiviRepository.findById(suiviId)
                .orElseThrow(() -> new HospiCustomException(
                        "Transfused with given Id not found",
                        NOT_FOUND
                ));
        suivi.setProvenance(suiviRequest.getProvenance());
        suivi.setHemoglobine(suiviRequest.getHemoglobine());
        suivi.setComments(suiviRequest.getComments());
        suivi.setDate_op(suiviRequest.getDate_op());
        suivi.setDonneur_ref(suiviRequest.getDonneur_ref());
        suivi.setReceveur_ref(suiviRequest.getReceveur_ref());
        suivi.setMotif(suiviRequest.getMotif());
        suivi.setHospit(hospitRepository.findById(suiviRequest.getHospit_id()).orElseThrow());
        suiviRepository.save(suivi);

        log.info("TransfusedServiceImpl | editTransfused | Transfused Updated");
        log.info("TransfusedServiceImpl | editTransfused | Transfused Id : " + suivi.getId());
    }

    @Override
    public void deleteTransfusedById(long suiviId) {
        log.info("Transfused id: {}", suiviId);

        if (!suiviRepository.existsById(suiviId)) {
            log.info("Im in this loop {}", !suiviRepository.existsById(suiviId));
            throw new HospiCustomException(
                    "Transfused with given with Id: " + suiviId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Transfused with id: {}", suiviId);
        suiviRepository.deleteById(suiviId);
    }
}
