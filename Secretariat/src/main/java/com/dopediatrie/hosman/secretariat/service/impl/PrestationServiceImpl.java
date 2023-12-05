package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Patient;
import com.dopediatrie.hosman.secretariat.entity.Prestation;
import com.dopediatrie.hosman.secretariat.entity.Tarif;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.FactureRequest;
import com.dopediatrie.hosman.secretariat.payload.request.PrestationRequest;
import com.dopediatrie.hosman.secretariat.payload.request.PrestationTarifRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PrestationResponse;
import com.dopediatrie.hosman.secretariat.repository.*;
import com.dopediatrie.hosman.secretariat.service.FactureService;
import com.dopediatrie.hosman.secretariat.service.MedecinService;
import com.dopediatrie.hosman.secretariat.service.PrestationService;
import com.dopediatrie.hosman.secretariat.service.PrestationTarifService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class PrestationServiceImpl implements PrestationService {
    private final PrestationRepository prestationRepository;
    private final PatientRepository patientRepository;

    private final MedecinService medecinService;
    private final String NOT_FOUND = "PRESTATION_NOT_FOUND";

    @Override
    public List<Prestation> getAllPrestations() {
        return prestationRepository.findAll();
    }

    @Override
    public long addPrestation(PrestationRequest prestationRequest) {
        log.info("PrestationServiceImpl | addPrestation is called");
        Patient patient = patientRepository.findById(prestationRequest.getPatient_id()).orElseThrow();
        String demandeur = "";
        if(prestationRequest.getDemandeur() != null){
            if(prestationRequest.getDemandeur().getMatricule() != null){
                demandeur = prestationRequest.getDemandeur().getMatricule();
            }else{
                demandeur = medecinService.addMedecin(prestationRequest.getDemandeur());
            }
        }

        Prestation prestation
                = Prestation.builder()
                .provenance(prestationRequest.getProvenance())
                .patient(patient)
                .consulteur(prestationRequest.getConsulteur())
                .date_prestation(prestationRequest.getDate_prestation())
                .secteur_code(prestationRequest.getSecteur_code())
                .build();

        if(demandeur != null && !demandeur.isBlank())
            prestation.setDemandeur(demandeur);

        prestation = prestationRepository.save(prestation);


        // add data to intermediate table
        /*for (PrestationTarifRequest prestationTarifRequest : prestationRequest.getTarifs()) {
            Tarif tarif = tarifRepository.findById(prestationTarifRequest.getTarif_id()).orElseThrow();
            double total_prix = 0;
            switch (patient.getIs_assure()){
                case 0:
                    total_prix = tarif.getTarif_non_assure();
                    break;
                case 1:
                    total_prix = tarif.getTarif_etr_non_assure();
                    break;
                case 2:
                    total_prix = tarif.getTarif_assur_locale();
                    break;
                case 3:
                    total_prix = tarif.getTarif_assur_hors_zone();
                    break;
                default:
                    total_prix = tarif.getTarif_non_assure();
                    break;
            }
            prestationTarifRequest.setPrestation_id(prestation.getId());
            prestationTarifRequest.setTotal_price_gros(total_prix);
            prestationTarifService.addPrestationTarif(prestationTarifRequest);
        }*/

        //add facture
        //FactureRequest fr =  prestationRequest.getFactureRequest();
        //fr.setPatient_id(patient.getId());
        //factureService.addFacture(fr);

        log.info("PrestationServiceImpl | addPrestation | Prestation Created");
        log.info("PrestationServiceImpl | addPrestation | Prestation Id : " + prestation.getId());
        return prestation.getId();
    }

    @Override
    public PrestationResponse getPrestationById(long prestationId) {
        log.info("PrestationServiceImpl | getPrestationById is called");
        log.info("PrestationServiceImpl | getPrestationById | Get the prestation for prestationId: {}", prestationId);

        Prestation prestation
                = prestationRepository.findById(prestationId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Prestation with given Id not found", NOT_FOUND));

        PrestationResponse prestationResponse = new PrestationResponse();

        copyProperties(prestation, prestationResponse);

        log.info("PrestationServiceImpl | getPrestationById | prestationResponse :" + prestationResponse.toString());

        return prestationResponse;
    }

    @Override
    public void editPrestation(PrestationRequest prestationRequest, long prestationId) {
        log.info("PrestationServiceImpl | editPrestation is called");

        Prestation prestation
                = prestationRepository.findById(prestationId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Prestation with given Id not found",
                        NOT_FOUND
                ));
        prestation.setProvenance(prestationRequest.getProvenance());
        prestation.setPatient(patientRepository.findById(prestationRequest.getPatient_id()).get());
        prestation.setConsulteur(prestationRequest.getConsulteur());
        prestation.setDate_prestation(prestationRequest.getDate_prestation());
        prestation.setSecteur_code(prestationRequest.getSecteur_code());
        prestationRepository.save(prestation);

        log.info("PrestationServiceImpl | editPrestation | Prestation Updated");
        log.info("PrestationServiceImpl | editPrestation | Prestation Id : " + prestation.getId());
    }

    @Override
    public void deletePrestationById(long prestationId) {
        log.info("Prestation id: {}", prestationId);

        if (!prestationRepository.existsById(prestationId)) {
            log.info("Im in this loop {}", !prestationRepository.existsById(prestationId));
            throw new SecretariatCustomException(
                    "Prestation with given with Id: " + prestationId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Prestation with id: {}", prestationId);
        prestationRepository.deleteById(prestationId);
    }
}
