package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Tarif;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.TarifRequest;
import com.dopediatrie.hosman.secretariat.payload.response.TarifResponse;
import com.dopediatrie.hosman.secretariat.repository.SousActeRepository;
import com.dopediatrie.hosman.secretariat.repository.TarifRepository;
import com.dopediatrie.hosman.secretariat.service.TarifService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class TarifServiceImpl implements TarifService {
    private final TarifRepository tarifRepository;
    private final SousActeRepository sousActeRepository;
    private final String NOT_FOUND = "ACTE_NOT_FOUND";

    @Override
    public List<Tarif> getAllTarifs() {
        return tarifRepository.findAll();
    }

    @Override
    public long addTarif(TarifRequest tarifRequest) {
        log.info("TarifServiceImpl | addTarif is called");

        Tarif tarif
                = Tarif.builder()
                .designation(tarifRequest.getDesignation())
                .tarif_non_assure(tarifRequest.getTarif_non_assure())
                .tarif_etr_non_assure(tarifRequest.getTarif_etr_non_assure())
                .tarif_assur_locale(tarifRequest.getTarif_assur_locale())
                .tarif_assur_etr(tarifRequest.getTarif_assur_etr())
                .tarif_assur_hors_zone(tarifRequest.getTarif_assur_hors_zone())
                .structure_id(tarifRequest.getStructure_id())
                .sous_acte(sousActeRepository.findById(tarifRequest.getSous_acte_id()).get())
                .description(tarifRequest.getDescription())
                .build();

        tarif = tarifRepository.save(tarif);

        log.info("TarifServiceImpl | addTarif | Tarif Created");
        log.info("TarifServiceImpl | addTarif | Tarif Id : " + tarif.getId());
        return tarif.getId();
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
    public void editTarif(TarifRequest tarifRequest, long tarifId) {
        log.info("TarifServiceImpl | editTarif is called");

        Tarif tarif
                = tarifRepository.findById(tarifId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Tarif with given Id not found",
                        NOT_FOUND
                ));
        tarif.setDesignation(tarifRequest.getDesignation());
        tarif.setTarif_non_assure(tarifRequest.getTarif_non_assure());
        tarif.setTarif_etr_non_assure(tarifRequest.getTarif_etr_non_assure());
        tarif.setTarif_assur_locale(tarifRequest.getTarif_assur_locale());
        tarif.setTarif_assur_etr(tarifRequest.getTarif_assur_etr());
        tarif.setTarif_assur_hors_zone(tarifRequest.getTarif_assur_hors_zone());
        tarif.setStructure_id(tarifRequest.getStructure_id());
        tarif.setSous_acte(sousActeRepository.findById(tarifRequest.getSous_acte_id()).get());
        tarif.setDescription(tarifRequest.getDescription());
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
}
