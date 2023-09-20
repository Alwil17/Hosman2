package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Majoration;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.MajorationRequest;
import com.dopediatrie.hosman.secretariat.payload.response.MajorationResponse;
import com.dopediatrie.hosman.secretariat.repository.FactureRepository;
import com.dopediatrie.hosman.secretariat.repository.MajorationRepository;
import com.dopediatrie.hosman.secretariat.repository.PatientRepository;
import com.dopediatrie.hosman.secretariat.service.MajorationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class MajorationServiceImpl implements MajorationService {
    private final MajorationRepository majorationRepository;
    private final PatientRepository patientRepository;
    private final String NOT_FOUND = "MAJORATION_NOT_FOUND";

    @Override
    public List<Majoration> getAllMajorations() {
        return majorationRepository.findAll();
    }

    @Override
    public long addMajoration(MajorationRequest majorationRequest) {
        log.info("MajorationServiceImpl | addMajoration is called");
        Majoration majoration = Majoration.builder()
                .montant(majorationRequest.getMontant())
                .motif(majorationRequest.getMotif())
                .patient(patientRepository.findById(majorationRequest.getPatient_id()).orElseThrow())
                .date_operation(majorationRequest.getDate_operation())
                .build();

        majoration = majorationRepository.save(majoration);

        log.info("MajorationServiceImpl | addMajoration | Majoration Created");
        log.info("MajorationServiceImpl | addMajoration | Majoration Id : " + majoration.getId());
        return majoration.getId();
    }

    @Override
    public MajorationResponse getMajorationById(long majorationId) {
        log.info("MajorationServiceImpl | getMajorationById is called");
        log.info("MajorationServiceImpl | getMajorationById | Get the majoration for majorationId: {}", majorationId);

        Majoration majoration
                = majorationRepository.findById(majorationId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Majoration with given Id not found", NOT_FOUND));

        MajorationResponse majorationResponse = new MajorationResponse();

        copyProperties(majoration, majorationResponse);

        log.info("MajorationServiceImpl | getMajorationById | majorationResponse :" + majorationResponse.toString());

        return majorationResponse;
    }

    @Override
    public void editMajoration(MajorationRequest majorationRequest, long majorationId) {
        log.info("MajorationServiceImpl | editMajoration is called");

        Majoration majoration
                = majorationRepository.findById(majorationId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Majoration with given Id not found",
                        NOT_FOUND
                ));
        majoration.setMontant(majorationRequest.getMontant());
        majoration.setMotif(majorationRequest.getMotif());
        majoration.setDate_operation(majorationRequest.getDate_operation());
        majorationRepository.save(majoration);

        log.info("MajorationServiceImpl | editMajoration | Majoration Updated");
        log.info("MajorationServiceImpl | editMajoration | Majoration Id : " + majoration.getId());
    }

    @Override
    public void deleteMajorationById(long majorationId) {
        log.info("Majoration id: {}", majorationId);

        if (!majorationRepository.existsById(majorationId)) {
            log.info("Im in this loop {}", !majorationRepository.existsById(majorationId));
            throw new SecretariatCustomException(
                    "Majoration with given with Id: " + majorationId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Majoration with id: {}", majorationId);
        majorationRepository.deleteById(majorationId);
    }
}
