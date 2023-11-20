package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.entity.Diagnostic;
import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.request.DiagnosticRequest;
import com.dopediatrie.hosman.bm.payload.response.DiagnosticResponse;
import com.dopediatrie.hosman.bm.repository.DiagnosticRepository;
import com.dopediatrie.hosman.bm.service.DiagnosticService;
import com.dopediatrie.hosman.bm.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class DiagnosticServiceImpl implements DiagnosticService {
    private final DiagnosticRepository diagnosticRepository;
    private final String NOT_FOUND = "DIAGNOSTIC_NOT_FOUND";

    @Override
    public List<Diagnostic> getAllDiagnostics() {
        return diagnosticRepository.findAll();
    }

    @Override
    public long addDiagnostic(DiagnosticRequest diagnosticRequest) {
        log.info("DiagnosticServiceImpl | addDiagnostic is called");

        Diagnostic diagnostic
                = Diagnostic.builder()
                .libelle(diagnosticRequest.getLibelle())
                .slug(Str.slug(diagnosticRequest.getLibelle()))
                .build();

        diagnostic = diagnosticRepository.save(diagnostic);

        log.info("DiagnosticServiceImpl | addDiagnostic | Diagnostic Created");
        log.info("DiagnosticServiceImpl | addDiagnostic | Diagnostic Id : " + diagnostic.getId());
        return diagnostic.getId();
    }

    @Override
    public void addDiagnostic(List<DiagnosticRequest> diagnosticRequests) {
        log.info("DiagnosticServiceImpl | addDiagnostic is called");

        for (DiagnosticRequest diagnosticRequest: diagnosticRequests) {
            Diagnostic diagnostic
                    = Diagnostic.builder()
                    .libelle(diagnosticRequest.getLibelle())
                    .slug(Str.slug(diagnosticRequest.getLibelle()))
                    .build();
            diagnosticRepository.save(diagnostic);
        }

        log.info("DiagnosticServiceImpl | addDiagnostic | Diagnostics Created");
    }

    @Override
    public DiagnosticResponse getDiagnosticById(long diagnosticId) {
        log.info("DiagnosticServiceImpl | getDiagnosticById is called");
        log.info("DiagnosticServiceImpl | getDiagnosticById | Get the diagnostic for diagnosticId: {}", diagnosticId);

        Diagnostic diagnostic
                = diagnosticRepository.findById(diagnosticId)
                .orElseThrow(
                        () -> new BMCustomException("Diagnostic with given Id not found", NOT_FOUND));

        DiagnosticResponse diagnosticResponse = new DiagnosticResponse();

        copyProperties(diagnostic, diagnosticResponse);

        log.info("DiagnosticServiceImpl | getDiagnosticById | diagnosticResponse :" + diagnosticResponse.toString());

        return diagnosticResponse;
    }

    @Override
    public void editDiagnostic(DiagnosticRequest diagnosticRequest, long diagnosticId) {
        log.info("DiagnosticServiceImpl | editDiagnostic is called");

        Diagnostic diagnostic
                = diagnosticRepository.findById(diagnosticId)
                .orElseThrow(() -> new BMCustomException(
                        "Diagnostic with given Id not found",
                        NOT_FOUND
                ));
        diagnostic.setLibelle(diagnosticRequest.getLibelle());
        diagnostic.setSlug(Str.slug(diagnosticRequest.getLibelle()));
        diagnosticRepository.save(diagnostic);

        log.info("DiagnosticServiceImpl | editDiagnostic | Diagnostic Updated");
        log.info("DiagnosticServiceImpl | editDiagnostic | Diagnostic Id : " + diagnostic.getId());
    }

    @Override
    public void deleteDiagnosticById(long diagnosticId) {
        log.info("Diagnostic id: {}", diagnosticId);

        if (!diagnosticRepository.existsById(diagnosticId)) {
            log.info("Im in this loop {}", !diagnosticRepository.existsById(diagnosticId));
            throw new BMCustomException(
                    "Diagnostic with given with Id: " + diagnosticId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Diagnostic with id: {}", diagnosticId);
        diagnosticRepository.deleteById(diagnosticId);
    }
}
