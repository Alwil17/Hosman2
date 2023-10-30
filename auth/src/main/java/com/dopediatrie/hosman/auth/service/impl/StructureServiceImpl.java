package com.dopediatrie.hosman.auth.service.impl;

import com.dopediatrie.hosman.auth.entity.Structure;
import com.dopediatrie.hosman.auth.exception.AuthCustomException;
import com.dopediatrie.hosman.auth.payload.request.StructureRequest;
import com.dopediatrie.hosman.auth.payload.response.StructureResponse;
import com.dopediatrie.hosman.auth.repository.StructureRepository;
import com.dopediatrie.hosman.auth.service.StructureService;
import com.dopediatrie.hosman.auth.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class StructureServiceImpl implements StructureService {
    private final StructureRepository structureRepository;
    private final String NOT_FOUND = "STRUCTURE_NOT_FOUND";

    @Override
    public List<Structure> getAllStructures() {
        return structureRepository.findAll();
    }

    @Override
    public long addStructure(StructureRequest structureRequest) {
        log.info("StructureServiceImpl | addStructure is called");
        Structure structure
                = Structure.builder()
                .nom(structureRequest.getNom())
                .slug(Str.slug(structureRequest.getNom()))
                .raison_sociale(structureRequest.getRaison_sociale())
                .email(structureRequest.getEmail())
                .adresse(structureRequest.getAdresse())
                .adresse2(structureRequest.getAdresse2())
                .adresse3(structureRequest.getAdresse3())
                .tel1(structureRequest.getTel1())
                .tel2(structureRequest.getTel2())
                .tel3(structureRequest.getTel3())
                .bp(structureRequest.getBp())
                .fax(structureRequest.getFax())
                .logo(structureRequest.getLogo())
                .baniere(structureRequest.getBaniere())
                .slogan(structureRequest.getSlogan())
                .sigle(structureRequest.getSigle())
                .cnss(structureRequest.getCnss())
                .rib(structureRequest.getRib())
                .created_at(LocalDateTime.now())
                .build();

        structure = structureRepository.save(structure);

        log.info("StructureServiceImpl | addStructure | Structure Created");
        log.info("StructureServiceImpl | addStructure | Structure Id : " + structure.getId());
        return structure.getId();
    }

    @Override
    public void addStructure(List<StructureRequest> structureRequests) {
        log.info("StructureServiceImpl | addStructure is called");

        for (StructureRequest structureRequest: structureRequests) {
            Structure structure
                    = Structure.builder()
                    .nom(structureRequest.getNom())
                    .slug(Str.slug(structureRequest.getNom()))
                    .raison_sociale(structureRequest.getRaison_sociale())
                    .email(structureRequest.getEmail())
                    .adresse(structureRequest.getAdresse())
                    .adresse2(structureRequest.getAdresse2())
                    .adresse3(structureRequest.getAdresse3())
                    .tel1(structureRequest.getTel1())
                    .tel2(structureRequest.getTel2())
                    .tel3(structureRequest.getTel3())
                    .bp(structureRequest.getBp())
                    .fax(structureRequest.getFax())
                    .logo(structureRequest.getLogo())
                    .baniere(structureRequest.getBaniere())
                    .slogan(structureRequest.getSlogan())
                    .sigle(structureRequest.getSigle())
                    .cnss(structureRequest.getCnss())
                    .rib(structureRequest.getRib())
                    .created_at(LocalDateTime.now())
                    .build();
            structureRepository.save(structure);
        }

        log.info("StructureServiceImpl | addStructure | Structures Created");
    }

    @Override
    public StructureResponse getStructureById(long structureId) {
        log.info("StructureServiceImpl | getStructureById is called");
        log.info("StructureServiceImpl | getStructureById | Get the structure for structureId: {}", structureId);

        Structure structure
                = structureRepository.findById(structureId)
                .orElseThrow(
                        () -> new AuthCustomException("Structure with given Id not found", NOT_FOUND));

        StructureResponse structureResponse = new StructureResponse();

        copyProperties(structure, structureResponse);

        log.info("StructureServiceImpl | getStructureById | structureResponse :" + structureResponse.toString());

        return structureResponse;
    }

    @Override
    public void editStructure(StructureRequest structureRequest, long structureId) {
        log.info("StructureServiceImpl | editStructure is called");

        Structure structure
                = structureRepository.findById(structureId)
                .orElseThrow(() -> new AuthCustomException(
                        "Structure with given Id not found",
                        NOT_FOUND
                ));
        structure.setNom(structureRequest.getNom());
        structure.setSlug(Str.slug(structureRequest.getNom()));
        structure.setRaison_sociale(structureRequest.getRaison_sociale());
        structure.setEmail(structureRequest.getEmail());
        structure.setAdresse(structureRequest.getAdresse());
        structure.setAdresse2(structureRequest.getAdresse2());
        structure.setAdresse3(structureRequest.getAdresse3());
        structure.setTel1(structureRequest.getTel1());
        structure.setTel2(structureRequest.getTel2());
        structure.setTel3(structureRequest.getTel3());
        structure.setBp(structureRequest.getBp());
        structure.setFax(structureRequest.getFax());
        structure.setLogo(structureRequest.getLogo());
        structure.setBaniere(structureRequest.getBaniere());
        structure.setSlogan(structureRequest.getSlogan());
        structure.setSigle(structureRequest.getSigle());
        structure.setCnss(structureRequest.getCnss());
        structure.setRib(structureRequest.getRib());
        structure.setUpdated_at(LocalDateTime.now());
        structureRepository.save(structure);

        log.info("StructureServiceImpl | editStructure | Structure Updated");
        log.info("StructureServiceImpl | editStructure | Structure Id : " + structure.getId());
    }

    @Override
    public void deleteStructureById(long structureId) {
        log.info("Structure id: {}", structureId);

        if (!structureRepository.existsById(structureId)) {
            log.info("Im in this loop {}", !structureRepository.existsById(structureId));
            throw new AuthCustomException(
                    "Structure with given with Id: " + structureId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Structure with id: {}", structureId);
        structureRepository.deleteById(structureId);
    }
}
