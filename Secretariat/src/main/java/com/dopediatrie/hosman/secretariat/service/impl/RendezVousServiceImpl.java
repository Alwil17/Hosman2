package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Etat;
import com.dopediatrie.hosman.secretariat.entity.RendezVous;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.RendezVousRequest;
import com.dopediatrie.hosman.secretariat.payload.response.AnnuaireResponse;
import com.dopediatrie.hosman.secretariat.payload.response.MedecinResponse;
import com.dopediatrie.hosman.secretariat.payload.response.PatientResponse;
import com.dopediatrie.hosman.secretariat.payload.response.RendezVousResponse;
import com.dopediatrie.hosman.secretariat.repository.EtatRepository;
import com.dopediatrie.hosman.secretariat.repository.RendezVousRepository;
import com.dopediatrie.hosman.secretariat.service.MedecinService;
import com.dopediatrie.hosman.secretariat.service.RendezVousService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class RendezVousServiceImpl implements RendezVousService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String NOT_FOUND = "RDV_NOT_FOUND";

    private final RendezVousRepository rendezVousRepository;
    private final MedecinService medecinService;
    private final EtatRepository etatRepository;


    @Override
    public List<RendezVous> getAllRendezVous() {
        List<RendezVous> rendezVous = rendezVousRepository.findAll();

        return rendezVous.stream().map(rendezVous1 -> {
            MedecinResponse medecin = null;
            if(rendezVous1.getMedecin_ref() != null && !rendezVous1.getMedecin_ref().isBlank()){
                medecin = medecinService.getMedecinByMatricule(rendezVous1.getMedecin_ref());
                rendezVous1.setMedecin(medecin);
            }

            if(rendezVous1.getIntervenant_ref() != null && !rendezVous1.getIntervenant_ref().isBlank()){
                MedecinResponse intervenant;
                if(medecin!= null && medecin.getMatricule().equals(rendezVous1.getIntervenant_ref())){
                    intervenant = medecin;
                }else {
                    intervenant = medecinService.getMedecinByMatricule(rendezVous1.getIntervenant_ref());
                }

                rendezVous1.setIntervenant(intervenant);
            }
            return rendezVous1;
        }).collect(Collectors.toList());
    }

    @Override
    public long addRendezVous(RendezVousRequest rendezVousRequest) {
        log.info("RendezVousServiceImpl | addRendezVous is called");
        LocalDateTime date_redv = LocalDateTime.parse(rendezVousRequest.getDate_rdv()+"T"+rendezVousRequest.getHeure_rdv());
        String etat_string = "programme";
        Etat etat = null;
        if(etatRepository.existsBySlug(etat_string) != null && etatRepository.existsBySlug(etat_string)){
            etat = etatRepository.findBySlug(etat_string).orElseThrow();
        }

        RendezVous rendezVous
                = RendezVous.builder()
                .date_rdv(date_redv)
                .patient_nom(rendezVousRequest.getPatient_nom())
                .patient_prenoms(rendezVousRequest.getPatient_prenoms())
                .patient_sexe(rendezVousRequest.getPatient_sexe())
                .patient_naiss(rendezVousRequest.getPatient_naiss())
                .medecin_ref(rendezVousRequest.getMedecin_ref())
                .intervenant_ref(rendezVousRequest.getIntervenant_ref())
                .objet(rendezVousRequest.getObjet())
                .build();

        if(etat != null)
            rendezVous.setEtat(etat);

        rendezVous = rendezVousRepository.save(rendezVous);

        log.info("RendezVousServiceImpl | addRendezVous | RendezVous Created");
        log.info("RendezVousServiceImpl | addRendezVous | RendezVous Id : " + rendezVous.getId());
        return rendezVous.getId();
    }

    @Override
    public void addRendezVous(List<RendezVousRequest> rendezVousRequests) {
        log.info("RendezVousServiceImpl | addRendezVous is called");

        for (RendezVousRequest rendezVousRequest: rendezVousRequests) {
            LocalDateTime date_redv = LocalDateTime.parse(rendezVousRequest.getDate_rdv()+"T"+rendezVousRequest.getHeure_rdv());
            String etat_string = "programme";
            Etat etat = null;
            if(etatRepository.existsBySlug(etat_string) != null && etatRepository.existsBySlug(etat_string)){
                etat = etatRepository.findBySlug(etat_string).get();
            }

            RendezVous rendezVous
                    = RendezVous.builder()
                    .date_rdv(date_redv)
                    .patient_nom(rendezVousRequest.getPatient_nom())
                    .patient_prenoms(rendezVousRequest.getPatient_prenoms())
                    .patient_sexe(rendezVousRequest.getPatient_sexe())
                    .patient_naiss(rendezVousRequest.getPatient_naiss())
                    .medecin_ref(rendezVousRequest.getMedecin_ref())
                    .intervenant_ref(rendezVousRequest.getIntervenant_ref())
                    .objet(rendezVousRequest.getObjet())
                    .build();

            if(etat != null)
                rendezVous.setEtat(etat);

            rendezVousRepository.save(rendezVous);
        }

        log.info("RendezVousServiceImpl | addRendezVous | RendezVouss Created");
    }

    @Override
    public RendezVousResponse getRendezVousById(long rendezVousId) {
        log.info("RendezVousServiceImpl | getRendezVousById is called");
        log.info("RendezVousServiceImpl | getRendezVousById | Get the rendezVous for rendezVousId: {}", rendezVousId);

        RendezVous rendezVous
                = rendezVousRepository.findById(rendezVousId)
                .orElseThrow(
                        () -> new SecretariatCustomException("RendezVous with given Id not found", NOT_FOUND));

        RendezVousResponse rendezVousResponse = new RendezVousResponse();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        rendezVousResponse = mapper.convertValue(rendezVous, RendezVousResponse.class);


        //copyProperties(rendezVous, rendezVousResponse);

        if(rendezVous.getMedecin_ref() != null && !rendezVous.getMedecin_ref().isBlank()){
            MedecinResponse medecin = medecinService.getMedecinByMatricule(rendezVous.getMedecin_ref());
            rendezVousResponse.setMedecin(medecin);
        }

        if(rendezVous.getIntervenant_ref() != null && !rendezVous.getIntervenant_ref().isBlank()){
            MedecinResponse intervenant = medecinService.getMedecinByMatricule(rendezVous.getIntervenant_ref());
            rendezVousResponse.setIntervenant(intervenant);
        }

        log.info("RendezVousServiceImpl | getRendezVousById | rendezVousResponse :" + rendezVousResponse.toString());

        return rendezVousResponse;
    }

    @Override
    public void editRendezVous(RendezVousRequest rendezVousRequest, long rendezVousId) {
        log.info("RendezVousServiceImpl | editRendezVous is called");

        RendezVous rendezVous
                = rendezVousRepository.findById(rendezVousId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "RendezVous with given Id not found",
                        NOT_FOUND
                ));
        LocalDateTime date_redv = LocalDateTime.parse(rendezVousRequest.getDate_rdv()+"T"+rendezVousRequest.getHeure_rdv());
        rendezVous.setDate_rdv(date_redv);
        rendezVous.setMedecin_ref(rendezVousRequest.getMedecin_ref());
        rendezVous.setIntervenant_ref(rendezVousRequest.getIntervenant_ref());

        rendezVous.setPatient_nom(rendezVousRequest.getPatient_nom());
        rendezVous.setPatient_prenoms(rendezVousRequest.getPatient_prenoms());
        rendezVous.setPatient_sexe(rendezVousRequest.getPatient_sexe());
        rendezVous.setPatient_naiss(rendezVousRequest.getPatient_naiss());
        rendezVous.setObjet(rendezVousRequest.getObjet());
        rendezVousRepository.save(rendezVous);

        log.info("RendezVousServiceImpl | editRendezVous | RendezVous Updated");
        log.info("RendezVousServiceImpl | editRendezVous | RendezVous Id : " + rendezVous.getId());
    }

    @Override
    public void cancelRendezVous(long rendezVousId) {
        log.info("RendezVousServiceImpl | cancelRendezVous is called");

        String etat_string = "annulee";
        Etat etat = null;
        if(etatRepository.existsBySlug(etat_string) != null && etatRepository.existsBySlug(etat_string)){
            etat = etatRepository.findBySlug(etat_string).orElseThrow();
        }

        RendezVous rendezVous
                = rendezVousRepository.findById(rendezVousId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "RendezVous with given Id not found",
                        NOT_FOUND
                ));
        if(etat != null){
            rendezVous.setEtat(etat);
            rendezVousRepository.save(rendezVous);
        }

        log.info("RendezVousServiceImpl | cancelRendezVous | RendezVous Canceled");
        log.info("RendezVousServiceImpl | cancelRendezVous | RendezVous Id : " + rendezVous.getId());
    }

    @Override
    public void deleteRendezVousById(long rendezVousId) {
        log.info("RendezVous id: {}", rendezVousId);

        if (!rendezVousRepository.existsById(rendezVousId)) {
            log.info("Im in this loop {}", !rendezVousRepository.existsById(rendezVousId));
            throw new SecretariatCustomException(
                    "RendezVous with given with Id: " + rendezVousId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting RendezVous with id: {}", rendezVousId);
        rendezVousRepository.deleteById(rendezVousId);
    }

    @Override
    public List<RendezVousResponse> getRendezVousByMedecin(String medRef) {
        log.info("RendezVousServiceImpl | getRendezVousByMedecin is called");
        log.info("RendezVousServiceImpl | getRendezVousByMedecin | Get the rendezVous for medecin: {}", medRef);

        List<RendezVous> rendezVous = rendezVousRepository.findByMedecin(medRef);

        List<RendezVousResponse> rendezVousResponses = new ArrayList<>();

        if(rendezVous != null && rendezVous.size() >0 ){
            for (RendezVous rdv : rendezVous) {
                RendezVousResponse rdvResponse = new RendezVousResponse();
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                rdvResponse = mapper.convertValue(rdv, RendezVousResponse.class);

                //copyProperties(rdv, rdvResponse);

                if(rdv.getMedecin_ref() != null && !rdv.getMedecin_ref().isBlank()){
                    MedecinResponse medecin = medecinService.getMedecinByMatricule(rdv.getMedecin_ref());
                    rdvResponse.setMedecin(medecin);
                }

                if(rdv.getIntervenant_ref() != null && !rdv.getIntervenant_ref().isBlank()){
                    MedecinResponse intervenant = medecinService.getMedecinByMatricule(rdv.getIntervenant_ref());
                    rdvResponse.setIntervenant(intervenant);
                }
                rendezVousResponses.add(rdvResponse);
            }
        }
        return rendezVousResponses;
    }

    @Override
    public List<String> getAllObjets() {
        log.info("RendezVousServiceImpl | getAllObjets is called");
        String sqlquery = "SELECT distinct a.objet as motif FROM `rendez_vous` a ";

        List<String> res = jdbcTemplate.query(sqlquery, (resultSet, rowNum) -> resultSet.getString("motif"));

        return res;
    }

    @Override
    public Map<String, List<RendezVous>> getRendezVousByMedecinAndPeriod(LocalDateTime dateDebut, LocalDateTime dateFin) {
        log.info("RendezVousServiceImpl | getRendezVousByMedecinAndPeriod is called");
        List<RendezVous> rendezVous = rendezVousRepository.groupByMedecinAndDateminAndDatemax(dateDebut, dateFin);

        Map<String, List<RendezVous>> allRendezVous = new HashMap<>();
        for (RendezVous rdv : rendezVous) {
            String medecinName = "";
            if(rdv.getMedecin_ref() != null && !rdv.getMedecin_ref().isBlank()){
                MedecinResponse medecin = medecinService.getMedecinByMatricule(rdv.getMedecin_ref());
                rdv.setMedecin(medecin);
                medecinName = medecin.getNom() + " " + medecin.getPrenoms();
            }

            if(rdv.getIntervenant_ref() != null && !rdv.getIntervenant_ref().isBlank()){
                MedecinResponse intervenant = medecinService.getMedecinByMatricule(rdv.getIntervenant_ref());
                rdv.setIntervenant(intervenant);
                if(medecinName.equals(""))
                    medecinName = intervenant.getNom() + " " + intervenant.getPrenoms();
            }

            if (!allRendezVous.containsKey(medecinName)) {
                allRendezVous.put(medecinName, new ArrayList<>());
            }
            allRendezVous.get(medecinName).add(rdv);
        }
        return allRendezVous;
    }

    @Override
    public Map<String, List<RendezVous>> getRendezVousByMedecinMatAndPeriod(LocalDateTime dateDebut, LocalDateTime dateFin, String matricule) {
        log.info("RendezVousServiceImpl | getRendezVousByMedecinAndPeriod is called");
        List<RendezVous> rendezVous = rendezVousRepository.groupByMedecinAndDateminAndDatemaxAndMatricule(dateDebut, dateFin, matricule);

        Map<String, List<RendezVous>> allRendezVous = new HashMap<>();
        for (RendezVous rdv : rendezVous) {
            String medecinName = "";
            if(rdv.getMedecin_ref() != null && !rdv.getMedecin_ref().isBlank()){
                MedecinResponse medecin = medecinService.getMedecinByMatricule(rdv.getMedecin_ref());
                rdv.setMedecin(medecin);
                medecinName = medecin.getNom() + " " + medecin.getPrenoms();
            }

            if(rdv.getIntervenant_ref() != null && !rdv.getIntervenant_ref().isBlank()){
                MedecinResponse intervenant = medecinService.getMedecinByMatricule(rdv.getIntervenant_ref());
                rdv.setIntervenant(intervenant);
                if(medecinName.equals(""))
                    medecinName = intervenant.getNom() + " " + intervenant.getPrenoms();
            }

            if (!allRendezVous.containsKey(medecinName)) {
                allRendezVous.put(medecinName, new ArrayList<>());
            }
            allRendezVous.get(medecinName).add(rdv);
        }
        return allRendezVous;
    }

    @Override
    public List<RendezVousResponse> getRendezVousByPeriod(LocalDateTime dateDebut, LocalDateTime dateFin) {
        log.info("RendezVousServiceImpl | getRendezVousByPeriod is called");

        List<RendezVous> rendezVous = rendezVousRepository.findByDateMinAndMax(dateDebut, dateFin);

        List<RendezVousResponse> rendezVousResponses = new ArrayList<>();

        if(rendezVous != null && rendezVous.size() >0 ){
            for (RendezVous rdv : rendezVous) {
                RendezVousResponse rdvResponse = new RendezVousResponse();
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                rdvResponse = mapper.convertValue(rdv, RendezVousResponse.class);

                //copyProperties(rdv, rdvResponse);

                if(rdv.getMedecin_ref() != null && !rdv.getMedecin_ref().isBlank()){
                    MedecinResponse medecin = medecinService.getMedecinByMatricule(rdv.getMedecin_ref());
                    rdvResponse.setMedecin(medecin);
                }

                if(rdv.getIntervenant_ref() != null && !rdv.getIntervenant_ref().isBlank()){
                    MedecinResponse intervenant = medecinService.getMedecinByMatricule(rdv.getIntervenant_ref());
                    rdvResponse.setIntervenant(intervenant);
                }
                rendezVousResponses.add(rdvResponse);
            }
        }
        return rendezVousResponses;
    }
}
