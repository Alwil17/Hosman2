package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Caisse;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.response.CaisseResponse;
import com.dopediatrie.hosman.secretariat.repository.CaisseRepository;
import com.dopediatrie.hosman.secretariat.service.CaisseService;
import com.dopediatrie.hosman.secretariat.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class CaisseServiceImpl implements CaisseService {
    private final CaisseRepository caisseRepository;
    private final String NOT_FOUND = "CAISSE_NOT_FOUND";

    @Override
    public CaisseResponse getCurrentCaisse() {
        Date dt = new Date();
        LocalDateTime today = LocalDateTime.from(dt.toInstant().atZone(ZoneId.of("UTC")));
        LocalDateTime tomorrow = LocalDateTime.from(dt.toInstant().atZone(ZoneId.of("UTC"))).plusDays(1);
        String libelle = "Caisse_"+ today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Caisse caisse;

        if(caisseRepository.existsByLibelle(libelle) == null || !caisseRepository.existsByLibelle(libelle)){
            caisse = addCaisse(0);
        }else {
            caisse = caisseRepository.findByLibelle(libelle).get();
        }

        CaisseResponse caisseResponse = new CaisseResponse();
        copyProperties(caisse, caisseResponse);
        return caisseResponse;
    }

    @Override
    public Caisse addCaisse(double amount) {
        log.info("CaisseServiceImpl | addCaisse is called");

        Date dt = new Date();
        LocalDateTime today = LocalDateTime.from(dt.toInstant().atZone(ZoneId.of("UTC")));
        LocalDateTime tomorrow = LocalDateTime.from(dt.toInstant().atZone(ZoneId.of("UTC"))).plusDays(1);
        String libelle = "Caisse_"+ today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        if(caisseRepository.existsByOuvert(true) != null && caisseRepository.existsByOuvert(true)){
            Caisse current = caisseRepository.findByOuvert(true).get();
            current.setOuvert(false);
            current.setDate_fermeture(LocalDateTime.now());
            caisseRepository.save(current);
        }

        Caisse caisse
                = Caisse.builder()
                .libelle(libelle)
                .slug(Str.slug(libelle))
                .date_ouverture(today)
                .montant(amount)
                .ouvert(true)
                .build();

        caisse = caisseRepository.save(caisse);

        log.info("CaisseServiceImpl | addCaisse | Caisse Created");
        log.info("CaisseServiceImpl | addCaisse | Caisse Id : " + caisse.getId());
        return caisse;
    }

    @Override
    public CaisseResponse addAmountCaisse(double amount) {
        log.info("CaisseServiceImpl | addAmountCaisse is called");

        Date dt = new Date();
        LocalDateTime today = LocalDateTime.from(dt.toInstant().atZone(ZoneId.of("UTC")));
        LocalDateTime tomorrow = LocalDateTime.from(dt.toInstant().atZone(ZoneId.of("UTC"))).plusDays(1);
        String libelle = "Caisse_"+ today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Caisse caisse;

        if(caisseRepository.existsByLibelle(libelle) == null || !caisseRepository.existsByLibelle(libelle)){
            caisse = addCaisse(amount);
        }else {
            caisse = caisseRepository.findByLibelle(libelle).get();
            caisse.setMontant(caisse.getMontant() + amount);
            caisse = caisseRepository.save(caisse);
        }

        CaisseResponse caisseResponse = new CaisseResponse();
        copyProperties(caisse, caisseResponse);

        log.info("CaisseServiceImpl | addAmountCaisse | Caisse currentAmount : " + caisse.getMontant());
        return caisseResponse;
    }

    @Override
    public CaisseResponse substractAmountCaisse(double amount) {
        log.info("CaisseServiceImpl | addAmountCaisse is called");

        Date dt = new Date();
        LocalDateTime today = LocalDateTime.from(dt.toInstant().atZone(ZoneId.of("UTC")));
        LocalDateTime tomorrow = LocalDateTime.from(dt.toInstant().atZone(ZoneId.of("UTC"))).plusDays(1);
        String libelle = "Caisse_"+ today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Caisse caisse;

        if(caisseRepository.existsByLibelle(libelle) == null || !caisseRepository.existsByLibelle(libelle)){
            caisse = addCaisse(-amount);
        }else {
            caisse = caisseRepository.getCurrentCaisse(today, tomorrow, true).get();
            caisse.setMontant(caisse.getMontant() - amount);
            caisse = caisseRepository.save(caisse);
        }

        CaisseResponse caisseResponse = new CaisseResponse();
        copyProperties(caisse, caisseResponse);

        log.info("CaisseServiceImpl | addAmountCaisse | Caisse currentAmount : " + caisse.getMontant());
        return caisseResponse;
    }

    @Override
    public CaisseResponse getCaisseById(long caisseId) {
        log.info("CaisseServiceImpl | getCaisseById is called");
        log.info("CaisseServiceImpl | getCaisseById | Get the caisse for caisseId: {}", caisseId);

        Caisse caisse
                = caisseRepository.findById(caisseId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Caisse with given Id not found", NOT_FOUND));

        CaisseResponse caisseResponse = new CaisseResponse();

        copyProperties(caisse, caisseResponse);

        log.info("CaisseServiceImpl | getCaisseById | caisseResponse :" + caisseResponse.toString());

        return caisseResponse;
    }


    @Override
    public CaisseResponse closeCaisseById(long caisseId) {
        log.info("Caisse id: {}", caisseId);

        if (!caisseRepository.existsById(caisseId)) {
            log.info("Im in this loop {}", !caisseRepository.existsById(caisseId));
            throw new SecretariatCustomException(
                    "Caisse with given with Id: " + caisseId + " not found:",
                    NOT_FOUND);
        }
        log.info("Close Caisse with id: {}", caisseId);
        Caisse caisse = caisseRepository.findById(caisseId).orElseThrow();
        caisse.setOuvert(false);
        caisse.setDate_fermeture(LocalDateTime.now());
        caisse = caisseRepository.save(caisse);

        CaisseResponse caisseResponse = new CaisseResponse();
        copyProperties(caisse, caisseResponse);

        return caisseResponse;
    }
}
