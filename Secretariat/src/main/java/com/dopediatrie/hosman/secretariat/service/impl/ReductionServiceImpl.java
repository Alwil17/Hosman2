package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Reduction;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.ReductionRequest;
import com.dopediatrie.hosman.secretariat.payload.response.ReductionResponse;
import com.dopediatrie.hosman.secretariat.repository.FactureRepository;
import com.dopediatrie.hosman.secretariat.repository.ReductionRepository;
import com.dopediatrie.hosman.secretariat.service.ReductionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReductionServiceImpl implements ReductionService {
    private final ReductionRepository reductionRepository;
    private final FactureRepository factureRepository;
    private final String NOT_FOUND = "REDUCTION_NOT_FOUND";

    @Override
    public List<Reduction> getAllReductions() {
        return reductionRepository.findAll();
    }

    @Override
    public long addReduction(ReductionRequest reductionRequest) {
        log.info("ReductionServiceImpl | addReduction is called");

        Reduction reduction;

        if(!reductionRepository.existsByFactureId(reductionRequest.getFacture_id())){
            reduction
                    = Reduction.builder()
                    .montant(reductionRequest.getMontant())
                    .motif(reductionRequest.getMotif())
                    .facture(factureRepository.findById(reductionRequest.getFacture_id()).orElseThrow())
                    .build();

            reduction = reductionRepository.save(reduction);
        }else{
            reduction = reductionRepository.findByFactureId(reductionRequest.getFacture_id()).orElseThrow();
        }

        log.info("ReductionServiceImpl | addReduction | Reduction Created");
        log.info("ReductionServiceImpl | addReduction | Reduction Id : " + reduction.getId());
        return reduction.getId();
    }

    @Override
    public ReductionResponse getReductionById(long reductionId) {
        log.info("ReductionServiceImpl | getReductionById is called");
        log.info("ReductionServiceImpl | getReductionById | Get the reduction for reductionId: {}", reductionId);

        Reduction reduction
                = reductionRepository.findById(reductionId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Reduction with given Id not found", NOT_FOUND));

        ReductionResponse reductionResponse = new ReductionResponse();

        copyProperties(reduction, reductionResponse);

        log.info("ReductionServiceImpl | getReductionById | reductionResponse :" + reductionResponse.toString());

        return reductionResponse;
    }

    @Override
    public void editReduction(ReductionRequest reductionRequest, long reductionId) {
        log.info("ReductionServiceImpl | editReduction is called");

        Reduction reduction
                = reductionRepository.findById(reductionId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Reduction with given Id not found",
                        NOT_FOUND
                ));
        reduction.setMontant(reductionRequest.getMontant());
        reduction.setMotif(reductionRequest.getMotif());
        reductionRepository.save(reduction);

        log.info("ReductionServiceImpl | editReduction | Reduction Updated");
        log.info("ReductionServiceImpl | editReduction | Reduction Id : " + reduction.getId());
    }

    @Override
    public void deleteReductionById(long reductionId) {
        log.info("Reduction id: {}", reductionId);

        if (!reductionRepository.existsById(reductionId)) {
            log.info("Im in this loop {}", !reductionRepository.existsById(reductionId));
            throw new SecretariatCustomException(
                    "Reduction with given with Id: " + reductionId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Reduction with id: {}", reductionId);
        reductionRepository.deleteById(reductionId);
    }
}
