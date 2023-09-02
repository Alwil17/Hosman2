package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.ModePayement;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.NameRequest;
import com.dopediatrie.hosman.secretariat.payload.response.NameResponse;
import com.dopediatrie.hosman.secretariat.repository.ModePayementRepository;
import com.dopediatrie.hosman.secretariat.service.ModePayementService;
import com.dopediatrie.hosman.secretariat.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class ModePayementServiceImpl implements ModePayementService {
    private final ModePayementRepository modePayementRepository;
    private final String NOT_FOUND = "MODE_PAYEMENT_NOT_FOUND";

    @Override
    public List<ModePayement> getAllModePayements() {
        return modePayementRepository.findAll();
    }

    @Override
    public long addModePayement(NameRequest modePayementRequest) {
        log.info("ModePayementServiceImpl | addModePayement is called");

        ModePayement modePayement;
        if(!modePayementRepository.existsByNom(modePayementRequest.getNom())){
            modePayement
                    = ModePayement.builder()
                    .nom(modePayementRequest.getNom())
                    .slug(Str.slug(modePayementRequest.getNom()))
                    .build();

            modePayement = modePayementRepository.save(modePayement);
        }else{
            modePayement = modePayementRepository.findByNom(modePayementRequest.getNom()).orElseThrow();
        }

        log.info("ModePayementServiceImpl | addModePayement | ModePayement Created");
        log.info("ModePayementServiceImpl | addModePayement | ModePayement Id : " + modePayement.getId());
        return modePayement.getId();
    }

    @Override
    public NameResponse getModePayementById(long modePayementId) {
        log.info("ModePayementServiceImpl | getModePayementById is called");
        log.info("ModePayementServiceImpl | getModePayementById | Get the modePayement for modePayementId: {}", modePayementId);

        ModePayement modePayement
                = modePayementRepository.findById(modePayementId)
                .orElseThrow(
                        () -> new SecretariatCustomException("ModePayement with given Id not found", NOT_FOUND));

        NameResponse modePayementResponse = new NameResponse();

        copyProperties(modePayement, modePayementResponse);

        log.info("ModePayementServiceImpl | getModePayementById | modePayementResponse :" + modePayementResponse.toString());

        return modePayementResponse;
    }

    @Override
    public void editModePayement(NameRequest modePayementRequest, long modePayementId) {
        log.info("ModePayementServiceImpl | editModePayement is called");

        ModePayement modePayement
                = modePayementRepository.findById(modePayementId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "ModePayement with given Id not found",
                        NOT_FOUND
                ));
        modePayement.setNom(modePayementRequest.getNom());
        modePayement.setSlug(Str.slug(modePayementRequest.getNom()));
        modePayementRepository.save(modePayement);

        log.info("ModePayementServiceImpl | editModePayement | ModePayement Updated");
        log.info("ModePayementServiceImpl | editModePayement | ModePayement Id : " + modePayement.getId());
    }

    @Override
    public void deleteModePayementById(long modePayementId) {
        log.info("ModePayement id: {}", modePayementId);

        if (!modePayementRepository.existsById(modePayementId)) {
            log.info("Im in this loop {}", !modePayementRepository.existsById(modePayementId));
            throw new SecretariatCustomException(
                    "ModePayement with given with Id: " + modePayementId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting ModePayement with id: {}", modePayementId);
        modePayementRepository.deleteById(modePayementId);
    }
}
