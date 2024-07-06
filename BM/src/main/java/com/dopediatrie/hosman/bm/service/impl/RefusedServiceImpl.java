package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.entity.Refused;
import com.dopediatrie.hosman.bm.entity.Refused;
import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.request.RefusedRequest;
import com.dopediatrie.hosman.bm.payload.response.RefusedResponse;
import com.dopediatrie.hosman.bm.payload.response.RefusedResponse;
import com.dopediatrie.hosman.bm.repository.ConsultationRepository;
import com.dopediatrie.hosman.bm.repository.RefusedRepository;
import com.dopediatrie.hosman.bm.service.RefusedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class RefusedServiceImpl implements RefusedService {
    private final String NOT_FOUND = "REFUSED_NOT_FOUND";

    private final RefusedRepository refusedRepository;
    private final ConsultationRepository consultationRepository;

    @Override
    public List<Refused> getAllRefuseds() {
        return refusedRepository.findAll();
    }

    @Override
    public long addRefused(RefusedRequest refusedRequest) {
        log.info("RefusedServiceImpl | addRefused is called");
        Refused refused = Refused.builder()
                .has_ordonnance(refusedRequest.isHas_ordonnance())
                .has_decharge(refusedRequest.isHas_decharge())
                .comments(refusedRequest.getComments())
                .date_op(refusedRequest.getDate_op())
                .motif(refusedRequest.getMotif())
                .consultation(consultationRepository.findById(refusedRequest.getConsultation_id()).orElseThrow())
                .build();
        refused = refusedRepository.save(refused);

        log.info("RefusedServiceImpl | addRefused | Refused Created/Updated");
        log.info("RefusedServiceImpl | addRefused | Refused Id : " + refused.getId());
        return refused.getId();
    }

    @Override
    public void addRefused(List<RefusedRequest> refusedRequests) {
        log.info("RefusedServiceImpl | addRefused is called");

        for (RefusedRequest refusedRequest : refusedRequests) {
            Refused refused = Refused.builder()
                    .has_ordonnance(refusedRequest.isHas_ordonnance())
                    .has_decharge(refusedRequest.isHas_decharge())
                    .comments(refusedRequest.getComments())
                    .date_op(refusedRequest.getDate_op())
                    .motif(refusedRequest.getMotif())
                    .consultation(consultationRepository.findById(refusedRequest.getConsultation_id()).orElseThrow())
                    .build();
            refusedRepository.save(refused);

        }

        log.info("RefusedServiceImpl | addRefused | Refused Created");
    }

    @Override
    public RefusedResponse getRefusedById(long refusedId) {
        log.info("RefusedServiceImpl | getRefusedById is called");
        log.info("RefusedServiceImpl | getRefusedById | Get the refused for refusedId: {}", refusedId);

        Refused refused
                = refusedRepository.findById(refusedId)
                .orElseThrow(
                        () -> new BMCustomException("Refused with given Id not found", NOT_FOUND));

        RefusedResponse refusedResponse = new RefusedResponse();

        copyProperties(refused, refusedResponse);

        log.info("RefusedServiceImpl | getRefusedById | refusedResponse :" + refusedResponse.toString());

        return refusedResponse;
    }

    @Override
    public RefusedResponse getRefusedByConsultationId(long consultationId) {
        log.info("RefusedServiceImpl | getRefusedByConsultationId is called");
        Refused refused = refusedRepository.findAllByConsultationId(consultationId).orElseThrow();
        RefusedResponse refusedResponse = new RefusedResponse();
        copyProperties(refused, refusedResponse);

        return refusedResponse;
    }

    @Override
    public void editRefused(RefusedRequest refusedRequest, long refusedId) {
        log.info("RefusedServiceImpl | editRefused is called");

        Refused refused
                = refusedRepository.findById(refusedId)
                .orElseThrow(() -> new BMCustomException(
                        "Refused with given Id not found",
                        NOT_FOUND
                ));
        refused.setHas_ordonnance(refusedRequest.isHas_ordonnance());
        refused.setHas_decharge(refusedRequest.isHas_decharge());
        refused.setComments(refusedRequest.getComments());
        refused.setDate_op(refusedRequest.getDate_op());
        refused.setMotif(refusedRequest.getMotif());
        refused.setConsultation(consultationRepository.findById(refusedRequest.getConsultation_id()).orElseThrow());
        refusedRepository.save(refused);

        log.info("RefusedServiceImpl | editRefused | Refused Updated");
        log.info("RefusedServiceImpl | editRefused | Refused Id : " + refused.getId());
    }

    @Override
    public void deleteRefusedById(long refusedId) {
        log.info("Refused id: {}", refusedId);

        if (!refusedRepository.existsById(refusedId)) {
            log.info("Im in this loop {}", !refusedRepository.existsById(refusedId));
            throw new BMCustomException(
                    "Refused with given with Id: " + refusedId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Refused with id: {}", refusedId);
        refusedRepository.deleteById(refusedId);
    }
}
