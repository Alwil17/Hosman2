package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.entity.Constante;
import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.request.ConstanteRequest;
import com.dopediatrie.hosman.bm.payload.response.ConstanteResponse;
import com.dopediatrie.hosman.bm.repository.ConstanteRepository;
import com.dopediatrie.hosman.bm.service.ConstanteService;
import com.dopediatrie.hosman.bm.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class ConstanteServiceImpl implements ConstanteService {
    private final ConstanteRepository constanteRepository;
    private final String NOT_FOUND = "CONSTANTE_NOT_FOUND";

    @Override
    public List<Constante> getAllConstantes() {
        return constanteRepository.findAll();
    }

    @Override
    public long addConstante(ConstanteRequest constanteRequest) {
        log.info("ConstanteServiceImpl | addConstante is called");

        Constante constante
                = Constante.builder()
                .poids(constanteRequest.getPoids())
                .taille(constanteRequest.getTaille())
                .tension(constanteRequest.getTension())
                .temperature(constanteRequest.getTemperature())
                .poul(constanteRequest.getPoul())
                .perimetre_cranien(constanteRequest.getPerimetre_cranien())
                .frequence_respiratoire(constanteRequest.getFrequence_respiratoire())
                .build();

        constante = constanteRepository.save(constante);

        log.info("ConstanteServiceImpl | addConstante | Constante Created");
        log.info("ConstanteServiceImpl | addConstante | Constante Id : " + constante.getId());
        return constante.getId();
    }

    @Override
    public void addConstante(List<ConstanteRequest> constanteRequests) {
        log.info("ConstanteServiceImpl | addConstante is called");

        for (ConstanteRequest constanteRequest: constanteRequests) {
            Constante constante
                    = Constante.builder()
                    .poids(constanteRequest.getPoids())
                    .taille(constanteRequest.getTaille())
                    .tension(constanteRequest.getTension())
                    .temperature(constanteRequest.getTemperature())
                    .poul(constanteRequest.getPoul())
                    .perimetre_cranien(constanteRequest.getPerimetre_cranien())
                    .frequence_respiratoire(constanteRequest.getFrequence_respiratoire())
                    .build();
            constanteRepository.save(constante);
        }

        log.info("ConstanteServiceImpl | addConstante | Constantes Created");
    }

    @Override
    public ConstanteResponse getConstanteById(long constanteId) {
        log.info("ConstanteServiceImpl | getConstanteById is called");
        log.info("ConstanteServiceImpl | getConstanteById | Get the constante for constanteId: {}", constanteId);

        Constante constante
                = constanteRepository.findById(constanteId)
                .orElseThrow(
                        () -> new BMCustomException("Constante with given Id not found", NOT_FOUND));

        ConstanteResponse constanteResponse = new ConstanteResponse();

        copyProperties(constante, constanteResponse);

        log.info("ConstanteServiceImpl | getConstanteById | constanteResponse :" + constanteResponse.toString());

        return constanteResponse;
    }

    @Override
    public void editConstante(ConstanteRequest constanteRequest, long constanteId) {
        log.info("ConstanteServiceImpl | editConstante is called");

        Constante constante
                = constanteRepository.findById(constanteId)
                .orElseThrow(() -> new BMCustomException(
                        "Constante with given Id not found",
                        NOT_FOUND
                ));
        constante.setPoids(constanteRequest.getPoids());
        constante.setTaille(constanteRequest.getTaille());
        constante.setTension(constanteRequest.getTension());
        constante.setTemperature(constanteRequest.getTemperature());
        constante.setPoul(constanteRequest.getPoul());
        constante.setPerimetre_cranien(constanteRequest.getPerimetre_cranien());
        constante.setFrequence_respiratoire(constanteRequest.getFrequence_respiratoire());
        constanteRepository.save(constante);

        log.info("ConstanteServiceImpl | editConstante | Constante Updated");
        log.info("ConstanteServiceImpl | editConstante | Constante Id : " + constante.getId());
    }

    @Override
    public void deleteConstanteById(long constanteId) {
        log.info("Constante id: {}", constanteId);

        if (!constanteRepository.existsById(constanteId)) {
            log.info("Im in this loop {}", !constanteRepository.existsById(constanteId));
            throw new BMCustomException(
                    "Constante with given with Id: " + constanteId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Constante with id: {}", constanteId);
        constanteRepository.deleteById(constanteId);
    }
}
