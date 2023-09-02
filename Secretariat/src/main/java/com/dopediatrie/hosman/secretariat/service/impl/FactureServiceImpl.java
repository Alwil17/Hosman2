package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.FactureMode;
import com.dopediatrie.hosman.secretariat.entity.Facture;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.FactureModeRequest;
import com.dopediatrie.hosman.secretariat.payload.request.FactureModeRequest;
import com.dopediatrie.hosman.secretariat.payload.request.FactureRequest;
import com.dopediatrie.hosman.secretariat.payload.response.FactureResponse;
import com.dopediatrie.hosman.secretariat.repository.*;
import com.dopediatrie.hosman.secretariat.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class FactureServiceImpl implements FactureService {
    private final FactureRepository factureRepository;
    private final EtatRepository etatRepository;
    private final ReductionRepository reductionRepository;
    private final MajorationRepository majorationRepository;
    private final CreanceRepository creanceRepository;
    private final ReliquatRepository reliquatRepository;
    private final FactureModeRepository eModeRepository;

    private final ReductionService reductionService;
    private final MajorationService majorationService;
    private final CreanceService creanceService;
    private final ReliquatService reliquatService;
    private final FactureModeService factureModeService;
    private final String NOT_FOUND = "FACTURE_NOT_FOUND";

    @Override
    public List<Facture> getAllFactures() {
        return factureRepository.findAll();
    }

    @Override
    public long addFacture(FactureRequest factureRequest) {
        log.info("FactureServiceImpl | addFacture is called");

        long majorationId = majorationService.addMajoration(factureRequest.getMajoration());
        long reductionId = reductionService.addReduction(factureRequest.getReduction());
        long reliquatId = reliquatService.addReliquat(factureRequest.getReliquat());
        long creanceId = creanceService.addCreance(factureRequest.getCreance());

        Facture facture
                = Facture.builder()
                .reference(factureRequest.getReference())
                .total(factureRequest.getTotal())
                .montant_pec(factureRequest.getMontant_pec())
                .majoration(majorationRepository.findById(majorationId).orElseThrow())
                .reduction(reductionRepository.findById(reductionId).orElseThrow())
                .a_payer(factureRequest.getA_payer())
                .reliquat(reliquatRepository.findById(reliquatId).orElseThrow())
                .creance(creanceRepository.findById(creanceId).orElseThrow())
                .etat(etatRepository.findById(factureRequest.getEtat_id()).orElseThrow())
                .exporte(factureRequest.getExporte())
                .date_facture(factureRequest.getDate_facture())
                .date_reglement(factureRequest.getDate_reglement())
                .build();

        facture = factureRepository.save(facture);

        for (FactureModeRequest eMode : factureRequest.getMode_payements()) {
            eMode.setFacture_id(facture.getId());
            factureModeService.addFactureMode(eMode);
        }

        log.info("FactureServiceImpl | addFacture | Facture Created");
        log.info("FactureServiceImpl | addFacture | Facture Id : " + facture.getId());
        return facture.getId();
    }

    @Override
    public FactureResponse getFactureById(long factureId) {
        log.info("FactureServiceImpl | getFactureById is called");
        log.info("FactureServiceImpl | getFactureById | Get the facture for factureId: {}", factureId);

        Facture facture
                = factureRepository.findById(factureId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Facture with given Id not found", NOT_FOUND));

        FactureResponse factureResponse = new FactureResponse();

        copyProperties(facture, factureResponse);

        log.info("FactureServiceImpl | getFactureById | factureResponse :" + factureResponse.toString());

        return factureResponse;
    }

    @Override
    public void editFacture(FactureRequest factureRequest, long factureId) {
        log.info("FactureServiceImpl | editFacture is called");

        Facture facture
                = factureRepository.findById(factureId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Facture with given Id not found",
                        NOT_FOUND
                ));

        majorationService.editMajoration(factureRequest.getMajoration(), facture.getMajoration().getId());
        reductionService.editReduction(factureRequest.getReduction(), facture.getReduction().getId());
        reliquatService.editReliquat(factureRequest.getReliquat(), facture.getReliquat().getId());
        creanceService.editCreance(factureRequest.getCreance(), facture.getCreance().getId());

        facture.setReference(factureRequest.getReference());
        facture.setTotal(factureRequest.getTotal());
        facture.setMontant_pec(factureRequest.getMontant_pec());
        facture.setA_payer(factureRequest.getA_payer());
        facture.setEtat(etatRepository.findById(factureRequest.getEtat_id()).orElseThrow());
        facture.setExporte(factureRequest.getExporte());
        facture.setDate_facture(factureRequest.getDate_facture());
        facture.setDate_reglement(factureRequest.getDate_reglement());
        factureRepository.save(facture);

        for (FactureModeRequest eMode : factureRequest.getMode_payements()) {
            if(eModeRepository.existsByFacture_IdAndMode_payement_Id(eMode.getFacture_id(), eMode.getMode_payement_id())){
                FactureMode em = eModeRepository.findByFacture_IdAndMode_payement_Id(eMode.getFacture_id(), eMode.getMode_payement_id()).orElseThrow();
                em.setMontant(eMode.getMontant());
                eModeRepository.save(em);
            }else{
                eMode.setFacture_id(facture.getId());
                factureModeService.addFactureMode(eMode);
            }
        }

        log.info("FactureServiceImpl | editFacture | Facture Updated");
        log.info("FactureServiceImpl | editFacture | Facture Id : " + facture.getId());
    }

    @Override
    public void deleteFactureById(long factureId) {
        log.info("Facture id: {}", factureId);

        if (!factureRepository.existsById(factureId)) {
            log.info("Im in this loop {}", !factureRepository.existsById(factureId));
            throw new SecretariatCustomException(
                    "Facture with given with Id: " + factureId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Facture with id: {}", factureId);
        factureRepository.deleteById(factureId);
    }
}
