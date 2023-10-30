package com.dopediatrie.hosman.stock.service.impl;

import com.dopediatrie.hosman.stock.entity.Delegue;
import com.dopediatrie.hosman.stock.exception.StockCustomException;
import com.dopediatrie.hosman.stock.payload.request.DelegueRequest;
import com.dopediatrie.hosman.stock.payload.response.DelegueResponse;
import com.dopediatrie.hosman.stock.repository.DelegueRepository;
import com.dopediatrie.hosman.stock.repository.LaboratoireRepository;
import com.dopediatrie.hosman.stock.service.DelegueService;
import com.dopediatrie.hosman.stock.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class DelegueServiceImpl implements DelegueService {
    private final DelegueRepository delegueRepository;
    private final LaboratoireRepository laboratoireRepository;
    private final String NOT_FOUND = "DELEGUE_NOT_FOUND";

    @Override
    public List<Delegue> getAllDelegues() {
        return delegueRepository.findAll();
    }

    @Override
    public long addDelegue(DelegueRequest delegueRequest) {
        log.info("DelegueServiceImpl | addDelegue is called");

        Delegue delegue
                = Delegue.builder()
                .nom(delegueRequest.getNom())
                .prenoms(delegueRequest.getPrenoms())
                .laboratoire(laboratoireRepository.findById(delegueRequest.getLaboratoire_id()).get())
                .tel1(delegueRequest.getTel1())
                .tel2(delegueRequest.getTel2())
                .email(delegueRequest.getEmail())
                .adresse(delegueRequest.getAdresse())
                .build();

        delegue = delegueRepository.save(delegue);

        log.info("DelegueServiceImpl | addDelegue | Delegue Created");
        log.info("DelegueServiceImpl | addDelegue | Delegue Id : " + delegue.getId());
        return delegue.getId();
    }

    @Override
    public void addDelegue(List<DelegueRequest> delegueRequests) {
        log.info("DelegueServiceImpl | addDelegue is called");

        for (DelegueRequest delegueRequest: delegueRequests) {
            Delegue delegue
                    = Delegue.builder()
                    .nom(delegueRequest.getNom())
                    .prenoms(delegueRequest.getPrenoms())
                    .laboratoire(laboratoireRepository.findById(delegueRequest.getLaboratoire_id()).get())
                    .tel1(delegueRequest.getTel1())
                    .tel2(delegueRequest.getTel2())
                    .email(delegueRequest.getEmail())
                    .adresse(delegueRequest.getAdresse())
                    .build();
            delegueRepository.save(delegue);
        }

        log.info("DelegueServiceImpl | addDelegue | Delegues Created");
    }

    @Override
    public DelegueResponse getDelegueById(long delegueId) {
        log.info("DelegueServiceImpl | getDelegueById is called");
        log.info("DelegueServiceImpl | getDelegueById | Get the delegue for delegueId: {}", delegueId);

        Delegue delegue
                = delegueRepository.findById(delegueId)
                .orElseThrow(
                        () -> new StockCustomException("Delegue with given Id not found", NOT_FOUND));

        DelegueResponse delegueResponse = new DelegueResponse();

        copyProperties(delegue, delegueResponse);

        log.info("DelegueServiceImpl | getDelegueById | delegueResponse :" + delegueResponse.toString());

        return delegueResponse;
    }

    @Override
    public void editDelegue(DelegueRequest delegueRequest, long delegueId) {
        log.info("DelegueServiceImpl | editDelegue is called");

        Delegue delegue
                = delegueRepository.findById(delegueId)
                .orElseThrow(() -> new StockCustomException(
                        "Delegue with given Id not found",
                        NOT_FOUND
                ));
        delegue.setNom(delegueRequest.getNom());
        delegue.setPrenoms(delegueRequest.getPrenoms());
        delegue.setLaboratoire(laboratoireRepository.findById(delegueRequest.getLaboratoire_id()).get());
        delegue.setTel1(delegueRequest.getTel1());
        delegue.setTel2(delegueRequest.getTel2());
        delegue.setEmail(delegueRequest.getEmail());
        delegue.setAdresse(delegueRequest.getAdresse());
        delegueRepository.save(delegue);

        log.info("DelegueServiceImpl | editDelegue | Delegue Updated");
        log.info("DelegueServiceImpl | editDelegue | Delegue Id : " + delegue.getId());
    }

    @Override
    public void deleteDelegueById(long delegueId) {
        log.info("Delegue id: {}", delegueId);

        if (!delegueRepository.existsById(delegueId)) {
            log.info("Im in this loop {}", !delegueRepository.existsById(delegueId));
            throw new StockCustomException(
                    "Delegue with given with Id: " + delegueId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Delegue with id: {}", delegueId);
        delegueRepository.deleteById(delegueId);
    }
}
