package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.FactureMode;
import com.dopediatrie.hosman.secretariat.entity.FactureModePK;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.FactureModeRequest;
import com.dopediatrie.hosman.secretariat.payload.response.FactureModeResponse;
import com.dopediatrie.hosman.secretariat.repository.FactureModeRepository;
import com.dopediatrie.hosman.secretariat.repository.FactureRepository;
import com.dopediatrie.hosman.secretariat.repository.ModePayementRepository;
import com.dopediatrie.hosman.secretariat.service.FactureModeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class FactureModeServiceImpl implements FactureModeService {
    private final FactureModeRepository factureModeRepository;
    private final FactureRepository factureRepository;
    private final ModePayementRepository modeRepository;
    private final String NOT_FOUND = "FACTURE_MODE_NOT_FOUND";

    @Override
    public List<FactureMode> getAllFactureModes() {
        return factureModeRepository.findAll();
    }

    @Override
    public FactureModePK addFactureMode(FactureModeRequest factureModeRequest) {
        log.info("FactureModeServiceImpl | addFactureMode is called");

        FactureModePK pk = new FactureModePK();
        pk.facture_id = factureModeRequest.getFacture_id();
        pk.mode_payement_id = factureModeRequest.getMode_payement_id();

        FactureMode factureMode
                = FactureMode.builder()
                .id(pk)
                .facture(factureRepository.findById(factureModeRequest.getFacture_id()).orElseThrow())
                .mode_payement(modeRepository.findById(factureModeRequest.getMode_payement_id()).orElseThrow())
                .montant(factureModeRequest.getMontant())
                .build();

        factureMode = factureModeRepository.save(factureMode);

        log.info("FactureModeServiceImpl | addFactureMode | FactureMode Created");
        log.info("FactureModeServiceImpl | addFactureMode | FactureMode Id : " + factureMode.getId());
        return factureMode.getId();
    }

    @Override
    public FactureModeResponse getFactureModeById(long factureModeId) {
        log.info("FactureModeServiceImpl | getFactureModeById is called");
        log.info("FactureModeServiceImpl | getFactureModeById | Get the factureMode for factureModeId: {}", factureModeId);

        FactureMode factureMode
                = factureModeRepository.findById(factureModeId)
                .orElseThrow(
                        () -> new SecretariatCustomException("FactureMode with given Id not found", NOT_FOUND));

        FactureModeResponse factureModeResponse = new FactureModeResponse();

        copyProperties(factureMode, factureModeResponse);

        log.info("FactureModeServiceImpl | getFactureModeById | factureModeResponse :" + factureModeResponse.toString());

        return factureModeResponse;
    }

    @Override
    public void editFactureMode(FactureModeRequest factureModeRequest, long factureModeId) {
        log.info("FactureModeServiceImpl | editFactureMode is called");

        FactureMode factureMode
                = factureModeRepository.findById(factureModeId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "FactureMode with given Id not found",
                        NOT_FOUND
                ));
        factureMode.setFacture(factureRepository.findById(factureModeRequest.getFacture_id()).orElseThrow());
        factureMode.setMode_payement(modeRepository.findById(factureModeRequest.getMode_payement_id()).orElseThrow());
        factureMode.setMontant(factureModeRequest.getMontant());
        factureModeRepository.save(factureMode);

        log.info("FactureModeServiceImpl | editFactureMode | FactureMode Updated");
        log.info("FactureModeServiceImpl | editFactureMode | FactureMode Id : " + factureMode.getId());
    }

    @Override
    public void deleteFactureModeById(long factureModeId) {
        log.info("FactureMode id: {}", factureModeId);

        if (!factureModeRepository.existsById(factureModeId)) {
            log.info("Im in this loop {}", !factureModeRepository.existsById(factureModeId));
            throw new SecretariatCustomException(
                    "FactureMode with given with Id: " + factureModeId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting FactureMode with id: {}", factureModeId);
        factureModeRepository.deleteById(factureModeId);
    }
}
