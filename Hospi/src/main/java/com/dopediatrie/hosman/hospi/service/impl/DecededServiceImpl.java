package com.dopediatrie.hosman.hospi.service.impl;

import com.dopediatrie.hosman.hospi.entity.Deceded;
import com.dopediatrie.hosman.hospi.exception.HospiCustomException;
import com.dopediatrie.hosman.hospi.payload.request.DecededRequest;
import com.dopediatrie.hosman.hospi.payload.response.DecededResponse;
import com.dopediatrie.hosman.hospi.repository.HospitRepository;
import com.dopediatrie.hosman.hospi.repository.DecededRepository;
import com.dopediatrie.hosman.hospi.service.DecededService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class DecededServiceImpl implements DecededService {
    private final String NOT_FOUND = "DECEDED_NOT_FOUND";

    private final DecededRepository suiviRepository;
    private final HospitRepository hospitRepository;

    @Override
    public List<Deceded> getAllDecededs() {
        return suiviRepository.findAll();
    }

    @Override
    public long addDeceded(DecededRequest suiviRequest) {
        log.info("DecededServiceImpl | addDeceded is called");
        Deceded suivi = Deceded.builder()
                .reanimation(suiviRequest.getReanimation())
                .after_out(suiviRequest.getAfter_out())
                .comments(suiviRequest.getComments())
                .date_op(suiviRequest.getDate_op())
                .motif(suiviRequest.getMotif())
                .hospit(hospitRepository.findById(suiviRequest.getHospit_id()).orElseThrow())
                .build();
        suivi = suiviRepository.save(suivi);

        log.info("DecededServiceImpl | addDeceded | Deceded Created/Updated");
        log.info("DecededServiceImpl | addDeceded | Deceded Id : " + suivi.getId());
        return suivi.getId();
    }

    @Override
    public void addDeceded(List<DecededRequest> suiviRequests) {
        log.info("DecededServiceImpl | addDeceded is called");

        for (DecededRequest suiviRequest : suiviRequests) {
            Deceded suivi = Deceded.builder()
                    .reanimation(suiviRequest.getReanimation())
                    .after_out(suiviRequest.getAfter_out())
                    .comments(suiviRequest.getComments())
                    .date_op(suiviRequest.getDate_op())
                    .motif(suiviRequest.getMotif())
                    .hospit(hospitRepository.findById(suiviRequest.getHospit_id()).orElseThrow())
                    .build();
            suiviRepository.save(suivi);

        }

        log.info("DecededServiceImpl | addDeceded | Deceded Created");
    }

    @Override
    public DecededResponse getDecededById(long suiviId) {
        log.info("DecededServiceImpl | getDecededById is called");
        log.info("DecededServiceImpl | getDecededById | Get the suivi for suiviId: {}", suiviId);

        Deceded suivi
                = suiviRepository.findById(suiviId)
                .orElseThrow(
                        () -> new HospiCustomException("Deceded with given Id not found", NOT_FOUND));

        DecededResponse suiviResponse = new DecededResponse();

        copyProperties(suivi, suiviResponse);

        log.info("DecededServiceImpl | getDecededById | suiviResponse :" + suiviResponse.toString());

        return suiviResponse;
    }

    @Override
    public List<DecededResponse> getDecededByHospitId(long hospitId) {
        log.info("DecededServiceImpl | getDecededByHospitId is called");
        List<Deceded> suivis = suiviRepository.findAllByHospitId(hospitId);
        List<DecededResponse> suiviResponses = new ArrayList<>();
        for (Deceded s : suivis) {
            DecededResponse sr = new DecededResponse();
            copyProperties(s, sr);
            suiviResponses.add(sr);
        }
        return suiviResponses;
    }

    @Override
    public void editDeceded(DecededRequest suiviRequest, long suiviId) {
        log.info("DecededServiceImpl | editDeceded is called");

        Deceded suivi
                = suiviRepository.findById(suiviId)
                .orElseThrow(() -> new HospiCustomException(
                        "Deceded with given Id not found",
                        NOT_FOUND
                ));
        suivi.setReanimation(suiviRequest.getReanimation());
        suivi.setAfter_out(suiviRequest.getAfter_out());
        suivi.setComments(suiviRequest.getComments());
        suivi.setDate_op(suiviRequest.getDate_op());
        suivi.setMotif(suiviRequest.getMotif());
        suivi.setHospit(hospitRepository.findById(suiviRequest.getHospit_id()).orElseThrow());
        suiviRepository.save(suivi);

        log.info("DecededServiceImpl | editDeceded | Deceded Updated");
        log.info("DecededServiceImpl | editDeceded | Deceded Id : " + suivi.getId());
    }

    @Override
    public void deleteDecededById(long suiviId) {
        log.info("Deceded id: {}", suiviId);

        if (!suiviRepository.existsById(suiviId)) {
            log.info("Im in this loop {}", !suiviRepository.existsById(suiviId));
            throw new HospiCustomException(
                    "Deceded with given with Id: " + suiviId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Deceded with id: {}", suiviId);
        suiviRepository.deleteById(suiviId);
    }
}
