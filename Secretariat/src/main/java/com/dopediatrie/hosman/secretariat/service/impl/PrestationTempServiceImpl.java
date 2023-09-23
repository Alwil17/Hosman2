package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Patient;
import com.dopediatrie.hosman.secretariat.entity.PrestationTemp;
import com.dopediatrie.hosman.secretariat.entity.Tarif;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.PrestationTempRequest;
import com.dopediatrie.hosman.secretariat.payload.request.PrestationTarifTempRequest;
import com.dopediatrie.hosman.secretariat.payload.response.FactureResponse;
import com.dopediatrie.hosman.secretariat.payload.response.PrestationTempResponse;
import com.dopediatrie.hosman.secretariat.repository.*;
import com.dopediatrie.hosman.secretariat.service.PrestationTempService;
import com.dopediatrie.hosman.secretariat.service.PrestationTarifTempService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class PrestationTempServiceImpl implements PrestationTempService {
    private final PrestationTempRepository prestationTempRepository;
    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;
    private final SecteurRepository secteurRepository;
    private final TarifRepository tarifRepository;

    private final PrestationTarifTempService prestationTarifTempService;
    private final String NOT_FOUND = "PRESTATION_TEMP_NOT_FOUND";

    @Override
    public List<PrestationTemp> getAllPrestationTemps() {
        return prestationTempRepository.findAll();
    }

    @Override
    public FactureResponse addPrestationTemp(PrestationTempRequest prestationTempRequest) {
        log.info("PrestationTempServiceImpl | addPrestationTemp is called");
        Patient patient = patientRepository.findById(prestationTempRequest.getPatient_id()).orElseThrow();

        PrestationTemp prestationTemp
                = PrestationTemp.builder()
                .provenance(prestationTempRequest.getProvenance())
                .patient(patient)
                .consulteur(medecinRepository.findById(prestationTempRequest.getConsulteur_id()).get())
                .demandeur(medecinRepository.findById(prestationTempRequest.getDemandeur_id()).get())
                .secteur(secteurRepository.findById(prestationTempRequest.getSecteur_id()).get())
                .date_prestation(prestationTempRequest.getDate_prestation())
                .build();

        prestationTemp = prestationTempRepository.save(prestationTemp);
        double total_facture = 0;
        // add data to intermediate table
        for (PrestationTarifTempRequest prestationTarifTempRequest : prestationTempRequest.getTarifs()) {
            Tarif tarif = tarifRepository.findById(prestationTarifTempRequest.getTarif_id()).orElseThrow();
            double total_prix_gros = 0;
            switch (patient.getIs_assure()){
                case 0:
                    total_prix_gros = tarif.getTarif_non_assure();
                    break;
                case 1:
                    total_prix_gros = tarif.getTarif_etr_non_assure();
                    break;
                case 2:
                    total_prix_gros = tarif.getTarif_assur_locale();
                    break;
                case 3:
                    total_prix_gros = tarif.getTarif_assur_hors_zone();
                    break;
                default:
                    total_prix_gros = tarif.getTarif_non_assure();
                    break;
            }
            total_prix_gros = total_prix_gros * prestationTarifTempRequest.getQuantite();
            total_facture += total_prix_gros;
            prestationTarifTempRequest.setPrestation_temp_id(prestationTemp.getId());
            prestationTarifTempRequest.setTotal_price_gros(total_prix_gros);
            prestationTarifTempService.addPrestationTarifTemp(prestationTarifTempRequest);
        }

        double montant_pec = 0;
        switch (patient.getIs_assure()){
            case 0:
                montant_pec = 0;
                break;
            case 1:
                montant_pec = 0;
                break;
            default:
                montant_pec = (total_facture * patient.getTaux_assurance() / 100) ;
                break;
        }

        double a_payer = total_facture - montant_pec;

        FactureResponse response = FactureResponse.builder()
                .total(total_facture)
                .montant_pec(montant_pec)
                .a_payer(a_payer)
                .prestation_id(prestationTemp.getId())
                .date_facture(LocalDateTime.now())
                .date_reglement(LocalDateTime.now())
                .build();

        log.info("PrestationTempTempServiceImpl | addPrestationTemp | PrestationTemp Created");
        log.info("PrestationTempTempServiceImpl | addPrestationTemp | PrestationTemp Id : " + prestationTemp.getId());
        return response;
    }

    @Override
    public PrestationTempResponse getPrestationTempById(long prestationTempId) {
        log.info("PrestationTempTempServiceImpl | getPrestationTempById is called");
        log.info("PrestationTempTempServiceImpl | getPrestationTempById | Get the prestationTemp for prestationTempId: {}", prestationTempId);

        PrestationTemp prestationTemp
                = prestationTempRepository.findById(prestationTempId)
                .orElseThrow(
                        () -> new SecretariatCustomException("PrestationTemp with given Id not found", NOT_FOUND));

        PrestationTempResponse prestationTempResponse = new PrestationTempResponse();

        copyProperties(prestationTemp, prestationTempResponse);

        log.info("PrestationTempTempServiceImpl | getPrestationTempById | prestationTempResponse :" + prestationTempResponse.toString());

        return prestationTempResponse;
    }

    @Override
    public void editPrestationTemp(PrestationTempRequest prestationTempRequest, long prestationTempId) {
        log.info("PrestationTempTempServiceImpl | editPrestationTemp is called");

        PrestationTemp prestationTemp
                = prestationTempRepository.findById(prestationTempId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "PrestationTemp with given Id not found",
                        NOT_FOUND
                ));
        prestationTemp.setProvenance(prestationTempRequest.getProvenance());
        prestationTemp.setPatient(patientRepository.findById(prestationTempRequest.getPatient_id()).get());
        prestationTemp.setConsulteur(medecinRepository.findById(prestationTempRequest.getConsulteur_id()).get());
        prestationTemp.setDemandeur(medecinRepository.findById(prestationTempRequest.getDemandeur_id()).get());
        prestationTemp.setSecteur(secteurRepository.findById(prestationTempRequest.getSecteur_id()).get());
        prestationTemp.setDate_prestation(prestationTempRequest.getDate_prestation());
        prestationTempRepository.save(prestationTemp);

        log.info("PrestationTempTempServiceImpl | editPrestationTemp | PrestationTemp Updated");
        log.info("PrestationTempTempServiceImpl | editPrestationTemp | PrestationTemp Id : " + prestationTemp.getId());
    }

    @Override
    public void deletePrestationTempById(long prestationTempId) {
        log.info("PrestationTemp id: {}", prestationTempId);

        if (!prestationTempRepository.existsById(prestationTempId)) {
            log.info("Im in this loop {}", !prestationTempRepository.existsById(prestationTempId));
            throw new SecretariatCustomException(
                    "PrestationTemp with given with Id: " + prestationTempId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting PrestationTemp with id: {}", prestationTempId);
        prestationTempRepository.deleteById(prestationTempId);
    }
}
