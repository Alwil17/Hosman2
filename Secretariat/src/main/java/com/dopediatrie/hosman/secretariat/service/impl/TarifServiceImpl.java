package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Acte;
import com.dopediatrie.hosman.secretariat.entity.Tarif;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.ProformatRequest;
import com.dopediatrie.hosman.secretariat.payload.request.TarifRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PatientResponse;
import com.dopediatrie.hosman.secretariat.payload.response.ProduitResponse;
import com.dopediatrie.hosman.secretariat.payload.response.ProformatResponse;
import com.dopediatrie.hosman.secretariat.payload.response.TarifResponse;
import com.dopediatrie.hosman.secretariat.repository.ActeRepository;
import com.dopediatrie.hosman.secretariat.repository.TarifRepository;
import com.dopediatrie.hosman.secretariat.service.TarifService;
import com.dopediatrie.hosman.secretariat.utils.Str;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class TarifServiceImpl implements TarifService {
    private final TarifRepository tarifRepository;
    private final ActeRepository acteRepository;
    private final String NOT_FOUND = "TARIF_NOT_FOUND";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Tarif> getAllTarifs() {
        return tarifRepository.findAll();
    }

    @Override
    public long addTarif(TarifRequest tarifRequest) {
        log.info("TarifServiceImpl | addTarif is called");

        Tarif tarif
                = Tarif.builder()
                .libelle(tarifRequest.getLibelle())
                .slug(Str.slug(tarifRequest.getLibelle()))
                .code(tarifRequest.getCode())
                .description(tarifRequest.getDescription())
                .tarif_non_assure(tarifRequest.getTarif_non_assure())
                .tarif_etr_non_assure(tarifRequest.getTarif_etr_non_assure())
                .tarif_assur_locale(tarifRequest.getTarif_assur_locale())
                .tarif_assur_etr(tarifRequest.getTarif_assur_etr())
                .tarif_assur_hors_zone(tarifRequest.getTarif_assur_hors_zone())
                .structure_id(tarifRequest.getStructure_id())
                .acte(acteRepository.findById(tarifRequest.getActe_id()).get())
                .build();

        tarif = tarifRepository.save(tarif);

        log.info("TarifServiceImpl | addTarif | Tarif Created");
        log.info("TarifServiceImpl | addTarif | Tarif Id : " + tarif.getId());
        return tarif.getId();
    }

    @Override
    public void addTarif(List<TarifRequest> tarifRequests) {
        log.info("TarifServiceImpl | addTarifs is called");

        for (TarifRequest tarifRequest : tarifRequests) {
            Tarif tarif
                    = Tarif.builder()
                    .libelle(tarifRequest.getLibelle())
                    .slug(Str.slug(tarifRequest.getLibelle()))
                    .code(tarifRequest.getCode())
                    .description(tarifRequest.getDescription())
                    .tarif_non_assure(tarifRequest.getTarif_non_assure())
                    .tarif_etr_non_assure(tarifRequest.getTarif_etr_non_assure())
                    .tarif_assur_locale(tarifRequest.getTarif_assur_locale())
                    .tarif_assur_etr(tarifRequest.getTarif_assur_etr())
                    .tarif_assur_hors_zone(tarifRequest.getTarif_assur_hors_zone())
                    .structure_id(tarifRequest.getStructure_id())
                    .acte(acteRepository.findById(tarifRequest.getActe_id()).get())
                    .build();

            tarifRepository.save(tarif);
        }

        log.info("TarifServiceImpl | addTarifs | Tarifs Created");
    }

    @Override
    public TarifResponse getTarifById(long tarifId) {
        log.info("TarifServiceImpl | getTarifById is called");
        log.info("TarifServiceImpl | getTarifById | Get the tarif for tarifId: {}", tarifId);

        Tarif tarif
                = tarifRepository.findById(tarifId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Tarif with given Id not found", NOT_FOUND));

        TarifResponse tarifResponse = new TarifResponse();

        copyProperties(tarif, tarifResponse);

        log.info("TarifServiceImpl | getTarifById | tarifResponse :" + tarifResponse.toString());

        return tarifResponse;
    }

    @Override
    public TarifResponse getTarifByCode(String code) {
        log.info("TarifServiceImpl | getTarifById is called");

        Tarif tarif
                = tarifRepository.findByCodeEquals(code)
                .orElseThrow(
                        () -> new SecretariatCustomException("Tarif with given Id not found", NOT_FOUND));

        TarifResponse tarifResponse = new TarifResponse();

        copyProperties(tarif, tarifResponse);

        log.info("TarifServiceImpl | getTarifById | tarifResponse :" + tarifResponse.toString());

        return tarifResponse;
    }

    @Override
    public List<Tarif> getTarifForGroupeAndActe(String groupeCode, String acte) {
        log.info("TarifServiceImpl | getTarifForGroupeAndActe is called");
        List<Tarif> tarifs = Collections.emptyList();
        List<ProduitResponse> produitResponses = Collections.emptyList();
        Acte mappedActe = Acte.builder().build();

        if(groupeCode.equals("GRP016")){
            ResponseEntity<ProduitResponse[]> responseEntity = restTemplate
                    .getForEntity("http://hosman-apps.com:83/produits/search?code_acte=medic",
                            ProduitResponse[].class);

            produitResponses = Arrays.asList(responseEntity.getBody());
            mappedActe = acteRepository.findByCodeEquals("medic").orElseThrow();
        }else if (groupeCode.equals("GRP017")){
            ResponseEntity<ProduitResponse[]> responseEntity = restTemplate
                    .getForEntity("http://hosman-apps.com:83/produits/search?code_acte=conso",
                            ProduitResponse[].class);

            produitResponses = Arrays.asList(responseEntity.getBody());
            mappedActe = acteRepository.findByCodeEquals("conso").orElseThrow();
        }
        tarifs = tarifRepository.findTarifsByGroupeAndActe(groupeCode, acte);

        if(produitResponses != null && produitResponses.size() > 0){
            for (ProduitResponse pr : produitResponses) {
                Tarif tarif = Tarif.builder()
                        .libelle(pr.getNom_officiel())
                        .slug(Str.slug(pr.getNom_officiel()))
                        .code(pr.getCode())
                        .tarif_assur_etr(pr.getPrix())
                        .tarif_assur_hors_zone(pr.getPrix())
                        .tarif_non_assure(pr.getPrix())
                        .tarif_etr_non_assure(pr.getPrix())
                        .tarif_assur_locale(pr.getPrix())
                        .acte(mappedActe)
                        .build();
                tarifs.add(tarif);
            }
        }
        return tarifs;
    }

    @Override
    public List<Tarif> getTarifForGroupe(String groupeCode) {
        log.info("TarifServiceImpl | getTarifForGroupe is called");
        log.info("TarifServiceImpl | getTarifForGroupe | Get the tarif for groupeCode: {}", groupeCode);

        List<Tarif> tarifs = Collections.emptyList();
        List<ProduitResponse> produitResponses = Collections.emptyList();
        Acte mappedActe = Acte.builder().build();

        if(groupeCode.equals("GRP016")){
            ResponseEntity<ProduitResponse[]> responseEntity = restTemplate
                    .getForEntity("http://hosman-apps.com:83/produits/search?code_acte=medic",
                            ProduitResponse[].class);

            produitResponses = Arrays.asList(responseEntity.getBody());
            mappedActe = acteRepository.findByCodeEquals("medic").orElseThrow();
        }else if (groupeCode.equals("GRP017")){
            ResponseEntity<ProduitResponse[]> responseEntity = restTemplate
                    .getForEntity("http://hosman-apps.com:83/produits/search?code_acte=conso",
                            ProduitResponse[].class);

            produitResponses = Arrays.asList(responseEntity.getBody());
            mappedActe = acteRepository.findByCodeEquals("conso").orElseThrow();
        }
        tarifs = tarifRepository.findTarifsByGroupe(groupeCode);

        if(produitResponses != null && produitResponses.size() > 0){
            for (ProduitResponse pr : produitResponses) {
                Tarif tarif = Tarif.builder()
                        .libelle(pr.getNom_officiel())
                        .slug(Str.slug(pr.getNom_officiel()))
                        .code(pr.getCode())
                        .tarif_assur_etr(pr.getPrix())
                        .tarif_assur_hors_zone(pr.getPrix())
                        .tarif_non_assure(pr.getPrix())
                        .tarif_etr_non_assure(pr.getPrix())
                        .tarif_assur_locale(pr.getPrix())
                        .acte(mappedActe)
                        .build();
                tarifs.add(tarif);
            }
        }
        return tarifs;
    }

    @Override
    public void editTarif(TarifRequest tarifRequest, long tarifId) {
        log.info("TarifServiceImpl | editTarif is called");

        Tarif tarif
                = tarifRepository.findById(tarifId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Tarif with given Id not found",
                        NOT_FOUND
                ));
        tarif.setLibelle(tarifRequest.getLibelle());
        tarif.setSlug(Str.slug(tarifRequest.getLibelle()));
        tarif.setCode(tarifRequest.getCode());
        tarif.setDescription(tarifRequest.getDescription());
        tarif.setTarif_non_assure(tarifRequest.getTarif_non_assure());
        tarif.setTarif_etr_non_assure(tarifRequest.getTarif_etr_non_assure());
        tarif.setTarif_assur_locale(tarifRequest.getTarif_assur_locale());
        tarif.setTarif_assur_etr(tarifRequest.getTarif_assur_etr());
        tarif.setTarif_assur_hors_zone(tarifRequest.getTarif_assur_hors_zone());
        tarif.setStructure_id(tarifRequest.getStructure_id());
        tarif.setActe(acteRepository.findById(tarifRequest.getActe_id()).get());
        tarifRepository.save(tarif);

        log.info("TarifServiceImpl | editTarif | Tarif Updated");
        log.info("TarifServiceImpl | editTarif | Tarif Id : " + tarif.getId());
    }

    @Override
    public void deleteTarifById(long tarifId) {
        log.info("Tarif id: {}", tarifId);

        if (!tarifRepository.existsById(tarifId)) {
            log.info("Im in this loop {}", !tarifRepository.existsById(tarifId));
            throw new SecretariatCustomException(
                    "Tarif with given with Id: " + tarifId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Tarif with id: {}", tarifId);
        tarifRepository.deleteById(tarifId);
    }

    @Override
    public List<TarifResponse> getTarifForExamen() {
        log.info("TarifServiceImpl | getTarifForExamen is called");
        List<Tarif> tarifs = tarifRepository.getAllExamens();
        List<TarifResponse> tarifResponses = new ArrayList<TarifResponse>();
        if(tarifs != null && tarifs.size() > 0){
            for (Tarif t : tarifs) {
                TarifResponse tarifResponse = new TarifResponse();
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                tarifResponse = mapper.convertValue(t, TarifResponse.class);
                tarifResponses.add(tarifResponse);
            }
        }
        return tarifResponses;
    }

    @Override
    public List<ProformatResponse> processProformat(ProformatRequest proformatRequest) {
        log.info("TarifServiceImpl | processProformat is called");
        List<ProformatResponse> responses = new ArrayList<>();
        if (proformatRequest.getActes() != null && proformatRequest.getActes().size() > 0){
            for (TarifRequest tarifRequest : proformatRequest.getActes()) {
                ProformatResponse res = new ProformatResponse();
                res.setActe(tarifRequest.getLibelle());
                res.setQte(tarifRequest.getQte());
                switch (proformatRequest.getIs_assure()){
                    case 0:
                        res.setPrix_unit(tarifRequest.getTarif_non_assure());
                        break;
                    case 1:
                        res.setPrix_unit(tarifRequest.getTarif_etr_non_assure());
                        break;
                    case 2:
                        res.setPrix_unit(tarifRequest.getTarif_assur_locale());
                        break;
                    case 3:
                        res.setPrix_unit(tarifRequest.getTarif_assur_hors_zone());
                        break;
                    default:
                        res.setPrix_unit(tarifRequest.getTarif_non_assure());
                        break;
                }
                res.setPrix_total(res.getPrix_unit() * res.getQte());
                responses.add(res);
            }
        }
        log.info("TarifServiceImpl | processProformat | count :"+responses.size());
        return responses;
    }

    @Override
    public List<Tarif> getTarifForActe(String acte) {
        log.info("TarifServiceImpl | getTarifForActe is called");
        return tarifRepository.findAllByActe(acte);
    }
}
