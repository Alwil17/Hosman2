package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.PrestationTarifPK;
import com.dopediatrie.hosman.secretariat.entity.PrestationTarifTemp;
import com.dopediatrie.hosman.secretariat.entity.PrestationTarifTempPK;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.PrestationTarifTempRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PrestationTarifTempResponse;
import com.dopediatrie.hosman.secretariat.repository.PrestationTempRepository;
import com.dopediatrie.hosman.secretariat.repository.PrestationTarifTempRepository;
import com.dopediatrie.hosman.secretariat.repository.TarifRepository;
import com.dopediatrie.hosman.secretariat.service.PrestationTarifTempService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class PrestationTarifTempServiceImpl implements PrestationTarifTempService {
    private final PrestationTarifTempRepository prestationTarifRepository;
    private final PrestationTempRepository prestationRepository;
    private final TarifRepository tarifRepository;
    private final String NOT_FOUND = "ASSURANCE_TARIF_NOT_FOUND";

    @Override
    public List<PrestationTarifTemp> getAllPrestationTarifTemps() {
        return prestationTarifRepository.findAll();
    }

    @Override
    public PrestationTarifTempPK addPrestationTarifTemp(PrestationTarifTempRequest prestationTarifRequest) {
        log.info("PrestationTarifTempServiceImpl | addPrestationTarifTemp is called");

        PrestationTarifTempPK pk = new PrestationTarifTempPK();
        pk.prestation_temp_id = prestationTarifRequest.getPrestation_temp_id();
        pk.tarif_id = prestationTarifRequest.getTarif_id();

        PrestationTarifTemp prestationTarif
                = PrestationTarifTemp.builder()
                .id(pk)
                .prestation(prestationRepository.findById(prestationTarifRequest.getPrestation_temp_id()).get())
                .tarif(tarifRepository.findById(prestationTarifRequest.getTarif_id()).get())
                .quantite(prestationTarifRequest.getQuantite())
                .total_price_gros(prestationTarifRequest.getTotal_price_gros())
                .build();

        prestationTarif = prestationTarifRepository.save(prestationTarif);

        log.info("PrestationTarifTempServiceImpl | addPrestationTarifTemp | PrestationTarifTemp Created");
        log.info("PrestationTarifTempServiceImpl | addPrestationTarifTemp | PrestationTarifTemp Id : " + prestationTarif.getId());
        return prestationTarif.getId();
    }

    @Override
    public PrestationTarifTempResponse getPrestationTarifTempById(long prestationTarifId) {
        log.info("PrestationTarifTempServiceImpl | getPrestationTarifTempById is called");
        log.info("PrestationTarifTempServiceImpl | getPrestationTarifTempById | Get the prestationTarif for prestationTarifId: {}", prestationTarifId);

        PrestationTarifTemp prestationTarif
                = prestationTarifRepository.findById(prestationTarifId)
                .orElseThrow(
                        () -> new SecretariatCustomException("PrestationTarifTemp with given Id not found", NOT_FOUND));

        PrestationTarifTempResponse prestationTarifResponse = new PrestationTarifTempResponse();

        copyProperties(prestationTarif, prestationTarifResponse);

        log.info("PrestationTarifTempServiceImpl | getPrestationTarifTempById | prestationTarifResponse :" + prestationTarifResponse.toString());

        return prestationTarifResponse;
    }

    @Override
    public void editPrestationTarifTemp(PrestationTarifTempRequest prestationTarifRequest, long prestationTarifId) {
        log.info("PrestationTarifTempServiceImpl | editPrestationTarifTemp is called");

        PrestationTarifTemp prestationTarif
                = prestationTarifRepository.findById(prestationTarifId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "PrestationTarifTemp with given Id not found",
                        NOT_FOUND
                ));
        prestationTarif.setPrestation(prestationRepository.findById(prestationTarifRequest.getPrestation_temp_id()).get());
        prestationTarif.setTarif(tarifRepository.findById(prestationTarifRequest.getTarif_id()).get());
        prestationTarif.setQuantite(prestationTarifRequest.getQuantite());
        prestationTarif.setTotal_price_gros(prestationTarifRequest.getTotal_price_gros());
        prestationTarifRepository.save(prestationTarif);

        log.info("PrestationTarifTempServiceImpl | editPrestationTarifTemp | PrestationTarifTemp Updated");
        log.info("PrestationTarifTempServiceImpl | editPrestationTarifTemp | PrestationTarifTemp Id : " + prestationTarif.getId());
    }

    @Override
    public void deletePrestationTarifTempById(long prestationTarifId) {
        log.info("PrestationTarifTemp id: {}", prestationTarifId);

        if (!prestationTarifRepository.existsById(prestationTarifId)) {
            log.info("Im in this loop {}", !prestationTarifRepository.existsById(prestationTarifId));
            throw new SecretariatCustomException(
                    "PrestationTarifTemp with given with Id: " + prestationTarifId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting PrestationTarifTemp with id: {}", prestationTarifId);
        prestationTarifRepository.deleteById(prestationTarifId);
    }

    @Override
    public void deletePrestationTarifTempById(PrestationTarifPK prestationTarifId) {
        log.info("PrestationTarifTemp id: {}", prestationTarifId);
        prestationTarifRepository.deleteByPrestationIdAndTarifId(prestationTarifId.prestation_id, prestationTarifId.tarif_id);
        log.info("PrestationTarifTemp deleted");
    }
}
