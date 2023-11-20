package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.PEC;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.PECRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PECResponse;
import com.dopediatrie.hosman.secretariat.repository.*;
import com.dopediatrie.hosman.secretariat.service.PECService;
import com.dopediatrie.hosman.secretariat.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class PECServiceImpl implements PECService {
    private final PECRepository pecRepository;
    private final AssuranceRepository assuranceRepository;
    private final FactureRepository factureRepository;
    private final ActeRepository acteRepository;
    private final PatientRepository patientRepository;
    private final String NOT_FOUND = "PEC_NOT_FOUND";

    @Override
    public List<PEC> getAllPECs() {
        return pecRepository.findAll();
    }

    @Override
    public long addPEC(PECRequest pecRequest) {
        log.info("PECServiceImpl | addPEC is called");

        PEC pec
                = PEC.builder()
                .assurance(assuranceRepository.findById(pecRequest.getAssurance_id()).orElseThrow())
                .facture(factureRepository.findById(pecRequest.getFacture_id()).orElseThrow())
                .acte(acteRepository.findById(pecRequest.getActe_id()).orElseThrow())
                .facture(factureRepository.findById(pecRequest.getFacture_id()).orElseThrow())
                .patient(patientRepository.findById(pecRequest.getPatient_id()).orElseThrow())
                .montant_pec(pecRequest.getMontant_pec())
                .build();

        pec = pecRepository.save(pec);

        log.info("PECServiceImpl | addPEC | PEC Created");
        log.info("PECServiceImpl | addPEC | PEC Id : " + pec.getId());
        return pec.getId();
    }

    @Override
    public void addPEC(List<PECRequest> patientRequests) {
        log.info("PECServiceImpl | addPEC is called");

        for (PECRequest pecRequest : patientRequests) {
            PEC pec
                    = PEC.builder()
                    .assurance(assuranceRepository.findById(pecRequest.getAssurance_id()).orElseThrow())
                    .facture(factureRepository.findById(pecRequest.getFacture_id()).orElseThrow())
                    .acte(acteRepository.findById(pecRequest.getActe_id()).orElseThrow())
                    .facture(factureRepository.findById(pecRequest.getFacture_id()).orElseThrow())
                    .patient(patientRepository.findById(pecRequest.getPatient_id()).orElseThrow())
                    .montant_pec(pecRequest.getMontant_pec())
                    .build();

            pecRepository.save(pec);
        }

        log.info("PECServiceImpl | addPEC | PEC Created");
    }

    @Override
    public PECResponse getPECById(long pecId) {
        log.info("PECServiceImpl | getPECById is called");
        log.info("PECServiceImpl | getPECById | Get the pec for pecId: {}", pecId);

        PEC pec
                = pecRepository.findById(pecId)
                .orElseThrow(
                        () -> new SecretariatCustomException("PEC with given Id not found", NOT_FOUND));

        PECResponse pecResponse = new PECResponse();

        copyProperties(pec, pecResponse);

        log.info("PECServiceImpl | getPECById | pecResponse :" + pecResponse.toString());

        return pecResponse;
    }

    @Override
    public void editPEC(PECRequest pecRequest, long pecId) {
        log.info("PECServiceImpl | editPEC is called");

        PEC pec
                = pecRepository.findById(pecId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "PEC with given Id not found",
                        NOT_FOUND
                ));
        pec.setAssurance(assuranceRepository.findById(pecRequest.getAssurance_id()).orElseThrow());
        pec.setFacture(factureRepository.findById(pecRequest.getFacture_id()).orElseThrow());
        pec.setActe(acteRepository.findById(pecRequest.getActe_id()).orElseThrow());
        pec.setFacture(factureRepository.findById(pecRequest.getFacture_id()).orElseThrow());
        pec.setPatient(patientRepository.findById(pecRequest.getPatient_id()).orElseThrow());
        pec.setMontant_pec(pecRequest.getMontant_pec());
        pecRepository.save(pec);

        log.info("PECServiceImpl | editPEC | PEC Updated");
        log.info("PECServiceImpl | editPEC | PEC Id : " + pec.getId());
    }

    @Override
    public void deletePECById(long pecId) {
        log.info("PEC id: {}", pecId);

        if (!pecRepository.existsById(pecId)) {
            log.info("Im in this loop {}", !pecRepository.existsById(pecId));
            throw new SecretariatCustomException(
                    "PEC with given with Id: " + pecId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting PEC with id: {}", pecId);
        pecRepository.deleteById(pecId);
    }
}
