package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Maladie;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.NameRequest;
import com.dopediatrie.hosman.secretariat.payload.response.NameResponse;
import com.dopediatrie.hosman.secretariat.repository.MaladieRepository;
import com.dopediatrie.hosman.secretariat.service.MaladieService;
import com.dopediatrie.hosman.secretariat.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class MaladieServiceImpl implements MaladieService {
    private final MaladieRepository maladieRepository;
    private final String NOT_FOUND = "MALADIE_NOT_FOUND";

    @Override
    public List<Maladie> getAllMaladies() {
        return maladieRepository.findAll();
    }

    @Override
    public long addMaladie(NameRequest maladieRequest) {
        log.info("MaladieServiceImpl | addMaladie is called");

        Maladie maladie;
        if(maladieRepository.existsByNom(maladieRequest.getNom()) == null || !maladieRepository.existsByNom(maladieRequest.getNom())){
            maladie = Maladie.builder()
                    .nom(maladieRequest.getNom())
                    .slug(Str.slug(maladieRequest.getNom()))
                    .build();
            maladie = maladieRepository.save(maladie);
        }else{
            maladie = maladieRepository.findByNom(maladieRequest.getNom()).get(0);
            editMaladie(maladieRequest, maladie.getId());
        }

        log.info("MaladieServiceImpl | addMaladie | Maladie Created");
        log.info("MaladieServiceImpl | addMaladie | Maladie Id : " + maladie.getId());
        return maladie.getId();
    }

    @Override
    public void addMaladie(List<NameRequest> maladieRequests) {
        log.info("MaladieServiceImpl | addMaladie is called");

        for (NameRequest maladieRequest : maladieRequests
             ) {
            Maladie maladie;
            if(maladieRepository.existsByNom(maladieRequest.getNom()) == null || !maladieRepository.existsByNom(maladieRequest.getNom())){
                maladie = Maladie.builder()
                        .nom(maladieRequest.getNom())
                        .slug(Str.slug(maladieRequest.getNom()))
                        .build();
                maladieRepository.save(maladie);
            }
        }

        log.info("MaladieServiceImpl | addMaladie | Maladie Created");
    }

    @Override
    public NameResponse getMaladieById(long maladieId) {
        log.info("MaladieServiceImpl | getMaladieById is called");
        log.info("MaladieServiceImpl | getMaladieById | Get the maladie for maladieId: {}", maladieId);

        Maladie maladie
                = maladieRepository.findById(maladieId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Maladie with given Id not found", NOT_FOUND));

        NameResponse maladieResponse = new NameResponse();

        copyProperties(maladie, maladieResponse);

        log.info("MaladieServiceImpl | getMaladieById | maladieResponse :" + maladieResponse.toString());

        return maladieResponse;
    }

    @Override
    public void editMaladie(NameRequest maladieRequest, long maladieId) {
        log.info("MaladieServiceImpl | editMaladie is called");

        Maladie maladie
                = maladieRepository.findById(maladieId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Maladie with given Id not found",
                        NOT_FOUND
                ));
        maladie.setNom(maladieRequest.getNom());
        maladie.setSlug(Str.slug(maladieRequest.getNom()));
        maladieRepository.save(maladie);

        log.info("MaladieServiceImpl | editMaladie | Maladie Updated");
        log.info("MaladieServiceImpl | editMaladie | Maladie Id : " + maladie.getId());
    }

    @Override
    public void deleteMaladieById(long maladieId) {
        log.info("Maladie id: {}", maladieId);

        if (!maladieRepository.existsById(maladieId)) {
            log.info("Im in this loop {}", !maladieRepository.existsById(maladieId));
            throw new SecretariatCustomException(
                    "Maladie with given with Id: " + maladieId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Maladie with id: {}", maladieId);
        maladieRepository.deleteById(maladieId);
    }
}
