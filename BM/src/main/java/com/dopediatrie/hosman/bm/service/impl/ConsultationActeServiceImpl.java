package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.entity.ConsultationActe;
import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.request.ConsultationActeRequest;
import com.dopediatrie.hosman.bm.payload.response.ConsultationActeResponse;
import com.dopediatrie.hosman.bm.repository.ConsultationActeRepository;
import com.dopediatrie.hosman.bm.repository.ConsultationRepository;
import com.dopediatrie.hosman.bm.service.ConsultationActeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class ConsultationActeServiceImpl implements ConsultationActeService {
    private final ConsultationActeRepository consultationActeRepository;
    private final ConsultationRepository consultationRepository;
    private final String NOT_FOUND = "INTERVENTION_ACTE_NOT_FOUND";

    @Override
    public List<ConsultationActe> getAllConsultationActes() {
        return consultationActeRepository.findAll();
    }

    @Override
    public List<ConsultationActe> getAllForConsultation(long id) {
        return consultationActeRepository.findAllByConsultation(id);
    }

    @Override
    public long addConsultationActe(ConsultationActeRequest consultationActeRequest) {
        log.info("ConsultationActeServiceImpl | addConsultationActe is called");

        ConsultationActe consultationActe
                = ConsultationActe.builder()
                .consultation(consultationRepository.findById(consultationActeRequest.getConsultation_id()).orElseThrow())
                .acte(consultationActeRequest.getActe())
                .build();

        consultationActe = consultationActeRepository.save(consultationActe);

        log.info("ConsultationActeServiceImpl | addConsultationActe | ConsultationActe Created");
        log.info("ConsultationActeServiceImpl | addConsultationActe | ConsultationActe Id : " + consultationActe.getId());
        return consultationActe.getId();
    }

    @Override
    public ConsultationActeResponse getConsultationActeById(long consultationActeId) {
        log.info("ConsultationActeServiceImpl | getConsultationActeById is called");
        log.info("ConsultationActeServiceImpl | getConsultationActeById | Get the consultationActe for consultationActeId: {}", consultationActeId);

        ConsultationActe consultationActe
                = consultationActeRepository.findById(consultationActeId)
                .orElseThrow(
                        () -> new BMCustomException("ConsultationActe with given Id not found", NOT_FOUND));

        ConsultationActeResponse consultationActeResponse = new ConsultationActeResponse();

        copyProperties(consultationActe, consultationActeResponse);

        log.info("ConsultationActeServiceImpl | getConsultationActeById | consultationActeResponse :" + consultationActeResponse.toString());

        return consultationActeResponse;
    }

    @Override
    public void editConsultationActe(ConsultationActeRequest consultationActeRequest, long consultationActeId) {
        log.info("ConsultationActeServiceImpl | editConsultationActe is called");

        ConsultationActe consultationActe
                = consultationActeRepository.findById(consultationActeId)
                .orElseThrow(() -> new BMCustomException(
                        "ConsultationActe with given Id not found",
                        NOT_FOUND
                ));
        consultationActe.setConsultation(consultationRepository.findById(consultationActeRequest.getConsultation_id()).orElseThrow());
        consultationActe.setActe(consultationActeRequest.getActe());
        consultationActeRepository.save(consultationActe);

        log.info("ConsultationActeServiceImpl | editConsultationActe | ConsultationActe Updated");
        log.info("ConsultationActeServiceImpl | editConsultationActe | ConsultationActe Id : " + consultationActe.getId());
    }

    @Override
    public void deleteConsultationActeById(long consultationActeId) {
        log.info("ConsultationActe id: {}", consultationActeId);

        if (!consultationActeRepository.existsById(consultationActeId)) {
            log.info("Im in this loop {}", !consultationActeRepository.existsById(consultationActeId));
            throw new BMCustomException(
                    "ConsultationActe with given with Id: " + consultationActeId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting ConsultationActe with id: {}", consultationActeId);
        consultationActeRepository.deleteById(consultationActeId);
    }

    @Override
    public void deleteAllForConsultationId(long consultationId) {
        log.info("deleteAllForConsultationId id: {}", consultationId);
        consultationActeRepository.deleteByConsultationId(consultationId);
    }
}
