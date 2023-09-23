package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Medecin;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.MedecinRequest;
import com.dopediatrie.hosman.secretariat.payload.response.MedecinResponse;
import com.dopediatrie.hosman.secretariat.repository.*;
import com.dopediatrie.hosman.secretariat.service.MedecinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class MedecinServiceImpl implements MedecinService {
    private final MedecinRepository medecinRepository;
    private final SecteurRepository secteurRepository;
    private final EmployeurRepository employeurRepository;
    private final String NOT_FOUND = "PATIENT_NOT_FOUND";

    @Override
    public List<Medecin> getAllMedecins() {
        return medecinRepository.findAll();
    }

    @Override
    public long addMedecin(MedecinRequest medecinRequest) {
        log.info("MedecinServiceImpl | addMedecin is called");
        long employeur_id = (medecinRequest.getEmployeur_id() != 0) ? employeurRepository.findById(medecinRequest.getEmployeur_id()).get().getId() : 0;

        Medecin medecin
                = Medecin.builder()
                .nom(medecinRequest.getNom())
                .prenoms(medecinRequest.getPrenoms())
                .date_naissance(medecinRequest.getDate_naissance())
                .lieu_naissance(medecinRequest.getLieu_naissance())
                .sexe(medecinRequest.getSexe())
                .tel1(medecinRequest.getTel1())
                .tel2(medecinRequest.getTel2())
                .email(medecinRequest.getEmail())
                .type_piece(medecinRequest.getType_piece())
                .no_piece(medecinRequest.getNo_piece())
                .type(medecinRequest.getType())
                .secteur(secteurRepository.findById(medecinRequest.getSecteur_id()).get())
                .build();

        if(employeur_id != 0)
            medecin.setEmployeur(employeurRepository.findById(employeur_id).get());

        medecin = medecinRepository.save(medecin);

        log.info("MedecinServiceImpl | addMedecin | Medecin Created");
        log.info("MedecinServiceImpl | addMedecin | Medecin Id : " + medecin.getId());
        return medecin.getId();
    }

    @Override
    public void addMedecin(List<MedecinRequest> medecinRequests) {
        log.info("MedecinServiceImpl | addMedecin is called");

        for (MedecinRequest medecinRequest : medecinRequests) {
            Medecin medecin
                    = Medecin.builder()
                    .nom(medecinRequest.getNom())
                    .prenoms(medecinRequest.getPrenoms())
                    .date_naissance(medecinRequest.getDate_naissance())
                    .lieu_naissance(medecinRequest.getLieu_naissance())
                    .sexe(medecinRequest.getSexe())
                    .tel1(medecinRequest.getTel1())
                    .tel2(medecinRequest.getTel2())
                    .email(medecinRequest.getEmail())
                    .type_piece(medecinRequest.getType_piece())
                    .no_piece(medecinRequest.getNo_piece())
                    .type(medecinRequest.getType())
                    .employeur(employeurRepository.findById(medecinRequest.getEmployeur_id()).get())
                    .secteur(secteurRepository.findById(medecinRequest.getSecteur_id()).get())
                    .build();

            medecinRepository.save(medecin);
        }

        log.info("MedecinServiceImpl | addMedecin | Medecin Created");
    }

    @Override
    public MedecinResponse getMedecinById(long medecinId) {
        log.info("MedecinServiceImpl | getMedecinById is called");
        log.info("MedecinServiceImpl | getMedecinById | Get the medecin for medecinId: {}", medecinId);

        Medecin medecin
                = medecinRepository.findById(medecinId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Medecin with given Id not found", NOT_FOUND));

        MedecinResponse medecinResponse = new MedecinResponse();

        copyProperties(medecin, medecinResponse);

        log.info("MedecinServiceImpl | getMedecinById | medecinResponse :" + medecinResponse.toString());

        return medecinResponse;
    }

    @Override
    public List<Medecin> getMedecinByType(String typeMedecin) {
        log.info("MedecinServiceImpl | getMedecinByType is called");
        log.info("MedecinServiceImpl | getMedecinByType | Get the medecin for typeMedecin: {}", typeMedecin);

        List<Medecin> medecins = medecinRepository.findByType(typeMedecin);

        return medecins;
    }

    @Override
    public void editMedecin(MedecinRequest medecinRequest, long medecinId) {
        log.info("MedecinServiceImpl | editMedecin is called");

        Medecin medecin
                = medecinRepository.findById(medecinId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Medecin with given Id not found",
                        NOT_FOUND
                ));
        medecin.setNom(medecinRequest.getNom());
        medecin.setPrenoms(medecinRequest.getPrenoms());
        medecin.setDate_naissance(medecinRequest.getDate_naissance());
        medecin.setLieu_naissance(medecinRequest.getLieu_naissance());
        medecin.setSexe(medecinRequest.getSexe());
        medecin.setTel1(medecinRequest.getTel1());
        medecin.setTel2(medecinRequest.getTel2());
        medecin.setEmail(medecinRequest.getEmail());
        medecin.setType_piece(medecinRequest.getType_piece());
        medecin.setNo_piece(medecinRequest.getNo_piece());
        medecin.setType(medecinRequest.getType());
        medecin.setEmployeur(employeurRepository.findById(medecinRequest.getEmployeur_id()).get());
        medecin.setSecteur(secteurRepository.findById(medecinRequest.getSecteur_id()).get());
        medecinRepository.save(medecin);

        log.info("MedecinServiceImpl | editMedecin | Medecin Updated");
        log.info("MedecinServiceImpl | editMedecin | Medecin Id : " + medecin.getId());
    }

    @Override
    public void deleteMedecinById(long medecinId) {
        log.info("Medecin id: {}", medecinId);

        if (!medecinRepository.existsById(medecinId)) {
            log.info("Im in this loop {}", !medecinRepository.existsById(medecinId));
            throw new SecretariatCustomException(
                    "Medecin with given with Id: " + medecinId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Medecin with id: {}", medecinId);
        medecinRepository.deleteById(medecinId);
    }
}
