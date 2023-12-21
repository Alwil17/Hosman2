package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.entity.ConsultationMotif;
import com.dopediatrie.hosman.bm.entity.ConsultationMotifPK;
import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.request.ConsultationMotifRequest;
import com.dopediatrie.hosman.bm.payload.response.ConsultationMotifResponse;
import com.dopediatrie.hosman.bm.repository.ConsultationRepository;
import com.dopediatrie.hosman.bm.repository.ConsultationMotifRepository;
import com.dopediatrie.hosman.bm.repository.MotifRepository;
import com.dopediatrie.hosman.bm.service.ConsultationMotifService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class ConsultationMotifServiceImpl implements ConsultationMotifService {
    private final ConsultationMotifRepository consultationMotifRepository;
    private final ConsultationRepository consultationRepository;
    private final MotifRepository modeRepository;
    private final String NOT_FOUND = "INTERVENTION_MOTIF_NOT_FOUND";

    @Override
    public List<ConsultationMotif> getAllConsultationMotifs() {
        return consultationMotifRepository.findAll();
    }

    @Override
    public ConsultationMotifPK addConsultationMotif(ConsultationMotifRequest consultationMotifRequest) {
        log.info("ConsultationMotifServiceImpl | addConsultationMotif is called");

        ConsultationMotifPK pk = new ConsultationMotifPK();
        pk.consultation_id = consultationMotifRequest.getConsultation_id();
        pk.motif_id = consultationMotifRequest.getMotif_id();

        ConsultationMotif consultationMotif
                = ConsultationMotif.builder()
                .id(pk)
                .consultation(consultationRepository.findById(consultationMotifRequest.getConsultation_id()).orElseThrow())
                .motif(modeRepository.findById(consultationMotifRequest.getMotif_id()).orElseThrow())
                .build();

        consultationMotif = consultationMotifRepository.save(consultationMotif);

        log.info("ConsultationMotifServiceImpl | addConsultationMotif | ConsultationMotif Created");
        log.info("ConsultationMotifServiceImpl | addConsultationMotif | ConsultationMotif Id : " + consultationMotif.getId());
        return consultationMotif.getId();
    }

    @Override
    public ConsultationMotifResponse getConsultationMotifById(long consultationMotifId) {
        log.info("ConsultationMotifServiceImpl | getConsultationMotifById is called");
        log.info("ConsultationMotifServiceImpl | getConsultationMotifById | Get the consultationMotif for consultationMotifId: {}", consultationMotifId);

        ConsultationMotif consultationMotif
                = consultationMotifRepository.findById(consultationMotifId)
                .orElseThrow(
                        () -> new BMCustomException("ConsultationMotif with given Id not found", NOT_FOUND));

        ConsultationMotifResponse consultationMotifResponse = new ConsultationMotifResponse();

        copyProperties(consultationMotif, consultationMotifResponse);

        log.info("ConsultationMotifServiceImpl | getConsultationMotifById | consultationMotifResponse :" + consultationMotifResponse.toString());

        return consultationMotifResponse;
    }

    @Override
    public void editConsultationMotif(ConsultationMotifRequest consultationMotifRequest, long consultationMotifId) {
        log.info("ConsultationMotifServiceImpl | editConsultationMotif is called");

        ConsultationMotif consultationMotif
                = consultationMotifRepository.findById(consultationMotifId)
                .orElseThrow(() -> new BMCustomException(
                        "ConsultationMotif with given Id not found",
                        NOT_FOUND
                ));
        consultationMotif.setConsultation(consultationRepository.findById(consultationMotifRequest.getConsultation_id()).orElseThrow());
        consultationMotif.setMotif(modeRepository.findById(consultationMotifRequest.getMotif_id()).orElseThrow());
        consultationMotifRepository.save(consultationMotif);

        log.info("ConsultationMotifServiceImpl | editConsultationMotif | ConsultationMotif Updated");
        log.info("ConsultationMotifServiceImpl | editConsultationMotif | ConsultationMotif Id : " + consultationMotif.getId());
    }

    @Override
    public void deleteConsultationMotifById(long consultationMotifId) {
        log.info("ConsultationMotif id: {}", consultationMotifId);

        if (!consultationMotifRepository.existsById(consultationMotifId)) {
            log.info("Im in this loop {}", !consultationMotifRepository.existsById(consultationMotifId));
            throw new BMCustomException(
                    "ConsultationMotif with given with Id: " + consultationMotifId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting ConsultationMotif with id: {}", consultationMotifId);
        consultationMotifRepository.deleteById(consultationMotifId);
    }

    @Override
    public void deleteAllForConsultationId(long consultationId) {
        log.info("deleteAllForConsultationId id: {}", consultationId);
        consultationMotifRepository.deleteByConsultationId(consultationId);
    }
}
