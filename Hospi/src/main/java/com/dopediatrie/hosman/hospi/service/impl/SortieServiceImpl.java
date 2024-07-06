package com.dopediatrie.hosman.hospi.service.impl;

import com.dopediatrie.hosman.hospi.entity.Hospit;
import com.dopediatrie.hosman.hospi.entity.Sortie;
import com.dopediatrie.hosman.hospi.entity.SortieDiagnostic;
import com.dopediatrie.hosman.hospi.exception.HospiCustomException;
import com.dopediatrie.hosman.hospi.payload.request.SortieDiagnosticRequest;
import com.dopediatrie.hosman.hospi.payload.request.SortieRequest;
import com.dopediatrie.hosman.hospi.payload.response.DiagnosticResponse;
import com.dopediatrie.hosman.hospi.payload.response.SortieResponse;
import com.dopediatrie.hosman.hospi.repository.HospitRepository;
import com.dopediatrie.hosman.hospi.repository.SortieRepository;
import com.dopediatrie.hosman.hospi.service.DiagnosticService;
import com.dopediatrie.hosman.hospi.service.HospitService;
import com.dopediatrie.hosman.hospi.service.SortieDiagnosticService;
import com.dopediatrie.hosman.hospi.service.SortieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class SortieServiceImpl implements SortieService {
    private final String NOT_FOUND = "SORTIE_NOT_FOUND";

    private final SortieRepository sortieRepository;
    private final HospitRepository hospitRepository;
    private final HospitService hospitService;
    private final SortieDiagnosticService sortieDiagnosticService;
    private final DiagnosticService diagnosticService;

    @Override
    public List<Sortie> getAllSorties() {
        return sortieRepository.findAll();
    }

    @Override
    public long addSortie(SortieRequest sortieRequest) {
        log.info("SortieServiceImpl | addSortie is called");
        Hospit hospit = hospitRepository.findById(sortieRequest.getHospit_id()).orElseThrow();
        Sortie sortie = Sortie.builder()
                .enceinte(sortieRequest.getEnceinte())
                .date_op(sortieRequest.getDate_op())
                .hospit(hospit)
                .build();
        sortie = sortieRepository.save(sortie);

        hospitService.updateStatus(hospit.getId(), 1);

        if((sortieRequest.getDiagnostics() != null) && (sortieRequest.getDiagnostics().size() > 0)){
            sortieDiagnosticService.deleteAllForSortieId(sortie.getId());
            for (SortieDiagnosticRequest iDiagnostic : sortieRequest.getDiagnostics()) {
                iDiagnostic.setSortie_id(sortie.getId());
                sortieDiagnosticService.addSortieDiagnostic(iDiagnostic);
            }
        }

        log.info("SortieServiceImpl | addSortie | Sortie Created/Updated");
        log.info("SortieServiceImpl | addSortie | Sortie Id : " + sortie.getId());
        return sortie.getId();
    }

    @Override
    public void addSortie(List<SortieRequest> sortieRequests) {
        log.info("SortieServiceImpl | addSortie is called");

        for (SortieRequest sortieRequest : sortieRequests) {
            Sortie sortie = Sortie.builder()
                    .enceinte(sortieRequest.getEnceinte())
                    .date_op(sortieRequest.getDate_op())
                    .hospit(hospitRepository.findById(sortieRequest.getHospit_id()).orElseThrow())
                    .build();
            sortie = sortieRepository.save(sortie);

            if((sortieRequest.getDiagnostics() != null) && (sortieRequest.getDiagnostics().size() > 0)){
                sortieDiagnosticService.deleteAllForSortieId(sortie.getId());
                for (SortieDiagnosticRequest iDiagnostic : sortieRequest.getDiagnostics()) {
                    iDiagnostic.setSortie_id(sortie.getId());
                    sortieDiagnosticService.addSortieDiagnostic(iDiagnostic);
                }
            }
        }

        log.info("SortieServiceImpl | addSortie | Sortie Created");
    }

    @Override
    public SortieResponse getSortieById(long sortieId) {
        log.info("SortieServiceImpl | getSortieById is called");
        log.info("SortieServiceImpl | getSortieById | Get the sortie for sortieId: {}", sortieId);

        Sortie sortie
                = sortieRepository.findById(sortieId)
                .orElseThrow(
                        () -> new HospiCustomException("Sortie with given Id not found", NOT_FOUND));

        SortieResponse sortieResponse = new SortieResponse();
        copyProperties(sortie, sortieResponse);

        if(sortie.getDiagnostics() != null && sortie.getDiagnostics().size() > 0){
            List<DiagnosticResponse> diagnosticResponses = new ArrayList<DiagnosticResponse>();
            for (SortieDiagnostic sortieDiagnostic : sortie.getDiagnostics()) {
                DiagnosticResponse diagnosticResponse = diagnosticService.getDiagnosticByCode(sortieDiagnostic.getDiagnostic());
                diagnosticResponses.add(diagnosticResponse);
            }
            sortieResponse.setDiagnostics(diagnosticResponses);
        }

        log.info("SortieServiceImpl | getSortieById | sortieResponse :" + sortieResponse.toString());

        return sortieResponse;
    }

    @Override
    public List<SortieResponse> getSortieByHospitId(long hospitId) {
        log.info("SortieServiceImpl | getSortieByHospitId is called");
        List<Sortie> sorties = sortieRepository.findAllByHospitId(hospitId);
        List<SortieResponse> sortieResponses = new ArrayList<>();
        for (Sortie s : sorties) {
            SortieResponse sr = new SortieResponse();
            copyProperties(s, sr);
            if(s.getDiagnostics() != null && s.getDiagnostics().size() > 0){
                List<DiagnosticResponse> diagnosticResponses = new ArrayList<DiagnosticResponse>();
                for (SortieDiagnostic sortieDiagnostic : s.getDiagnostics()) {
                    DiagnosticResponse diagnosticResponse = diagnosticService.getDiagnosticByCode(sortieDiagnostic.getDiagnostic());
                    diagnosticResponses.add(diagnosticResponse);
                }
                sr.setDiagnostics(diagnosticResponses);
            }

            sortieResponses.add(sr);
        }
        return sortieResponses;
    }

    @Override
    public void editSortie(SortieRequest sortieRequest, long sortieId) {
        log.info("SortieServiceImpl | editSortie is called");

        Sortie sortie
                = sortieRepository.findById(sortieId)
                .orElseThrow(() -> new HospiCustomException(
                        "Sortie with given Id not found",
                        NOT_FOUND
                ));
        sortie.setEnceinte(sortieRequest.getEnceinte());
        sortie.setDate_op(sortieRequest.getDate_op());
        sortie.setHospit(hospitRepository.findById(sortieRequest.getHospit_id()).orElseThrow());
        sortieRepository.save(sortie);

        log.info("SortieServiceImpl | editSortie | Sortie Updated");
        log.info("SortieServiceImpl | editSortie | Sortie Id : " + sortie.getId());
    }

    @Override
    public void deleteSortieById(long sortieId) {
        log.info("Sortie id: {}", sortieId);

        if (!sortieRepository.existsById(sortieId)) {
            log.info("Im in this loop {}", !sortieRepository.existsById(sortieId));
            throw new HospiCustomException(
                    "Sortie with given with Id: " + sortieId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Sortie with id: {}", sortieId);
        sortieRepository.deleteById(sortieId);
    }
}
