package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.entity.Transfered;
import com.dopediatrie.hosman.bm.entity.Transfered;
import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.request.TransferedRequest;
import com.dopediatrie.hosman.bm.payload.response.TransferedResponse;
import com.dopediatrie.hosman.bm.payload.response.TransferedResponse;
import com.dopediatrie.hosman.bm.payload.response.MedecinResponse;
import com.dopediatrie.hosman.bm.repository.TransferedRepository;
import com.dopediatrie.hosman.bm.repository.ConsultationRepository;
import com.dopediatrie.hosman.bm.service.TransferedService;
import com.dopediatrie.hosman.bm.service.MedecinService;
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
public class TransferedServiceImpl implements TransferedService {
    private final String NOT_FOUND = "TRANSFERED_NOT_FOUND";

    private final TransferedRepository transferedRepository;
    private final ConsultationRepository consultationRepository;

    @Override
    public List<Transfered> getAllTransfereds() {
        return transferedRepository.findAll();
    }

    @Override
    public long addTransfered(TransferedRequest transferedRequest) {
        log.info("TransferedServiceImpl | addTransfered is called");
        Transfered transfered = Transfered.builder()
                .specialite(transferedRequest.getSpecialite())
                .destination(transferedRequest.getDestination())
                .comments(transferedRequest.getComments())
                .accompagne(transferedRequest.getAccompagne())
                .motif(transferedRequest.getMotif())
                .transport(transferedRequest.getTransport())
                .date_op(transferedRequest.getDate_op())
                .consultation(consultationRepository.findById(transferedRequest.getConsultation_id()).orElseThrow())
                .build();
        transfered = transferedRepository.save(transfered);

        log.info("TransferedServiceImpl | addTransfered | Transfered Created/Updated");
        log.info("TransferedServiceImpl | addTransfered | Transfered Id : " + transfered.getId());
        return transfered.getId();
    }

    @Override
    public void addTransfered(List<TransferedRequest> transferedRequests) {
        log.info("TransferedServiceImpl | addTransfered is called");

        for (TransferedRequest transferedRequest : transferedRequests) {
            Transfered transfered = Transfered.builder()
                    .specialite(transferedRequest.getSpecialite())
                    .destination(transferedRequest.getDestination())
                    .comments(transferedRequest.getComments())
                    .accompagne(transferedRequest.getAccompagne())
                    .motif(transferedRequest.getMotif())
                    .transport(transferedRequest.getTransport())
                    .date_op(transferedRequest.getDate_op())
                    .consultation(consultationRepository.findById(transferedRequest.getConsultation_id()).orElseThrow())
                    .build();
            transferedRepository.save(transfered);
        }

        log.info("TransferedServiceImpl | addTransfered | Transfered Created");
    }

    @Override
    public TransferedResponse getTransferedById(long transferedId) {
        log.info("TransferedServiceImpl | getTransferedById is called");
        log.info("TransferedServiceImpl | getTransferedById | Get the transfered for transferedId: {}", transferedId);

        Transfered transfered
                = transferedRepository.findById(transferedId)
                .orElseThrow(
                        () -> new BMCustomException("Transfered with given Id not found", NOT_FOUND));

        TransferedResponse transferedResponse = new TransferedResponse();

        copyProperties(transfered, transferedResponse);

        log.info("TransferedServiceImpl | getTransferedById | transferedResponse :" + transferedResponse.toString());

        return transferedResponse;
    }

    @Override
    public TransferedResponse getTransferedByConsultationId(long consultationId) {
        log.info("TransferedServiceImpl | getTransferedByConsultationId is called");
        Transfered transfered = transferedRepository.findAllByConsultationId(consultationId).orElseThrow();
        TransferedResponse transferedResponse = new TransferedResponse();
        copyProperties(transfered, transferedResponse);

        return transferedResponse;
    }

    @Override
    public void editTransfered(TransferedRequest transferedRequest, long transferedId) {
        log.info("TransferedServiceImpl | editTransfered is called");

        Transfered transfered
                = transferedRepository.findById(transferedId)
                .orElseThrow(() -> new BMCustomException(
                        "Transfered with given Id not found",
                        NOT_FOUND
                ));
        transfered.setSpecialite(transferedRequest.getSpecialite());
        transfered.setDestination(transferedRequest.getDestination());
        transfered.setComments(transferedRequest.getComments());
        transfered.setAccompagne(transferedRequest.getAccompagne());
        transfered.setMotif(transferedRequest.getMotif());
        transfered.setTransport(transferedRequest.getTransport());
        transfered.setDate_op(transferedRequest.getDate_op());
        transfered.setConsultation(consultationRepository.findById(transferedRequest.getConsultation_id()).orElseThrow());

        transferedRepository.save(transfered);

        log.info("TransferedServiceImpl | editTransfered | Transfered Updated");
        log.info("TransferedServiceImpl | editTransfered | Transfered Id : " + transfered.getId());
    }

    @Override
    public void deleteTransferedById(long transferedId) {
        log.info("Transfered id: {}", transferedId);

        if (!transferedRepository.existsById(transferedId)) {
            log.info("Im in this loop {}", !transferedRepository.existsById(transferedId));
            throw new BMCustomException(
                    "Transfered with given with Id: " + transferedId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Transfered with id: {}", transferedId);
        transferedRepository.deleteById(transferedId);
    }
}
