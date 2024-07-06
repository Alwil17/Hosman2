package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.entity.Delegue;
import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.request.DelegueRequest;
import com.dopediatrie.hosman.bm.payload.response.DelegueResponse;
import com.dopediatrie.hosman.bm.repository.DelegueRepository;
import com.dopediatrie.hosman.bm.repository.LaboratoireRepository;
import com.dopediatrie.hosman.bm.service.DelegueService;
import com.dopediatrie.hosman.bm.service.LaboratoireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class DelegueServiceImpl implements DelegueService {
    private final String NOT_FOUND = "DELEGUE_NOT_FOUND";

    private final DelegueRepository delegueRepository;
    private final LaboratoireRepository laboratoireRepository;
    private final LaboratoireService laboratoireService;


    @Override
    public List<Delegue> getAllDelegues() {
        return delegueRepository.findAll();
    }

    @Override
    public long addDelegue(DelegueRequest delegueRequest) {
        log.info("DelegueServiceImpl | addDelegue is called");


        Delegue delegue;
        if(delegueRepository.existsByNomAndPrenomsAndTel1AndTel2(delegueRequest.getNom(), delegueRequest.getPrenoms(), delegueRequest.getTel1(), delegueRequest.getTel2())){
            delegue = delegueRepository.findByNomAndPrenomsAndTel1AndTel2(delegueRequest.getNom(), delegueRequest.getPrenoms(), delegueRequest.getTel1(), delegueRequest.getTel2()).orElseThrow();
            editDelegue(delegueRequest, delegue.getId());
        }else{
            long laboratoire_id = delegueRequest.getLaboratoire() != null ? laboratoireService.addLaboratoire(delegueRequest.getLaboratoire()) : 0 ;

            delegue = Delegue.builder()
                    .nom(delegueRequest.getNom())
                    .prenoms(delegueRequest.getPrenoms())
                    .tel1(delegueRequest.getTel1())
                    .tel2(delegueRequest.getTel2())
                    .email(delegueRequest.getEmail())
                    .adresse(delegueRequest.getAdresse())
                    .build();

            if(laboratoire_id != 0)
                delegue.setLaboratoire(laboratoireRepository.findById(laboratoire_id).get());

            delegue = delegueRepository.save(delegue);
        }


        log.info("DelegueServiceImpl | addDelegue | Delegue Created");
        log.info("DelegueServiceImpl | addDelegue | Delegue Id : " + delegue.getId());
        return delegue.getId();
    }

    @Override
    public void addDelegue(List<DelegueRequest> delegueRequests) {
        log.info("DelegueServiceImpl | addDelegue is called");

        for (DelegueRequest delegueRequest: delegueRequests) {
            Delegue delegue;
            if(delegueRepository.existsByNomAndPrenomsAndTel1AndTel2(delegueRequest.getNom(), delegueRequest.getPrenoms(), delegueRequest.getTel1(), delegueRequest.getTel2())){
                delegue = delegueRepository.findByNomAndPrenomsAndTel1AndTel2(delegueRequest.getNom(), delegueRequest.getPrenoms(), delegueRequest.getTel1(), delegueRequest.getTel2()).orElseThrow();
                editDelegue(delegueRequest, delegue.getId());
            }else{
                long laboratoire_id = delegueRequest.getLaboratoire() != null ? laboratoireService.addLaboratoire(delegueRequest.getLaboratoire()) : 0 ;

                delegue = Delegue.builder()
                        .nom(delegueRequest.getNom())
                        .prenoms(delegueRequest.getPrenoms())
                        .tel1(delegueRequest.getTel1())
                        .tel2(delegueRequest.getTel2())
                        .email(delegueRequest.getEmail())
                        .adresse(delegueRequest.getAdresse())
                        .build();

                if(laboratoire_id != 0)
                    delegue.setLaboratoire(laboratoireRepository.findById(laboratoire_id).get());

                delegueRepository.save(delegue);
            }
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
                        () -> new BMCustomException("Delegue with given Id not found", NOT_FOUND));

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
                .orElseThrow(() -> new BMCustomException(
                        "Delegue with given Id not found",
                        NOT_FOUND
                ));
        long laboratoire_id = delegueRequest.getLaboratoire() != null ? laboratoireService.addLaboratoire(delegueRequest.getLaboratoire()) : 0 ;

        delegue.setNom(delegueRequest.getNom());
        delegue.setPrenoms(delegueRequest.getPrenoms());
        delegue.setTel1(delegueRequest.getTel1());
        delegue.setTel2(delegueRequest.getTel2());
        delegue.setEmail(delegueRequest.getEmail());
        delegue.setAdresse(delegueRequest.getAdresse());

        if(laboratoire_id != 0)
            delegue.setLaboratoire(laboratoireRepository.findById(laboratoire_id).get());

        delegueRepository.save(delegue);

        log.info("DelegueServiceImpl | editDelegue | Delegue Updated");
        log.info("DelegueServiceImpl | editDelegue | Delegue Id : " + delegue.getId());
    }

    @Override
    public void deleteDelegueById(long delegueId) {
        log.info("Delegue id: {}", delegueId);

        if (!delegueRepository.existsById(delegueId)) {
            log.info("Im in this loop {}", !delegueRepository.existsById(delegueId));
            throw new BMCustomException(
                    "Delegue with given with Id: " + delegueId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Delegue with id: {}", delegueId);
        delegueRepository.deleteById(delegueId);
    }

    @Override
    public List<Delegue> getDelegueByAgenceId(long agenceId) {
        log.info("DelegueServiceImpl | getDelegueByAgenceId is called");
        return delegueRepository.findDelegueByAgenceId(agenceId);
    }

    @Override
    public List<Delegue> getDelegueByAgenceIdAndQueryString(long agenceId, String q) {
        log.info("DelegueServiceImpl | getDelegueByAgenceIdAndQueryString is called");
        return delegueRepository.findDelegueByAgenceIdAndQueryString(agenceId, q);
    }

    @Override
    public List<Delegue> getDelegueByLaboIdAndQueryString(long laboId, String q) {
        log.info("DelegueServiceImpl | getDelegueByLaboIdAndQueryString is called");
        return delegueRepository.findDelegueByLaboIdAndQueryString(laboId, q);
    }

    @Override
    public List<Delegue> getDelegueByLaboId(long laboId) {
        log.info("DelegueServiceImpl | getDelegueById is called");
        return delegueRepository.findDelegueByLaboId(laboId);
    }

    @Override
    public List<Delegue> getDelegueByQueryString(String q) {
        log.info("DelegueServiceImpl | getDelegueById is called");
        return delegueRepository.findDelegueByQueryString(q);
    }
}
