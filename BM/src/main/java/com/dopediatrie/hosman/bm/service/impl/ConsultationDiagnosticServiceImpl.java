package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.entity.ConsultationDiagnostic;
import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.request.ConsultationDiagnosticRequest;
import com.dopediatrie.hosman.bm.payload.response.ConsultationDiagnosticResponse;
import com.dopediatrie.hosman.bm.repository.ConsultationDiagnosticRepository;
import com.dopediatrie.hosman.bm.repository.ConsultationRepository;
import com.dopediatrie.hosman.bm.service.ConsultationDiagnosticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class ConsultationDiagnosticServiceImpl implements ConsultationDiagnosticService {
    private final ConsultationDiagnosticRepository consultationDiagnosticRepository;
    private final ConsultationRepository consultationRepository;
    private final String NOT_FOUND = "INTERVENTION_MOTIF_NOT_FOUND";

    @Override
    public List<ConsultationDiagnostic> getAllConsultationDiagnostics() {
        return consultationDiagnosticRepository.findAll();
    }

    @Override
    public long addConsultationDiagnostic(ConsultationDiagnosticRequest consultationDiagnosticRequest) {
        log.info("ConsultationDiagnosticServiceImpl | addConsultationDiagnostic is called");

        ConsultationDiagnostic consultationDiagnostic
                = ConsultationDiagnostic.builder()
                .consultation(consultationRepository.findById(consultationDiagnosticRequest.getConsultation_id()).orElseThrow())
                .diagnostic(consultationDiagnosticRequest.getDiagnostic())
                .build();

        consultationDiagnostic = consultationDiagnosticRepository.save(consultationDiagnostic);

        log.info("ConsultationDiagnosticServiceImpl | addConsultationDiagnostic | ConsultationDiagnostic Created");
        log.info("ConsultationDiagnosticServiceImpl | addConsultationDiagnostic | ConsultationDiagnostic Id : " + consultationDiagnostic.getId());
        return consultationDiagnostic.getId();
    }

    @Override
    public ConsultationDiagnosticResponse getConsultationDiagnosticById(long consultationDiagnosticId) {
        log.info("ConsultationDiagnosticServiceImpl | getConsultationDiagnosticById is called");
        log.info("ConsultationDiagnosticServiceImpl | getConsultationDiagnosticById | Get the consultationDiagnostic for consultationDiagnosticId: {}", consultationDiagnosticId);

        ConsultationDiagnostic consultationDiagnostic
                = consultationDiagnosticRepository.findById(consultationDiagnosticId)
                .orElseThrow(
                        () -> new BMCustomException("ConsultationDiagnostic with given Id not found", NOT_FOUND));

        ConsultationDiagnosticResponse consultationDiagnosticResponse = new ConsultationDiagnosticResponse();

        copyProperties(consultationDiagnostic, consultationDiagnosticResponse);

        log.info("ConsultationDiagnosticServiceImpl | getConsultationDiagnosticById | consultationDiagnosticResponse :" + consultationDiagnosticResponse.toString());

        return consultationDiagnosticResponse;
    }

    @Override
    public void editConsultationDiagnostic(ConsultationDiagnosticRequest consultationDiagnosticRequest, long consultationDiagnosticId) {
        log.info("ConsultationDiagnosticServiceImpl | editConsultationDiagnostic is called");

        ConsultationDiagnostic consultationDiagnostic
                = consultationDiagnosticRepository.findById(consultationDiagnosticId)
                .orElseThrow(() -> new BMCustomException(
                        "ConsultationDiagnostic with given Id not found",
                        NOT_FOUND
                ));
        consultationDiagnostic.setConsultation(consultationRepository.findById(consultationDiagnosticRequest.getConsultation_id()).orElseThrow());
        consultationDiagnostic.setDiagnostic(consultationDiagnosticRequest.getDiagnostic());
        consultationDiagnosticRepository.save(consultationDiagnostic);

        log.info("ConsultationDiagnosticServiceImpl | editConsultationDiagnostic | ConsultationDiagnostic Updated");
        log.info("ConsultationDiagnosticServiceImpl | editConsultationDiagnostic | ConsultationDiagnostic Id : " + consultationDiagnostic.getId());
    }

    @Override
    public void deleteConsultationDiagnosticById(long consultationDiagnosticId) {
        log.info("ConsultationDiagnostic id: {}", consultationDiagnosticId);

        if (!consultationDiagnosticRepository.existsById(consultationDiagnosticId)) {
            log.info("Im in this loop {}", !consultationDiagnosticRepository.existsById(consultationDiagnosticId));
            throw new BMCustomException(
                    "ConsultationDiagnostic with given with Id: " + consultationDiagnosticId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting ConsultationDiagnostic with id: {}", consultationDiagnosticId);
        consultationDiagnosticRepository.deleteById(consultationDiagnosticId);
    }

    @Override
    public void deleteAllForConsultationId(long consultationId) {
        log.info("deleteAllForConsultationId id: {}", consultationId);
        consultationDiagnosticRepository.deleteByConsultationId(consultationId);
    }
}
