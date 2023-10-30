package com.dopediatrie.hosman.auth.service.impl;

import com.dopediatrie.hosman.auth.entity.Poste;
import com.dopediatrie.hosman.auth.exception.AuthCustomException;
import com.dopediatrie.hosman.auth.payload.request.PosteRequest;
import com.dopediatrie.hosman.auth.payload.response.PosteResponse;
import com.dopediatrie.hosman.auth.repository.PosteRepository;
import com.dopediatrie.hosman.auth.repository.DepartementRepository;
import com.dopediatrie.hosman.auth.service.PosteService;
import com.dopediatrie.hosman.auth.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class PosteServiceImpl implements PosteService {
    private final PosteRepository posteRepository;
    private final DepartementRepository departementRepository;
    private final String NOT_FOUND = "POSTE_NOT_FOUND";

    @Override
    public List<Poste> getAllPostes() {
        return posteRepository.findAll();
    }

    @Override
    public long addPoste(PosteRequest posteRequest) {
        log.info("PosteServiceImpl | addPoste is called");
        Poste poste
                = Poste.builder()
                .intitule(posteRequest.getIntitule())
                .slug(Str.slug(posteRequest.getIntitule()))
                .code(posteRequest.getCode())
                .departement(departementRepository.findById(posteRequest.getDepartement_id()).get())
                .build();

        poste = posteRepository.save(poste);

        log.info("PosteServiceImpl | addPoste | Poste Created");
        log.info("PosteServiceImpl | addPoste | Poste Id : " + poste.getId());
        return poste.getId();
    }

    @Override
    public void addPoste(List<PosteRequest> posteRequests) {
        log.info("PosteServiceImpl | addPoste is called");

        for (PosteRequest posteRequest: posteRequests) {
            Poste poste
                    = Poste.builder()
                    .intitule(posteRequest.getIntitule())
                    .slug(Str.slug(posteRequest.getIntitule()))
                    .code(posteRequest.getCode())
                    .departement(departementRepository.findById(posteRequest.getDepartement_id()).get())
                    .build();
            posteRepository.save(poste);
        }

        log.info("PosteServiceImpl | addPoste | Postes Created");
    }

    @Override
    public PosteResponse getPosteById(long posteId) {
        log.info("PosteServiceImpl | getPosteById is called");
        log.info("PosteServiceImpl | getPosteById | Get the poste for posteId: {}", posteId);

        Poste poste
                = posteRepository.findById(posteId)
                .orElseThrow(
                        () -> new AuthCustomException("Poste with given Id not found", NOT_FOUND));

        PosteResponse posteResponse = new PosteResponse();

        copyProperties(poste, posteResponse);

        log.info("PosteServiceImpl | getPosteById | posteResponse :" + posteResponse.toString());

        return posteResponse;
    }

    @Override
    public void editPoste(PosteRequest posteRequest, long posteId) {
        log.info("PosteServiceImpl | editPoste is called");

        Poste poste
                = posteRepository.findById(posteId)
                .orElseThrow(() -> new AuthCustomException(
                        "Poste with given Id not found",
                        NOT_FOUND
                ));
        poste.setIntitule(posteRequest.getIntitule());
        poste.setSlug(Str.slug(posteRequest.getIntitule()));
        poste.setCode(posteRequest.getCode());
        poste.setDepartement(departementRepository.findById(posteRequest.getDepartement_id()).get());
        posteRepository.save(poste);

        log.info("PosteServiceImpl | editPoste | Poste Updated");
        log.info("PosteServiceImpl | editPoste | Poste Id : " + poste.getId());
    }

    @Override
    public void deletePosteById(long posteId) {
        log.info("Poste id: {}", posteId);

        if (!posteRepository.existsById(posteId)) {
            log.info("Im in this loop {}", !posteRepository.existsById(posteId));
            throw new AuthCustomException(
                    "Poste with given with Id: " + posteId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Poste with id: {}", posteId);
        posteRepository.deleteById(posteId);
    }
}
