package com.dopediatrie.hosman.hospi.service.impl;

import com.dopediatrie.hosman.hospi.entity.Suivi;
import com.dopediatrie.hosman.hospi.exception.HospiCustomException;
import com.dopediatrie.hosman.hospi.payload.request.SuiviRequest;
import com.dopediatrie.hosman.hospi.payload.response.SuiviResponse;
import com.dopediatrie.hosman.hospi.repository.SuiviRepository;
import com.dopediatrie.hosman.hospi.repository.HospitRepository;
import com.dopediatrie.hosman.hospi.service.SuiviService;
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
public class SuiviServiceImpl implements SuiviService {
    private final String NOT_FOUND = "SUIVI_NOT_FOUND";

    private final SuiviRepository suiviRepository;
    private final HospitRepository hospitRepository;

    @Override
    public List<Suivi> getAllSuivis() {
        return suiviRepository.findAll();
    }

    @Override
    public long addSuivi(SuiviRequest suiviRequest) {
        log.info("SuiviServiceImpl | addSuivi is called");
        Suivi suivi;
        if(suiviRepository.existsByApply_dateAndTypeAndType_idAndHospitIdAndExtras(suiviRequest.getApply_date(), suiviRequest.getType(), suiviRequest.getType_id(), suiviRequest.getHospit_id(), suiviRequest.getExtras())){
            suivi = suiviRepository.findByApply_dateAndTypeAndType_idAndHospitIdAndExtras(suiviRequest.getApply_date(), suiviRequest.getType(), suiviRequest.getType_id(), suiviRequest.getHospit_id(), suiviRequest.getExtras()).orElseThrow();
            editSuivi(suiviRequest, suivi.getId());
        }else {
            suivi = Suivi.builder()
                    .type(suiviRequest.getType())
                    .type_id(suiviRequest.getType_id())
                    .qte(suiviRequest.getQte())
                    .apply_date(suiviRequest.getApply_date())
                    .created_at(LocalDateTime.now())
                    .extras(suiviRequest.getExtras())
                    .hospit(hospitRepository.findById(suiviRequest.getHospit_id()).orElseThrow())
                    .build();
            suivi = suiviRepository.save(suivi);
        }

        log.info("SuiviServiceImpl | addSuivi | Suivi Created/Updated");
        log.info("SuiviServiceImpl | addSuivi | Suivi Id : " + suivi.getId());
        return suivi.getId();
    }

    @Override
    public void addSuivi(List<SuiviRequest> suiviRequests) {
        log.info("SuiviServiceImpl | addSuivi is called");

        for (SuiviRequest suiviRequest : suiviRequests) {
            Suivi suivi;
            if(suiviRepository.existsByApply_dateAndTypeAndType_idAndHospitIdAndExtras(suiviRequest.getApply_date(), suiviRequest.getType(), suiviRequest.getType_id(), suiviRequest.getHospit_id(), suiviRequest.getExtras())){
                suivi = suiviRepository.findByApply_dateAndTypeAndType_idAndHospitIdAndExtras(suiviRequest.getApply_date(), suiviRequest.getType(), suiviRequest.getType_id(), suiviRequest.getHospit_id(), suiviRequest.getExtras()).orElseThrow();
                editSuivi(suiviRequest, suivi.getId());
            }else {
                suivi = Suivi.builder()
                        .type(suiviRequest.getType())
                        .type_id(suiviRequest.getType_id())
                        .qte(suiviRequest.getQte())
                        .apply_date(suiviRequest.getApply_date())
                        .created_at(LocalDateTime.now())
                        .extras(suiviRequest.getExtras())
                        .hospit(hospitRepository.findById(suiviRequest.getHospit_id()).orElseThrow())
                        .build();
                suiviRepository.save(suivi);
            }
        }

        log.info("SuiviServiceImpl | addSuivi | Suivi Created");
    }

    @Override
    public SuiviResponse getSuiviById(long suiviId) {
        log.info("SuiviServiceImpl | getSuiviById is called");
        log.info("SuiviServiceImpl | getSuiviById | Get the suivi for suiviId: {}", suiviId);

        Suivi suivi
                = suiviRepository.findById(suiviId)
                .orElseThrow(
                        () -> new HospiCustomException("Suivi with given Id not found", NOT_FOUND));

        SuiviResponse suiviResponse = new SuiviResponse();

        copyProperties(suivi, suiviResponse);

        log.info("SuiviServiceImpl | getSuiviById | suiviResponse :" + suiviResponse.toString());

        return suiviResponse;
    }

    @Override
    public List<SuiviResponse> getSuiviByHospitId(long hospitId) {
        log.info("SuiviServiceImpl | getSuiviByHospitId is called");
        List<Suivi> suivis = suiviRepository.findAllByHospitId(hospitId);
        List<SuiviResponse> suiviResponses = new ArrayList<>();
        for (Suivi s : suivis) {
            SuiviResponse sr = new SuiviResponse();
            copyProperties(s, sr);
            suiviResponses.add(sr);
        }
        return suiviResponses;
    }

    @Override
    public void editSuivi(SuiviRequest suiviRequest, long suiviId) {
        log.info("SuiviServiceImpl | editSuivi is called");

        Suivi suivi
                = suiviRepository.findById(suiviId)
                .orElseThrow(() -> new HospiCustomException(
                        "Suivi with given Id not found",
                        NOT_FOUND
                ));
        suivi.setType(suiviRequest.getType());
        suivi.setType_id(suiviRequest.getType_id());
        suivi.setQte(suiviRequest.getQte());
        suivi.setApply_date(suiviRequest.getApply_date());
        suivi.setUpdated_at(LocalDateTime.now());
        suivi.setExtras(suiviRequest.getExtras());
        if (suiviRequest.getHospit_id() != 0) suivi.setHospit(hospitRepository.findById(suiviRequest.getHospit_id()).orElseThrow());

        suiviRepository.save(suivi);

        log.info("SuiviServiceImpl | editSuivi | Suivi Updated");
        log.info("SuiviServiceImpl | editSuivi | Suivi Id : " + suivi.getId());
    }

    @Override
    public void deleteSuiviById(long suiviId) {
        log.info("Suivi id: {}", suiviId);

        if (!suiviRepository.existsById(suiviId)) {
            log.info("Im in this loop {}", !suiviRepository.existsById(suiviId));
            throw new HospiCustomException(
                    "Suivi with given with Id: " + suiviId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Suivi with id: {}", suiviId);
        suiviRepository.deleteById(suiviId);
    }
}
