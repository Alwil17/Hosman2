package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Attente;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.AttenteRequest;
import com.dopediatrie.hosman.secretariat.payload.response.AttenteResponse;
import com.dopediatrie.hosman.secretariat.payload.response.MedecinResponse;
import com.dopediatrie.hosman.secretariat.payload.response.SecteurResponse;
import com.dopediatrie.hosman.secretariat.repository.*;
import com.dopediatrie.hosman.secretariat.service.AttenteService;
import com.dopediatrie.hosman.secretariat.service.MedecinService;
import com.dopediatrie.hosman.secretariat.service.SecteurService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class AttenteServiceImpl implements AttenteService {
    private final AttenteRepository attenteRepository;
    private final PatientRepository patientRepository;
    private final FactureRepository factureRepository;

    private final SecteurService secteurService;
    private final MedecinService medecinService;
    private final String NOT_FOUND = "ATTENTE_NOT_FOUND";

    @Override
    public List<Attente> getAllAttentes() {
        List<Attente> attentes =  attenteRepository.findAll();
        for (Attente attente : attentes) {
            MedecinResponse consulteur = medecinService.getMedecinByMatricule(attente.getMedecin());
            MedecinResponse receveur = medecinService.getMedecinByMatricule(attente.getReceveur());
            SecteurResponse secteur = secteurService.getSecteurByCode(attente.getSecteur_code());
            attente.setMedecin_consulteur(consulteur);
            attente.setMedecin_receveur(receveur);
            attente.setSecteur(secteur);
        }
        return attentes;
    }

    @Override
    public long addAttente(AttenteRequest attenteRequest) {
        log.info("AttenteServiceImpl | addAttente is called");
        long max_attente = attenteRepository.getMaxNumAttente() != null ? attenteRepository.getMaxNumAttente() : 0;
        Attente attente
                = Attente.builder()
                .ordre(0)
                .attente(true)
                .num_attente(max_attente+1)
                .date_attente(attenteRequest.getDate_attente())
                .patient(patientRepository.findById(attenteRequest.getPatient_id()).orElseThrow())
                .medecin(attenteRequest.getMedecin())
                .secteur_code(attenteRequest.getSecteur_code())
                .facture(factureRepository.findById(attenteRequest.getFacture_id()).orElseThrow())
                .urgence(attenteRequest.isUrgence())
                .structure_id(attenteRequest.getStructure_id())
                .build();

        attente = attenteRepository.save(attente);

        log.info("AttenteServiceImpl | addAttente | Attente Created");
        log.info("AttenteServiceImpl | addAttente | Attente Id : " + attente.getId());
        return attente.getId();
    }

    @Override
    public AttenteResponse getAttenteById(long attenteId) {
        log.info("AttenteServiceImpl | getAttenteById is called");
        log.info("AttenteServiceImpl | getAttenteById | Get the attente for attenteId: {}", attenteId);

        Attente attente
                = attenteRepository.findById(attenteId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Attente with given Id not found", NOT_FOUND));

        AttenteResponse attenteResponse = new AttenteResponse();

        copyProperties(attente, attenteResponse);

        log.info("AttenteServiceImpl | getAttenteById | attenteResponse :" + attenteResponse.toString());

        return attenteResponse;
    }

    @Override
    public void editAttente(AttenteRequest attenteRequest, long attenteId) {
        log.info("AttenteServiceImpl | editAttente is called");

        Attente attente
                = attenteRepository.findById(attenteId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Attente with given Id not found",
                        NOT_FOUND
                ));
        attente.setOrdre(0);
        attente.setNum_attente(attenteRepository.getMaxNumAttente()+1);
        attente.setDate_attente(attenteRequest.getDate_attente());
        attente.setAttente(attenteRequest.isAttente());
        attente.setPatient(patientRepository.findById(attenteRequest.getPatient_id()).orElseThrow());
        attente.setMedecin(attenteRequest.getMedecin());
        attente.setReceveur(attenteRequest.getReceveur());
        attente.setSecteur_code(attenteRequest.getSecteur_code());
        attente.setFacture(factureRepository.findById(attenteRequest.getFacture_id()).orElseThrow());
        attente.setUrgence(attenteRequest.isUrgence());
        attente.setStructure_id(attenteRequest.getStructure_id());
        attenteRepository.save(attente);

        log.info("AttenteServiceImpl | editAttente | Attente Updated");
        log.info("AttenteServiceImpl | editAttente | Attente Id : " + attente.getId());
    }

    @Override
    public void deleteAttenteById(long attenteId) {
        log.info("Attente id: {}", attenteId);

        if (!attenteRepository.existsById(attenteId)) {
            log.info("Im in this loop {}", !attenteRepository.existsById(attenteId));
            throw new SecretariatCustomException(
                    "Attente with given with Id: " + attenteId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Attente with id: {}", attenteId);
        attenteRepository.deleteById(attenteId);
    }

    @Override
    public List<Attente> getAttenteForMySecteur(long userId) {
        SecteurResponse sr = secteurService.getSecteurForUser(userId);
        List<Attente> attentes =  attenteRepository.getAttenteBySecteurCode(sr.getCode());
        for (Attente attente : attentes) {
            MedecinResponse consulteur = medecinService.getMedecinByMatricule(attente.getMedecin());
            MedecinResponse receveur = medecinService.getMedecinByMatricule(attente.getReceveur());
            SecteurResponse secteur = secteurService.getSecteurByCode(attente.getSecteur_code());
            attente.setMedecin_consulteur(consulteur);
            attente.setMedecin_receveur(receveur);
            attente.setSecteur(secteur);
        }

        return attentes;
    }

    @Override
    public List<Attente> getAttenteForMe(long userId) {
        MedecinResponse mr = medecinService.getMedecinForUser(userId);
        List<Attente> attentes =  attenteRepository.getAttenteByMedecin(mr.getMatricule());
        for (Attente attente : attentes) {
            MedecinResponse consulteur = medecinService.getMedecinByMatricule(attente.getMedecin());
            MedecinResponse receveur = medecinService.getMedecinByMatricule(attente.getReceveur());
            SecteurResponse secteur = secteurService.getSecteurByCode(attente.getSecteur_code());
            attente.setMedecin_consulteur(consulteur);
            attente.setMedecin_receveur(receveur);
            attente.setSecteur(secteur);
        }
        return attentes;
    }

    @Override
    public List<Attente> getAttenteForMedecin(String medecin_matricule) {
        List<Attente> attentes =  attenteRepository.getAttenteByMedecin(medecin_matricule);
        for (Attente attente : attentes) {
            MedecinResponse consulteur = medecinService.getMedecinByMatricule(attente.getMedecin());
            MedecinResponse receveur = medecinService.getMedecinByMatricule(attente.getReceveur());
            SecteurResponse secteur = secteurService.getSecteurByCode(attente.getSecteur_code());
            attente.setMedecin_consulteur(consulteur);
            attente.setMedecin_receveur(receveur);
            attente.setSecteur(secteur);
        }
        return attentes;
    }
}
