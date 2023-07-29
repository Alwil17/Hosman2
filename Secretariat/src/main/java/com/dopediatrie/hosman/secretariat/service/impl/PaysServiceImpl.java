package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Pays;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.PaysRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PaysResponse;
import com.dopediatrie.hosman.secretariat.repository.PaysRepository;
import com.dopediatrie.hosman.secretariat.service.PaysService;
import com.dopediatrie.hosman.secretariat.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class PaysServiceImpl implements PaysService {
    private final PaysRepository paysRepository;
    private final String NOT_FOUND = "PAYS_NOT_FOUND";

    @Override
    public List<Pays> getAllPays() {
        return paysRepository.findAll();
    }

    @Override
    public long addPays(PaysRequest paysRequest) {
        log.info("PaysServiceImpl | addPays is called");

        Pays pays
                = Pays.builder()
                .nom(paysRequest.getNom())
                .slug(Str.slug(paysRequest.getNom()))
                .code(paysRequest.getCode())
                .indicatif(paysRequest.getIndicatif())
                .nationalite(paysRequest.getNationalite())
                .build();

        pays = paysRepository.save(pays);

        log.info("PaysServiceImpl | addPays | Pays Created");
        log.info("PaysServiceImpl | addPays | Pays Id : " + pays.getId());
        return pays.getId();
    }

    @Override
    public PaysResponse getPaysById(long paysId) {
        log.info("PaysServiceImpl | getPaysById is called");
        log.info("PaysServiceImpl | getPaysById | Get the pays for paysId: {}", paysId);

        Pays pays
                = paysRepository.findById(paysId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Pays with given Id not found", NOT_FOUND));

        PaysResponse paysResponse = new PaysResponse();

        copyProperties(pays, paysResponse);

        log.info("PaysServiceImpl | getPaysById | paysResponse :" + paysResponse.toString());

        return paysResponse;
    }

    @Override
    public void editPays(PaysRequest paysRequest, long paysId) {
        log.info("PaysServiceImpl | editPays is called");

        Pays pays
                = paysRepository.findById(paysId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Pays with given Id not found",
                        NOT_FOUND
                ));
        pays.setNom(paysRequest.getNom());
        pays.setSlug(Str.slug(paysRequest.getNom()));
        pays.setCode(paysRequest.getCode());
        pays.setIndicatif(paysRequest.getIndicatif());
        pays.setNationalite(paysRequest.getNationalite());
        paysRepository.save(pays);

        log.info("PaysServiceImpl | editPays | Pays Updated");
        log.info("PaysServiceImpl | editPays | Pays Id : " + pays.getId());
    }

    @Override
    public void deletePaysById(long paysId) {
        log.info("Pays id: {}", paysId);

        if (!paysRepository.existsById(paysId)) {
            log.info("Im in this loop {}", !paysRepository.existsById(paysId));
            throw new SecretariatCustomException(
                    "Pays with given with Id: " + paysId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Pays with id: {}", paysId);
        paysRepository.deleteById(paysId);
    }
}
