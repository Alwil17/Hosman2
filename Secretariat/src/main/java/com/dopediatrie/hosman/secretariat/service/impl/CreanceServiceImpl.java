package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Creance;
import com.dopediatrie.hosman.secretariat.entity.Etat;
import com.dopediatrie.hosman.secretariat.entity.FactureMode;
import com.dopediatrie.hosman.secretariat.entity.Reliquat;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.CreanceModeRequest;
import com.dopediatrie.hosman.secretariat.payload.request.CreanceRequest;
import com.dopediatrie.hosman.secretariat.payload.request.FactureModeRequest;
import com.dopediatrie.hosman.secretariat.payload.request.ReliquatRequest;
import com.dopediatrie.hosman.secretariat.payload.response.CreanceResponse;
import com.dopediatrie.hosman.secretariat.repository.*;
import com.dopediatrie.hosman.secretariat.service.CreanceModeService;
import com.dopediatrie.hosman.secretariat.service.CreanceService;
import com.dopediatrie.hosman.secretariat.service.FactureModeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class CreanceServiceImpl implements CreanceService {
    private final CreanceRepository creanceRepository;
    private final EtatRepository etatRepository;
    private final PatientRepository patientRepository;
    private final FactureModeRepository factureModeRepository;
    private final ReliquatRepository reliquatRepository;
    private final CreanceModeService creanceModeService;
    private final FactureModeService factureModeService;
    private final String NOT_FOUND = "CREANCE_NOT_FOUND";

    @Override
    public List<Creance> getAllCreances() {
        List<Creance> creances = creanceRepository.findAllWithPositiveMontant();
        List<Creance> creanceList = new ArrayList<Creance>();
        for (Creance creance : creances) {
            if(creance.getFacture() != null){
                creance.setFacture_ref(creance.getFacture().getReference());
            }
            creanceList.add(creance);
        }
        return creanceList;
    }

    @Override
    public long addCreance(CreanceRequest creanceRequest) {
        log.info("CreanceServiceImpl | addCreance is called");

        Creance creance = Creance.builder()
                .montant(creanceRequest.getMontant())
                .etat(etatRepository.findById(creanceRequest.getEtat_id()).get())
                .date_operation(creanceRequest.getDate_operation())
                .patient(patientRepository.findById(creanceRequest.getPatient_id()).orElseThrow())
                .build();

        creance = creanceRepository.save(creance);

        log.info("CreanceServiceImpl | addCreance | Creance Created");
        log.info("CreanceServiceImpl | addCreance | Creance Id : " + creance.getId());
        return creance.getId();
    }

    @Override
    public CreanceResponse getCreanceById(long creanceId) {
        log.info("CreanceServiceImpl | getCreanceById is called");
        log.info("CreanceServiceImpl | getCreanceById | Get the creance for creanceId: {}", creanceId);

        Creance creance
                = creanceRepository.findById(creanceId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Creance with given Id not found", NOT_FOUND));

        CreanceResponse creanceResponse = new CreanceResponse();
        if(creance.getFacture() != null){
            creanceResponse.setFacture_ref(creance.getFacture().getReference());
        }
        copyProperties(creance, creanceResponse);

        log.info("CreanceServiceImpl | getCreanceById | creanceResponse :" + creanceResponse.toString());

        return creanceResponse;
    }

    @Override
    public List<Creance> getCreanceByDateMinAndMax(LocalDateTime datemin, LocalDateTime datemax) {
        log.info("CreanceServiceImpl | getCreanceByDateMinAndMax is called");
        List<Creance> creances = creanceRepository.getAllByDateminAndDatemax(datemin, datemax);
        List<Creance> creanceList = new ArrayList<Creance>();
        for (Creance creance : creances) {
            if(creance.getFacture() != null){
                creance.setFacture_ref(creance.getFacture().getReference());
            }
            creanceList.add(creance);
        }
        return creanceList;
    }

    @Override
    public List<Creance> getCreanceByDateMinAndMaxAndNom(LocalDateTime datemin, LocalDateTime datemax, String nom) {
        log.info("CreanceServiceImpl | getCreanceByDateMinAndMaxAndNom is called");
        List<Creance> creances = creanceRepository.getAllByDateminAndDatemaxAndNom(datemin, datemax, nom);
        List<Creance> creanceList = new ArrayList<Creance>();
        for (Creance creance : creances) {
            if(creance.getFacture() != null){
                creance.setFacture_ref(creance.getFacture().getReference());
            }
            creanceList.add(creance);
        }
        return creanceList;
    }

    @Override
    public List<Creance> getCreanceByDateMinAndMaxAndReference(LocalDateTime datemin, LocalDateTime datemax, String reference) {
        log.info("CreanceServiceImpl | getCreanceByDateMinAndMaxAndReference is called");
        List<Creance> creances = creanceRepository.getAllByDateminAndDatemaxAndReference(datemin, datemax, reference);
        List<Creance> creanceList = new ArrayList<Creance>();
        for (Creance creance : creances) {
            if(creance.getFacture() != null){
                creance.setFacture_ref(creance.getFacture().getReference());
            }
            creanceList.add(creance);
        }
        return creanceList;
    }

    @Override
    public void editCreance(CreanceRequest creanceRequest, long creanceId) {
        log.info("CreanceServiceImpl | editCreance is called");

        Creance creance
                = creanceRepository.findById(creanceId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Creance with given Id not found",
                        NOT_FOUND
                ));
        // Le montant de la créance change si le montant de la facture change.
        creance.setMontant(creanceRequest.getMontant());
        creance.setEtat(etatRepository.findById(creanceRequest.getEtat_id()).get());
        creance.setDate_operation(creanceRequest.getDate_operation());
        //creance.setDate_reglement(creanceRequest.getDate_reglement());
        creanceRepository.save(creance);

        log.info("CreanceServiceImpl | editCreance | Creance Updated");
        log.info("CreanceServiceImpl | editCreance | Creance Id : " + creance.getId());
    }

    @Override
    public void soldCreance(CreanceRequest creanceRequest, long creanceId) {
        log.info("CreanceServiceImpl | soldCreance is called");

        Creance creance
                = creanceRepository.findById(creanceId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Creance with given Id not found",
                        NOT_FOUND
                ));
        // Le montant de la créance change si le montant de la facture change.
        String etatString = "attente-de-payement";
        double montant_verse = 0;
        if((creanceRequest.getModes() != null) && (creanceRequest.getModes().size() >0)){
            for (CreanceModeRequest cmr : creanceRequest.getModes()) {
                cmr.setCreance_id(creanceId);
                montant_verse += cmr.getMontant();
                creanceModeService.addCreanceMode(cmr);

                Boolean checKFM = factureModeRepository.existsByFacture_IdAndMode_payement_Id(creance.getFacture().getId(), cmr.getMode_payement_id());
                if(checKFM == null || !checKFM){
                    FactureModeRequest factureModeRequest = FactureModeRequest.builder()
                            .facture_id(creance.getFacture().getId())
                            .mode_payement_id(cmr.getMode_payement_id())
                            .montant(cmr.getMontant())
                            .build();
                    factureModeService.addFactureMode(factureModeRequest);
                }else {
                    FactureMode factureMode = factureModeRepository.findByFacture_IdAndMode_payement_Id(creance.getFacture().getId(), cmr.getMode_payement_id()).orElseThrow();
                    factureMode.setMontant(factureMode.getMontant()+cmr.getMontant());
                    factureModeRepository.save(factureMode);
                }
            }
        }

        if(montant_verse <= 0){
            etatString = "attente-de-payement";
            throw new SecretariatCustomException("Il n'y a rien à mettre à jour", "422");
        }else {
            double newAmount = creance.getMontant()-montant_verse;

            if(newAmount > 0){
                creance.setMontant(newAmount);
                etatString = "attente-de-payement";
            }else if(newAmount == 0){
                etatString = "payee";
                creance.setDate_reglement(creanceRequest.getDate_reglement());
                creance.setMontant(0);
            }else {
                creance.setDate_reglement(creanceRequest.getDate_reglement());
                creance.setMontant(0);
                Reliquat reliquat;
                if(reliquatRepository.existsByFactureId(creance.getFacture().getId())){
                    reliquat = reliquatRepository.findByFactureId(creance.getFacture().getId()).orElseThrow();
                    reliquat.setMontant(-newAmount);
                }
                etatString = "payee";
            }

        }
        Etat etat = etatRepository.findBySlug(etatString).orElseThrow();
        creance.setEtat(etat);

        creanceRepository.save(creance);

        log.info("CreanceServiceImpl | soldCreance | Creance Updated");
        log.info("CreanceServiceImpl | soldCreance | Creance Id : " + creance.getId());
    }

    @Override
    public void deleteCreanceById(long creanceId) {
        log.info("Creance id: {}", creanceId);

        if (!creanceRepository.existsById(creanceId)) {
            log.info("Im in this loop {}", !creanceRepository.existsById(creanceId));
            throw new SecretariatCustomException(
                    "Creance with given with Id: " + creanceId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Creance with id: {}", creanceId);
        creanceRepository.deleteById(creanceId);
    }
}
