package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.entity.Deceded;
import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.request.DecededRequest;
import com.dopediatrie.hosman.bm.payload.response.DecededResponse;
import com.dopediatrie.hosman.bm.repository.ConsultationRepository;
import com.dopediatrie.hosman.bm.repository.DecededRepository;
import com.dopediatrie.hosman.bm.service.DecededService;
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

    private final DecededRepository decededRepository;
    private final ConsultationRepository consultationRepository;

    @Override
    public List<Deceded> getAllDecededs() {
        return decededRepository.findAll();
    }

    @Override
    public long addDeceded(DecededRequest decededRequest) {
        log.info("DecededServiceImpl | addDeceded is called");
        Deceded deceded = Deceded.builder()
                .reanimation(decededRequest.isReanimation())
                .after_out(decededRequest.isAfter_out())
                .comments(decededRequest.getComments())
                .date_op(decededRequest.getDate_op())
                .motif(decededRequest.getMotif())
                .consultation(consultationRepository.findById(decededRequest.getConsultation_id()).orElseThrow())
                .build();
        deceded = decededRepository.save(deceded);

        log.info("DecededServiceImpl | addDeceded | Deceded Created/Updated");
        log.info("DecededServiceImpl | addDeceded | Deceded Id : " + deceded.getId());
        return deceded.getId();
    }

    @Override
    public void addDeceded(List<DecededRequest> decededRequests) {
        log.info("DecededServiceImpl | addDeceded is called");

        for (DecededRequest decededRequest : decededRequests) {
            Deceded deceded = Deceded.builder()
                    .reanimation(decededRequest.isReanimation())
                    .after_out(decededRequest.isAfter_out())
                    .comments(decededRequest.getComments())
                    .date_op(decededRequest.getDate_op())
                    .motif(decededRequest.getMotif())
                    .consultation(consultationRepository.findById(decededRequest.getConsultation_id()).orElseThrow())
                    .build();
            decededRepository.save(deceded);

        }

        log.info("DecededServiceImpl | addDeceded | Deceded Created");
    }

    @Override
    public DecededResponse getDecededById(long decededId) {
        log.info("DecededServiceImpl | getDecededById is called");
        log.info("DecededServiceImpl | getDecededById | Get the deceded for decededId: {}", decededId);

        Deceded deceded
                = decededRepository.findById(decededId)
                .orElseThrow(
                        () -> new BMCustomException("Deceded with given Id not found", NOT_FOUND));

        DecededResponse decededResponse = new DecededResponse();

        copyProperties(deceded, decededResponse);

        log.info("DecededServiceImpl | getDecededById | decededResponse :" + decededResponse.toString());

        return decededResponse;
    }

    @Override
    public DecededResponse getDecededByConsultationId(long consultationId) {
        log.info("DecededServiceImpl | getDecededByConsultationId is called");
        Deceded deceded = decededRepository.findAllByConsultationId(consultationId).orElseThrow();
        DecededResponse decededResponse = new DecededResponse();
        copyProperties(deceded, decededResponse);

        return decededResponse;
    }

    @Override
    public void editDeceded(DecededRequest decededRequest, long decededId) {
        log.info("DecededServiceImpl | editDeceded is called");

        Deceded deceded
                = decededRepository.findById(decededId)
                .orElseThrow(() -> new BMCustomException(
                        "Deceded with given Id not found",
                        NOT_FOUND
                ));
        deceded.setReanimation(decededRequest.isReanimation());
        deceded.setAfter_out(decededRequest.isAfter_out());
        deceded.setComments(decededRequest.getComments());
        deceded.setDate_op(decededRequest.getDate_op());
        deceded.setMotif(decededRequest.getMotif());
        deceded.setConsultation(consultationRepository.findById(decededRequest.getConsultation_id()).orElseThrow());
        decededRepository.save(deceded);

        log.info("DecededServiceImpl | editDeceded | Deceded Updated");
        log.info("DecededServiceImpl | editDeceded | Deceded Id : " + deceded.getId());
    }

    @Override
    public void deleteDecededById(long decededId) {
        log.info("Deceded id: {}", decededId);

        if (!decededRepository.existsById(decededId)) {
            log.info("Im in this loop {}", !decededRepository.existsById(decededId));
            throw new BMCustomException(
                    "Deceded with given with Id: " + decededId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Deceded with id: {}", decededId);
        decededRepository.deleteById(decededId);
    }
}
