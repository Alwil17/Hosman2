package com.dopediatrie.hosman.hospi.service.impl;

import com.dopediatrie.hosman.hospi.entity.SortieDiagnostic;
import com.dopediatrie.hosman.hospi.exception.HospiCustomException;
import com.dopediatrie.hosman.hospi.payload.request.SortieDiagnosticRequest;
import com.dopediatrie.hosman.hospi.payload.response.SortieDiagnosticResponse;
import com.dopediatrie.hosman.hospi.repository.SortieRepository;
import com.dopediatrie.hosman.hospi.repository.SortieDiagnosticRepository;
import com.dopediatrie.hosman.hospi.service.SortieDiagnosticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class SortieDiagnosticServiceImpl implements SortieDiagnosticService {
    private final String NOT_FOUND = "SCAM_NOT_FOUND";

    private final SortieDiagnosticRepository suiviRepository;
    private final SortieRepository sortieRepository;

    @Override
    public List<SortieDiagnostic> getAllSortieDiagnostics() {
        return suiviRepository.findAll();
    }

    @Override
    public long addSortieDiagnostic(SortieDiagnosticRequest suiviRequest) {
        log.info("SortieDiagnosticServiceImpl | addSortieDiagnostic is called");
        SortieDiagnostic suivi = SortieDiagnostic.builder()
                .diagnostic(suiviRequest.getDiagnostic())
                .sortie(sortieRepository.findById(suiviRequest.getSortie_id()).orElseThrow())
                .build();
        suivi = suiviRepository.save(suivi);

        log.info("SortieDiagnosticServiceImpl | addSortieDiagnostic | SortieDiagnostic Created/Updated");
        log.info("SortieDiagnosticServiceImpl | addSortieDiagnostic | SortieDiagnostic Id : " + suivi.getId());
        return suivi.getId();
    }

    @Override
    public void addSortieDiagnostic(List<SortieDiagnosticRequest> suiviRequests) {
        log.info("SortieDiagnosticServiceImpl | addSortieDiagnostic is called");

        for (SortieDiagnosticRequest suiviRequest : suiviRequests) {
            SortieDiagnostic suivi = SortieDiagnostic.builder()
                    .diagnostic(suiviRequest.getDiagnostic())
                    .sortie(sortieRepository.findById(suiviRequest.getSortie_id()).orElseThrow())
                    .build();
            suiviRepository.save(suivi);

        }

        log.info("SortieDiagnosticServiceImpl | addSortieDiagnostic | SortieDiagnostic Created");
    }

    @Override
    public SortieDiagnosticResponse getSortieDiagnosticById(long suiviId) {
        log.info("SortieDiagnosticServiceImpl | getSortieDiagnosticById is called");
        log.info("SortieDiagnosticServiceImpl | getSortieDiagnosticById | Get the suivi for suiviId: {}", suiviId);

        SortieDiagnostic suivi
                = suiviRepository.findById(suiviId)
                .orElseThrow(
                        () -> new HospiCustomException("SortieDiagnostic with given Id not found", NOT_FOUND));

        SortieDiagnosticResponse suiviResponse = new SortieDiagnosticResponse();

        copyProperties(suivi, suiviResponse);

        log.info("SortieDiagnosticServiceImpl | getSortieDiagnosticById | suiviResponse :" + suiviResponse.toString());

        return suiviResponse;
    }

    @Override
    public void editSortieDiagnostic(SortieDiagnosticRequest suiviRequest, long suiviId) {
        log.info("SortieDiagnosticServiceImpl | editSortieDiagnostic is called");

        SortieDiagnostic suivi
                = suiviRepository.findById(suiviId)
                .orElseThrow(() -> new HospiCustomException(
                        "SortieDiagnostic with given Id not found",
                        NOT_FOUND
                ));
        suivi.setDiagnostic(suiviRequest.getDiagnostic());
        suivi.setSortie(sortieRepository.findById(suiviRequest.getSortie_id()).orElseThrow());
        suiviRepository.save(suivi);

        log.info("SortieDiagnosticServiceImpl | editSortieDiagnostic | SortieDiagnostic Updated");
        log.info("SortieDiagnosticServiceImpl | editSortieDiagnostic | SortieDiagnostic Id : " + suivi.getId());
    }

    @Override
    public void deleteSortieDiagnosticById(long suiviId) {
        log.info("SortieDiagnostic id: {}", suiviId);

        if (!suiviRepository.existsById(suiviId)) {
            log.info("Im in this loop {}", !suiviRepository.existsById(suiviId));
            throw new HospiCustomException(
                    "SortieDiagnostic with given with Id: " + suiviId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting SortieDiagnostic with id: {}", suiviId);
        suiviRepository.deleteById(suiviId);
    }

    @Override
    public void deleteAllForSortieId(long sortieId) {
        log.info("deleteAllForSortieId id: {}", sortieId);
        suiviRepository.deleteBySortieId(sortieId);
    }
}
