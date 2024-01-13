package com.dopediatrie.hosman.hospi.service.impl;

import com.dopediatrie.hosman.hospi.entity.Chirurgie;
import com.dopediatrie.hosman.hospi.exception.HospiCustomException;
import com.dopediatrie.hosman.hospi.payload.request.ChirurgieRequest;
import com.dopediatrie.hosman.hospi.payload.response.ChirurgieResponse;
import com.dopediatrie.hosman.hospi.payload.response.MedecinResponse;
import com.dopediatrie.hosman.hospi.repository.ChirurgieRepository;
import com.dopediatrie.hosman.hospi.repository.HospitRepository;
import com.dopediatrie.hosman.hospi.service.ChirurgieService;
import com.dopediatrie.hosman.hospi.service.MedecinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChirurgieServiceImpl implements ChirurgieService {
    private final String NOT_FOUND = "CHIRURGIE_NOT_FOUND";

    private final ChirurgieRepository chirurgieRepository;
    private final HospitRepository hospitRepository;
    private final MedecinService medecinService;

    @Override
    public List<Chirurgie> getAllChirurgies() {
        return chirurgieRepository.findAll();
    }

    @Override
    public long addChirurgie(ChirurgieRequest chirurgieRequest) {
        log.info("ChirurgieServiceImpl | addChirurgie is called");
        Chirurgie chirurgie;
        if(chirurgieRepository.existsByMed_refAndTitleAndHospitId(chirurgieRequest.getMed_ref(), chirurgieRequest.getTitle(), chirurgieRequest.getHospit_id())){
            chirurgie = chirurgieRepository.findByMed_refAndTitleAndHospitId(chirurgieRequest.getMed_ref(), chirurgieRequest.getTitle(), chirurgieRequest.getHospit_id()).orElseThrow();
            editChirurgie(chirurgieRequest, chirurgie.getId());
        }else {
            chirurgie = Chirurgie.builder()
                    .title(chirurgieRequest.getTitle())
                    .med_ref(chirurgieRequest.getMed_ref())
                    .med_type(chirurgieRequest.getMed_type())
                    .coef(chirurgieRequest.getCoef())
                    .date_op(chirurgieRequest.getDate_op())
                    .comments(chirurgieRequest.getComments())
                    .hospit(hospitRepository.findById(chirurgieRequest.getHospit_id()).orElseThrow())
                    .build();
            chirurgie = chirurgieRepository.save(chirurgie);
        }

        log.info("ChirurgieServiceImpl | addChirurgie | Chirurgie Created/Updated");
        log.info("ChirurgieServiceImpl | addChirurgie | Chirurgie Id : " + chirurgie.getId());
        return chirurgie.getId();
    }

    @Override
    public void addChirurgie(List<ChirurgieRequest> chirurgieRequests) {
        log.info("ChirurgieServiceImpl | addChirurgie is called");

        for (ChirurgieRequest chirurgieRequest : chirurgieRequests) {
            Chirurgie chirurgie;
            if(chirurgieRepository.existsByMed_refAndTitleAndHospitId(chirurgieRequest.getMed_ref(), chirurgieRequest.getTitle(), chirurgieRequest.getHospit_id())){
                chirurgie = chirurgieRepository.findByMed_refAndTitleAndHospitId(chirurgieRequest.getMed_ref(), chirurgieRequest.getTitle(), chirurgieRequest.getHospit_id()).orElseThrow();
                editChirurgie(chirurgieRequest, chirurgie.getId());
            }else {
                chirurgie = Chirurgie.builder()
                        .title(chirurgieRequest.getTitle())
                        .med_ref(chirurgieRequest.getMed_ref())
                        .med_type(chirurgieRequest.getMed_type())
                        .coef(chirurgieRequest.getCoef())
                        .date_op(chirurgieRequest.getDate_op())
                        .comments(chirurgieRequest.getComments())
                        .hospit(hospitRepository.findById(chirurgieRequest.getHospit_id()).orElseThrow())
                        .build();
                chirurgieRepository.save(chirurgie);
            }
        }

        log.info("ChirurgieServiceImpl | addChirurgie | Chirurgie Created");
    }

    @Override
    public ChirurgieResponse getChirurgieById(long chirurgieId) {
        log.info("ChirurgieServiceImpl | getChirurgieById is called");
        log.info("ChirurgieServiceImpl | getChirurgieById | Get the chirurgie for chirurgieId: {}", chirurgieId);

        Chirurgie chirurgie
                = chirurgieRepository.findById(chirurgieId)
                .orElseThrow(
                        () -> new HospiCustomException("Chirurgie with given Id not found", NOT_FOUND));

        ChirurgieResponse chirurgieResponse = new ChirurgieResponse();
        copyProperties(chirurgie, chirurgieResponse);
        MedecinResponse mr = medecinService.getMedecinByMatricule(chirurgieResponse.getMed_ref());
        chirurgieResponse.setMedecin(mr);

        log.info("ChirurgieServiceImpl | getChirurgieById | chirurgieResponse :" + chirurgieResponse.toString());

        return chirurgieResponse;
    }

    @Override
    public List<ChirurgieResponse> getChirurgieByHospitId(long hospitId) {
        log.info("ChirurgieServiceImpl | getChirurgieByHospitId is called");
        List<Chirurgie> chirurgies = chirurgieRepository.findAllByHospitId(hospitId);
        List<ChirurgieResponse> chirurgieResponses = new ArrayList<>();
        for (Chirurgie s : chirurgies) {
            ChirurgieResponse sr = new ChirurgieResponse();
            copyProperties(s, sr);
            MedecinResponse mr = medecinService.getMedecinByMatricule(sr.getMed_ref());
            sr.setMedecin(mr);
            chirurgieResponses.add(sr);
        }
        return chirurgieResponses;
    }

    @Override
    public void editChirurgie(ChirurgieRequest chirurgieRequest, long chirurgieId) {
        log.info("ChirurgieServiceImpl | editChirurgie is called");

        Chirurgie chirurgie
                = chirurgieRepository.findById(chirurgieId)
                .orElseThrow(() -> new HospiCustomException(
                        "Chirurgie with given Id not found",
                        NOT_FOUND
                ));
        chirurgie.setCoef(chirurgieRequest.getCoef());
        chirurgie.setComments(chirurgieRequest.getComments());
        chirurgie.setMed_ref(chirurgieRequest.getMed_ref());
        chirurgie.setMed_type(chirurgieRequest.getMed_type());
        chirurgie.setDate_op(chirurgieRequest.getDate_op());
        chirurgie.setTitle(chirurgieRequest.getTitle());
        chirurgie.setHospit(hospitRepository.findById(chirurgieRequest.getHospit_id()).orElseThrow());
        chirurgieRepository.save(chirurgie);

        log.info("ChirurgieServiceImpl | editChirurgie | Chirurgie Updated");
        log.info("ChirurgieServiceImpl | editChirurgie | Chirurgie Id : " + chirurgie.getId());
    }

    @Override
    public void deleteChirurgieById(long chirurgieId) {
        log.info("Chirurgie id: {}", chirurgieId);

        if (!chirurgieRepository.existsById(chirurgieId)) {
            log.info("Im in this loop {}", !chirurgieRepository.existsById(chirurgieId));
            throw new HospiCustomException(
                    "Chirurgie with given with Id: " + chirurgieId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Chirurgie with id: {}", chirurgieId);
        chirurgieRepository.deleteById(chirurgieId);
    }
}
