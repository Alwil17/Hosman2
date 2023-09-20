package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.PrestationTarif;
import com.dopediatrie.hosman.secretariat.entity.PrestationTarifPK;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.PrestationTarifRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PrestationTarifResponse;
import com.dopediatrie.hosman.secretariat.repository.PrestationRepository;
import com.dopediatrie.hosman.secretariat.repository.PrestationTarifRepository;
import com.dopediatrie.hosman.secretariat.repository.TarifRepository;
import com.dopediatrie.hosman.secretariat.service.PrestationTarifService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class PrestationTarifServiceImpl implements PrestationTarifService {
    private final PrestationTarifRepository prestationTarifRepository;
    private final PrestationRepository prestationRepository;
    private final TarifRepository tarifRepository;
    private final String NOT_FOUND = "ASSURANCE_TARIF_NOT_FOUND";

    @Override
    public List<PrestationTarif> getAllPrestationTarifs() {
        return prestationTarifRepository.findAll();
    }

    @Override
    public PrestationTarifPK addPrestationTarif(PrestationTarifRequest prestationTarifRequest) {
        log.info("PrestationTarifServiceImpl | addPrestationTarif is called");

        PrestationTarifPK pk = new PrestationTarifPK();
        pk.prestation_id = prestationTarifRequest.getPrestation_id();
        pk.tarif_id = prestationTarifRequest.getTarif_id();

        PrestationTarif prestationTarif
                = PrestationTarif.builder()
                .id(pk)
                .prestation(prestationRepository.findById(prestationTarifRequest.getPrestation_id()).get())
                .tarif(tarifRepository.findById(prestationTarifRequest.getTarif_id()).get())
                .quantite(prestationTarifRequest.getQuantite())
                .total_price_gros(prestationTarifRequest.getTotal_price_gros())
                .build();

        prestationTarif = prestationTarifRepository.save(prestationTarif);

        log.info("PrestationTarifServiceImpl | addPrestationTarif | PrestationTarif Created");
        log.info("PrestationTarifServiceImpl | addPrestationTarif | PrestationTarif Id : " + prestationTarif.getId());
        return prestationTarif.getId();
    }

    @Override
    public PrestationTarifResponse getPrestationTarifById(long prestationTarifId) {
        log.info("PrestationTarifServiceImpl | getPrestationTarifById is called");
        log.info("PrestationTarifServiceImpl | getPrestationTarifById | Get the prestationTarif for prestationTarifId: {}", prestationTarifId);

        PrestationTarif prestationTarif
                = prestationTarifRepository.findById(prestationTarifId)
                .orElseThrow(
                        () -> new SecretariatCustomException("PrestationTarif with given Id not found", NOT_FOUND));

        PrestationTarifResponse prestationTarifResponse = new PrestationTarifResponse();

        copyProperties(prestationTarif, prestationTarifResponse);

        log.info("PrestationTarifServiceImpl | getPrestationTarifById | prestationTarifResponse :" + prestationTarifResponse.toString());

        return prestationTarifResponse;
    }

    @Override
    public void editPrestationTarif(PrestationTarifRequest prestationTarifRequest, long prestationTarifId) {
        log.info("PrestationTarifServiceImpl | editPrestationTarif is called");

        PrestationTarif prestationTarif
                = prestationTarifRepository.findById(prestationTarifId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "PrestationTarif with given Id not found",
                        NOT_FOUND
                ));
        prestationTarif.setPrestation(prestationRepository.findById(prestationTarifRequest.getPrestation_id()).get());
        prestationTarif.setTarif(tarifRepository.findById(prestationTarifRequest.getTarif_id()).get());
        prestationTarif.setQuantite(prestationTarifRequest.getQuantite());
        prestationTarif.setTotal_price_gros(prestationTarifRequest.getTotal_price_gros());
        prestationTarifRepository.save(prestationTarif);

        log.info("PrestationTarifServiceImpl | editPrestationTarif | PrestationTarif Updated");
        log.info("PrestationTarifServiceImpl | editPrestationTarif | PrestationTarif Id : " + prestationTarif.getId());
    }

    @Override
    public void deletePrestationTarifById(long prestationTarifId) {
        log.info("PrestationTarif id: {}", prestationTarifId);

        if (!prestationTarifRepository.existsById(prestationTarifId)) {
            log.info("Im in this loop {}", !prestationTarifRepository.existsById(prestationTarifId));
            throw new SecretariatCustomException(
                    "PrestationTarif with given with Id: " + prestationTarifId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting PrestationTarif with id: {}", prestationTarifId);
        prestationTarifRepository.deleteById(prestationTarifId);
    }
}
