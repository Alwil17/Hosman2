package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Antecedant;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.AntecedantRequest;
import com.dopediatrie.hosman.secretariat.payload.response.AntecedantResponse;
import com.dopediatrie.hosman.secretariat.repository.AntededantRepository;
import com.dopediatrie.hosman.secretariat.service.AntecedantService;
import com.dopediatrie.hosman.secretariat.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class AntecedantServiceImpl implements AntecedantService {
    private final AntededantRepository antecedantRepository;

    private final String NOT_FOUND = "ANTECEDANT_NOT_FOUND";

    @Override
    public List<AntecedantResponse> getAllAntecedants() {
        List<Antecedant> antecedants = antecedantRepository.findAll();
        List<AntecedantResponse> antecedantList = new ArrayList<>();
        if(antecedants.size() >0){
            antecedantList = antecedants.stream().map(antecedant -> {
                AntecedantResponse antecedantResponse = new AntecedantResponse();
                copyProperties(antecedant, antecedantResponse);

                if(antecedant.getHospits() != null && !antecedant.getHospits().isBlank())
                    antecedantResponse.setHospitalisations(Str.convertStringToList(antecedant.getHospits(), "#>"));
                if(antecedant.getChirurs() != null && !antecedant.getChirurs().isBlank())
                    antecedantResponse.setChirurgies(Str.convertStringToList(antecedant.getChirurs(), "#>"));
                if(antecedant.getMedics() != null && !antecedant.getMedics().isBlank())
                    antecedantResponse.setMedicaments(Str.convertStringToList(antecedant.getMedics(), "#>"));

                return antecedantResponse;
            }).collect(Collectors.toList());
        }
        return antecedantList;
    }

    @Override
    public long addAntecedant(AntecedantRequest antecedantRequest) {
        log.info("AntecedantServiceImpl | addAntecedant is called");

        Antecedant antecedant;
        if(antecedantRepository.existsByPatientId(antecedantRequest.getPatient_id())){
            antecedant = antecedantRepository.findByPatientId(antecedantRequest.getPatient_id()).orElseThrow();
            editAntecedant(antecedantRequest, antecedant.getId());
        }else {
            if(antecedantRequest.getType() != null && antecedantRequest.getType().equals("enfant")){
                antecedant = Antecedant.builder()
                        .type(antecedantRequest.getType())
                        .voie_accouch(antecedantRequest.getVoie_accouch())
                        .voie_cause(antecedantRequest.getVoie_cause())
                        .reanime(antecedantRequest.isReanime())
                        .scolarise(antecedantRequest.isScolarise())
                        .hospits(Str.convertListToString(antecedantRequest.getHospitalisations(), "#>"))
                        .classe_scolarise(antecedantRequest.getClasse_scolarise())
                        .build();
            }else{
                antecedant = Antecedant.builder()
                        .type(antecedantRequest.getType())
                        .has_ugd(antecedantRequest.isHas_ugd())
                        .has_hta(antecedantRequest.isHas_hta())
                        .has_asthme(antecedantRequest.isHas_asthme())
                        .type_drepano(antecedantRequest.getType_drepano())
                        .has_alcool(antecedantRequest.isHas_alcool())
                        .nb_tabac(antecedantRequest.getNb_tabac())
                        .mesure_tabac(antecedantRequest.getMesure_tabac())
                        .frequence_tabac(antecedantRequest.getFrequence_tabac())
                        .nb_medic(antecedantRequest.getNb_medic())
                        .medics(Str.convertListToString(antecedantRequest.getMedicaments(), "#>"))
                        .nb_chirurgie(antecedantRequest.getNb_chirurgie())
                        .chirurs(Str.convertListToString(antecedantRequest.getChirurgies(), "#>"))
                        .nb_hospit(antecedantRequest.getNb_hospit())
                        .hospits(Str.convertListToString(antecedantRequest.getHospitalisations(), "#>"))
                        .allergies(antecedantRequest.getAllergies())
                        .autre(antecedantRequest.getAutre())
                        .build();
                if(antecedantRequest.getType_diabete() != null && !antecedantRequest.getType_diabete().isBlank()) {
                    antecedant.setType_diabete(antecedantRequest.getType_diabete());
                    antecedant.setHas_diabete(true);
                }
            }

            antecedant = antecedantRepository.save(antecedant);
        }
        log.info("AntecedantServiceImpl | addAntecedant | Antecedant Created");
        log.info("AntecedantServiceImpl | addAntecedant | Antecedant Id : " + antecedant.getId());
        return antecedant.getId();
    }

    @Override
    public AntecedantResponse getAntecedantById(long antecedantId) {
        log.info("AntecedantServiceImpl | getAntecedantById is called");
        log.info("AntecedantServiceImpl | getAntecedantById | Get the antecedant for antecedantId: {}", antecedantId);

        Antecedant antecedant
                = antecedantRepository.findById(antecedantId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Antecedant with given Id not found", NOT_FOUND));

        AntecedantResponse antecedantResponse = new AntecedantResponse();
        copyProperties(antecedant, antecedantResponse);

        if(antecedant.getHospits() != null && !antecedant.getHospits().isBlank())
            antecedantResponse.setHospitalisations(Str.convertStringToList(antecedant.getHospits(), "#>"));
        if(antecedant.getChirurs() != null && !antecedant.getChirurs().isBlank())
            antecedantResponse.setChirurgies(Str.convertStringToList(antecedant.getChirurs(), "#>"));
        if(antecedant.getMedics() != null && !antecedant.getMedics().isBlank())
            antecedantResponse.setMedicaments(Str.convertStringToList(antecedant.getMedics(), "#>"));


        //log.info("AntecedantServiceImpl | getAntecedantById | antecedantResponse :" + antecedantResponse.toString());

        return antecedantResponse;
    }

    @Override
    public void editAntecedant(AntecedantRequest antecedantRequest, long antecedantId) {
        log.info("AntecedantServiceImpl | editAntecedant is called");
        Antecedant antecedant
                = antecedantRepository.findById(antecedantId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Antecedant with given Id not found",
                        NOT_FOUND
                ));
        if(antecedantRequest.getType() != null && antecedantRequest.getType().equals("enfant")){
            antecedant.setType(antecedantRequest.getType());
            antecedant.setVoie_accouch(antecedantRequest.getVoie_accouch());
            antecedant.setVoie_cause(antecedantRequest.getVoie_cause());
            antecedant.setReanime(antecedantRequest.isReanime());
            antecedant.setScolarise(antecedantRequest.isScolarise());
            antecedant.setClasse_scolarise(antecedantRequest.getClasse_scolarise());
            if(antecedantRequest.getHospitalisations() != null && antecedantRequest.getHospitalisations().size() > 0)
                antecedant.setHospits(Str.convertListToString(antecedantRequest.getHospitalisations(), "#>"));
        }else{
            antecedant.setType(antecedantRequest.getType());
            antecedant.setType_diabete(antecedantRequest.getType_diabete());
            antecedant.setHas_ugd(antecedantRequest.isHas_ugd());
            antecedant.setHas_hta(antecedantRequest.isHas_hta());
            antecedant.setHas_asthme(antecedantRequest.isHas_asthme());
            antecedant.setHas_drepano(antecedantRequest.isHas_drepano());
            antecedant.setType_drepano(antecedantRequest.getType_diabete());
            antecedant.setHas_alcool(antecedantRequest.isHas_alcool());
            antecedant.setNb_tabac(antecedantRequest.getNb_tabac());
            antecedant.setMesure_tabac(antecedantRequest.getMesure_tabac());
            antecedant.setFrequence_tabac(antecedantRequest.getFrequence_tabac());
            antecedant.setNb_medic(antecedantRequest.getNb_medic());
            antecedant.setNb_chirurgie(antecedantRequest.getNb_chirurgie());
            antecedant.setNb_hospit(antecedantRequest.getNb_hospit());
            antecedant.setAllergies(antecedantRequest.getAllergies());
            antecedant.setAutre(antecedantRequest.getAutre());

            if(antecedantRequest.getHospitalisations() != null && antecedantRequest.getHospitalisations().size() > 0)
                antecedant.setHospits(Str.convertListToString(antecedantRequest.getHospitalisations(), "#>"));
            if(antecedantRequest.getChirurgies() != null && antecedantRequest.getChirurgies().size() > 0)
                antecedant.setChirurs(Str.convertListToString(antecedantRequest.getChirurgies(), "#>"));
            if(antecedantRequest.getMedicaments() != null && antecedantRequest.getMedicaments().size() > 0)
                antecedant.setMedics(Str.convertListToString(antecedantRequest.getMedicaments(), "#>"));
        }

        antecedant = antecedantRepository.save(antecedant);

        log.info("AntecedantServiceImpl | editAntecedant | Antecedant Updated");
        log.info("AntecedantServiceImpl | editAntecedant | Antecedant Id : " + antecedant.getId());
    }

    @Override
    public void deleteAntecedantById(long antecedantId) {
        log.info("Antecedant id: {}", antecedantId);

        if (!antecedantRepository.existsById(antecedantId)) {
            log.info("Im in this loop {}", !antecedantRepository.existsById(antecedantId));
            throw new SecretariatCustomException(
                    "Antecedant with given with Id: " + antecedantId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Antecedant with id: {}", antecedantId);
        antecedantRepository.deleteById(antecedantId);
    }
}
