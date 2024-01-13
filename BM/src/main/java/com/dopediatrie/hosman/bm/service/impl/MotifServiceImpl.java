package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.entity.Motif;
import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.request.MotifRequest;
import com.dopediatrie.hosman.bm.payload.response.MotifResponse;
import com.dopediatrie.hosman.bm.repository.MotifRepository;
import com.dopediatrie.hosman.bm.service.MotifService;
import com.dopediatrie.hosman.bm.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class MotifServiceImpl implements MotifService {
    private final MotifRepository motifRepository;
    private final String NOT_FOUND = "MOTIF_NOT_FOUND";

    @Override
    public List<Motif> getAllMotifs() {
        return motifRepository.findAll(PageRequest.of(0, 200)).toList();
    }

    @Override
    public long addMotif(MotifRequest motifRequest) {
        log.info("MotifServiceImpl | addMotif is called");

        Motif motif
                = Motif.builder()
                .libelle(motifRequest.getLibelle())
                .slug(Str.slug(motifRequest.getLibelle()))
                .build();

        motif = motifRepository.save(motif);

        log.info("MotifServiceImpl | addMotif | Motif Created");
        log.info("MotifServiceImpl | addMotif | Motif Id : " + motif.getId());
        return motif.getId();
    }

    @Override
    public void addMotif(List<MotifRequest> motifRequests) {
        log.info("MotifServiceImpl | addMotif is called");

        for (MotifRequest motifRequest: motifRequests) {
            Motif motif
                    = Motif.builder()
                    .libelle(motifRequest.getLibelle())
                    .slug(Str.slug(motifRequest.getLibelle()))
                    .build();
            motifRepository.save(motif);
        }

        log.info("MotifServiceImpl | addMotif | Motifs Created");
    }

    @Override
    public MotifResponse getMotifById(long motifId) {
        log.info("MotifServiceImpl | getMotifById is called");
        log.info("MotifServiceImpl | getMotifById | Get the motif for motifId: {}", motifId);

        Motif motif
                = motifRepository.findById(motifId)
                .orElseThrow(
                        () -> new BMCustomException("Motif with given Id not found", NOT_FOUND));

        MotifResponse motifResponse = new MotifResponse();

        copyProperties(motif, motifResponse);

        log.info("MotifServiceImpl | getMotifById | motifResponse :" + motifResponse.toString());

        return motifResponse;
    }

    @Override
    public MotifResponse getMotifByLibelle(String libelle) {
        log.info("MotifServiceImpl | getMotifByLibelle is called");
        log.info("MotifServiceImpl | getMotifByLibelle | Get the motif for motifId: {}", libelle);

        Motif motif
                = motifRepository.findBySlugEquals(Str.slug(libelle))
                .orElseThrow(
                        () -> new BMCustomException("Motif with given Slug not found", NOT_FOUND));

        MotifResponse motifResponse = new MotifResponse();

        copyProperties(motif, motifResponse);

        log.info("MotifServiceImpl | getMotifById | motifResponse :" + motifResponse.toString());

        return motifResponse;
    }

    @Override
    public void editMotif(MotifRequest motifRequest, long motifId) {
        log.info("MotifServiceImpl | editMotif is called");

        Motif motif
                = motifRepository.findById(motifId)
                .orElseThrow(() -> new BMCustomException(
                        "Motif with given Id not found",
                        NOT_FOUND
                ));
        motif.setLibelle(motifRequest.getLibelle());
        motif.setSlug(Str.slug(motifRequest.getLibelle()));
        motifRepository.save(motif);

        log.info("MotifServiceImpl | editMotif | Motif Updated");
        log.info("MotifServiceImpl | editMotif | Motif Id : " + motif.getId());
    }

    @Override
    public void deleteMotifById(long motifId) {
        log.info("Motif id: {}", motifId);

        if (!motifRepository.existsById(motifId)) {
            log.info("Im in this loop {}", !motifRepository.existsById(motifId));
            throw new BMCustomException(
                    "Motif with given with Id: " + motifId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Motif with id: {}", motifId);
        motifRepository.deleteById(motifId);
    }

    @Override
    public List<Motif> getMotifByLibelleLike(String libelle) {
        log.info("MotifServiceImpl | getMotifByLibelle is called");

        return motifRepository.findByLibelleLike(libelle);
    }
}
