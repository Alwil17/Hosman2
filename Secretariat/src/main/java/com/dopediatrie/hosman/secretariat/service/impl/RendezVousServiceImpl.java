package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Etat;
import com.dopediatrie.hosman.secretariat.entity.RendezVous;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.RendezVousRequest;
import com.dopediatrie.hosman.secretariat.payload.response.RendezVousResponse;
import com.dopediatrie.hosman.secretariat.repository.EtatRepository;
import com.dopediatrie.hosman.secretariat.repository.MedecinRepository;
import com.dopediatrie.hosman.secretariat.repository.PatientRepository;
import com.dopediatrie.hosman.secretariat.repository.RendezVousRepository;
import com.dopediatrie.hosman.secretariat.service.RendezVousService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class RendezVousServiceImpl implements RendezVousService {
    private final RendezVousRepository rendezVousRepository;
    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;
    private final EtatRepository etatRepository;
    private final String NOT_FOUND = "RDV_NOT_FOUND";

    @Override
    public List<RendezVous> getAllRendezVous() {
        return rendezVousRepository.findAll();
    }

    @Override
    public long addRendezVous(RendezVousRequest rendezVousRequest) {
        log.info("RendezVousServiceImpl | addRendezVous is called");

        LocalDateTime date_redv = LocalDateTime.parse(rendezVousRequest.getDate_rdv()+"T"+rendezVousRequest.getHeure_rdv());
        String etat_string = "programmee";
        Etat etat = null;
        if(etatRepository.existsBySlug(etat_string) == null || !etatRepository.existsBySlug(etat_string)){
            etat = etatRepository.findBySlug(etat_string).get();
        }

        RendezVous rendezVous
                = RendezVous.builder()
                .date_rdv(date_redv)
                .medecin(medecinRepository.findById(rendezVousRequest.getMedecin_id()).get())
                .patient(patientRepository.findById(rendezVousRequest.getPatient_id()).get())
                .objet(rendezVousRequest.getObjet())
                .build();

        if (rendezVousRequest.getIntervenant_id() != 0)
            rendezVous.setIntervenant(medecinRepository.findById(rendezVousRequest.getIntervenant_id()).get());
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
            String etat_string = "programmee";
            Etat etat = null;
            if(etatRepository.existsBySlug(etat_string) == null || !etatRepository.existsBySlug(etat_string)){
                etat = etatRepository.findBySlug(etat_string).get();
            }

            RendezVous rendezVous
                    = RendezVous.builder()
                    .date_rdv(date_redv)
                    .medecin(medecinRepository.findById(rendezVousRequest.getMedecin_id()).get())
                    .patient(patientRepository.findById(rendezVousRequest.getPatient_id()).get())
                    .objet(rendezVousRequest.getObjet())
                    .build();

            if (rendezVousRequest.getIntervenant_id() != 0)
                rendezVous.setIntervenant(medecinRepository.findById(rendezVousRequest.getIntervenant_id()).get());
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

        copyProperties(rendezVous, rendezVousResponse);

        log.info("RendezVousServiceImpl | getRendezVousById | rendezVousResponse :" + rendezVousResponse.toString());

        return rendezVousResponse;
    }

    @Override
    public void editRendezVous(RendezVousRequest rendezVousRequest, long rendezVousId) {
        log.info("RendezVousServiceImpl | editRendezVous is called");

        LocalDateTime date_redv = LocalDateTime.parse(rendezVousRequest.getDate_rdv()+"T"+rendezVousRequest.getHeure_rdv());

        RendezVous rendezVous
                = rendezVousRepository.findById(rendezVousId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "RendezVous with given Id not found",
                        NOT_FOUND
                ));
        rendezVous.setDate_rdv(date_redv);
        rendezVous.setMedecin(medecinRepository.findById(rendezVousRequest.getMedecin_id()).get());
        rendezVous.setPatient(patientRepository.findById(rendezVousRequest.getPatient_id()).get());
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
        if(etatRepository.existsBySlug(etat_string) == null || !etatRepository.existsBySlug(etat_string)){
            etat = etatRepository.findBySlug(etat_string).get();
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
}
