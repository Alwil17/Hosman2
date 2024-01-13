package com.dopediatrie.hosman.hospi.service.impl;

import com.dopediatrie.hosman.hospi.entity.Scam;
import com.dopediatrie.hosman.hospi.exception.HospiCustomException;
import com.dopediatrie.hosman.hospi.payload.request.ScamRequest;
import com.dopediatrie.hosman.hospi.payload.response.ScamResponse;
import com.dopediatrie.hosman.hospi.repository.HospitRepository;
import com.dopediatrie.hosman.hospi.repository.ScamRepository;
import com.dopediatrie.hosman.hospi.service.ScamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class ScamServiceImpl implements ScamService {
    private final String NOT_FOUND = "SCAM_NOT_FOUND";

    private final ScamRepository suiviRepository;
    private final HospitRepository hospitRepository;

    @Override
    public List<Scam> getAllScams() {
        return suiviRepository.findAll();
    }

    @Override
    public long addScam(ScamRequest suiviRequest) {
        log.info("ScamServiceImpl | addScam is called");
        Scam suivi = Scam.builder()
                .ordonnance(suiviRequest.getOrdonnance())
                .decharge(suiviRequest.getDecharge())
                .comments(suiviRequest.getComments())
                .date_op(suiviRequest.getDate_op())
                .motif(suiviRequest.getMotif())
                .hospit(hospitRepository.findById(suiviRequest.getHospit_id()).orElseThrow())
                .build();
        suivi = suiviRepository.save(suivi);

        log.info("ScamServiceImpl | addScam | Scam Created/Updated");
        log.info("ScamServiceImpl | addScam | Scam Id : " + suivi.getId());
        return suivi.getId();
    }

    @Override
    public void addScam(List<ScamRequest> suiviRequests) {
        log.info("ScamServiceImpl | addScam is called");

        for (ScamRequest suiviRequest : suiviRequests) {
            Scam suivi = Scam.builder()
                    .ordonnance(suiviRequest.getOrdonnance())
                    .decharge(suiviRequest.getDecharge())
                    .comments(suiviRequest.getComments())
                    .date_op(suiviRequest.getDate_op())
                    .motif(suiviRequest.getMotif())
                    .hospit(hospitRepository.findById(suiviRequest.getHospit_id()).orElseThrow())
                    .build();
            suiviRepository.save(suivi);

        }

        log.info("ScamServiceImpl | addScam | Scam Created");
    }

    @Override
    public ScamResponse getScamById(long suiviId) {
        log.info("ScamServiceImpl | getScamById is called");
        log.info("ScamServiceImpl | getScamById | Get the suivi for suiviId: {}", suiviId);

        Scam suivi
                = suiviRepository.findById(suiviId)
                .orElseThrow(
                        () -> new HospiCustomException("Scam with given Id not found", NOT_FOUND));

        ScamResponse suiviResponse = new ScamResponse();

        copyProperties(suivi, suiviResponse);

        log.info("ScamServiceImpl | getScamById | suiviResponse :" + suiviResponse.toString());

        return suiviResponse;
    }

    @Override
    public List<ScamResponse> getScamByHospitId(long hospitId) {
        log.info("ScamServiceImpl | getScamByHospitId is called");
        List<Scam> suivis = suiviRepository.findAllByHospitId(hospitId);
        List<ScamResponse> suiviResponses = new ArrayList<>();
        for (Scam s : suivis) {
            ScamResponse sr = new ScamResponse();
            copyProperties(s, sr);
            suiviResponses.add(sr);
        }
        return suiviResponses;
    }

    @Override
    public void editScam(ScamRequest suiviRequest, long suiviId) {
        log.info("ScamServiceImpl | editScam is called");

        Scam suivi
                = suiviRepository.findById(suiviId)
                .orElseThrow(() -> new HospiCustomException(
                        "Scam with given Id not found",
                        NOT_FOUND
                ));
        suivi.setOrdonnance(suiviRequest.getOrdonnance());
        suivi.setDecharge(suiviRequest.getDecharge());
        suivi.setComments(suiviRequest.getComments());
        suivi.setDate_op(suiviRequest.getDate_op());
        suivi.setMotif(suiviRequest.getMotif());
        suivi.setHospit(hospitRepository.findById(suiviRequest.getHospit_id()).orElseThrow());
        suiviRepository.save(suivi);

        log.info("ScamServiceImpl | editScam | Scam Updated");
        log.info("ScamServiceImpl | editScam | Scam Id : " + suivi.getId());
    }

    @Override
    public void deleteScamById(long suiviId) {
        log.info("Scam id: {}", suiviId);

        if (!suiviRepository.existsById(suiviId)) {
            log.info("Im in this loop {}", !suiviRepository.existsById(suiviId));
            throw new HospiCustomException(
                    "Scam with given with Id: " + suiviId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Scam with id: {}", suiviId);
        suiviRepository.deleteById(suiviId);
    }
}
