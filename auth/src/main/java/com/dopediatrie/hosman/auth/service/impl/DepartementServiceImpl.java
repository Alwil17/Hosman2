package com.dopediatrie.hosman.auth.service.impl;

import com.dopediatrie.hosman.auth.entity.Departement;
import com.dopediatrie.hosman.auth.exception.AuthCustomException;
import com.dopediatrie.hosman.auth.payload.request.DepartementRequest;
import com.dopediatrie.hosman.auth.payload.response.DepartementResponse;
import com.dopediatrie.hosman.auth.repository.DepartementRepository;
import com.dopediatrie.hosman.auth.repository.StructureRepository;
import com.dopediatrie.hosman.auth.service.DepartementService;
import com.dopediatrie.hosman.auth.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class DepartementServiceImpl implements DepartementService {
    private final DepartementRepository departementRepository;
    private final StructureRepository structureRepository;
    private final String NOT_FOUND = "DEPARTEMENT_NOT_FOUND";

    @Override
    public List<Departement> getAllDepartements() {
        return departementRepository.findAll();
    }

    @Override
    public long addDepartement(DepartementRequest departementRequest) {
        log.info("DepartementServiceImpl | addDepartement is called");
        Departement departement
                = Departement.builder()
                .libelle(departementRequest.getLibelle())
                .slug(Str.slug(departementRequest.getLibelle()))
                .couleur(departementRequest.getCouleur())
                .code(departementRequest.getCode())
                .structure(structureRepository.findById(departementRequest.getStructure_id()).get())
                .build();

        departement = departementRepository.save(departement);

        log.info("DepartementServiceImpl | addDepartement | Departement Created");
        log.info("DepartementServiceImpl | addDepartement | Departement Id : " + departement.getId());
        return departement.getId();
    }

    @Override
    public void addDepartement(List<DepartementRequest> departementRequests) {
        log.info("DepartementServiceImpl | addDepartement is called");

        for (DepartementRequest departementRequest: departementRequests) {
            Departement departement
                    = Departement.builder()
                    .libelle(departementRequest.getLibelle())
                    .slug(Str.slug(departementRequest.getLibelle()))
                    .couleur(departementRequest.getCouleur())
                    .code(departementRequest.getCode())
                    .structure(structureRepository.findById(departementRequest.getStructure_id()).get())
                    .build();
            departementRepository.save(departement);
        }

        log.info("DepartementServiceImpl | addDepartement | Departements Created");
    }

    @Override
    public DepartementResponse getDepartementById(long departementId) {
        log.info("DepartementServiceImpl | getDepartementById is called");
        log.info("DepartementServiceImpl | getDepartementById | Get the departement for departementId: {}", departementId);

        Departement departement
                = departementRepository.findById(departementId)
                .orElseThrow(
                        () -> new AuthCustomException("Departement with given Id not found", NOT_FOUND));

        DepartementResponse departementResponse = new DepartementResponse();

        copyProperties(departement, departementResponse);

        log.info("DepartementServiceImpl | getDepartementById | departementResponse :" + departementResponse.toString());

        return departementResponse;
    }

    @Override
    public void editDepartement(DepartementRequest departementRequest, long departementId) {
        log.info("DepartementServiceImpl | editDepartement is called");

        Departement departement
                = departementRepository.findById(departementId)
                .orElseThrow(() -> new AuthCustomException(
                        "Departement with given Id not found",
                        NOT_FOUND
                ));
        departement.setLibelle(departementRequest.getLibelle());
        departement.setSlug(Str.slug(departementRequest.getLibelle()));
        departement.setCouleur(departementRequest.getCouleur());
        departement.setCode(departementRequest.getCode());
        departementRepository.save(departement);

        log.info("DepartementServiceImpl | editDepartement | Departement Updated");
        log.info("DepartementServiceImpl | editDepartement | Departement Id : " + departement.getId());
    }

    @Override
    public void deleteDepartementById(long departementId) {
        log.info("Departement id: {}", departementId);

        if (!departementRepository.existsById(departementId)) {
            log.info("Im in this loop {}", !departementRepository.existsById(departementId));
            throw new AuthCustomException(
                    "Departement with given with Id: " + departementId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Departement with id: {}", departementId);
        departementRepository.deleteById(departementId);
    }
}
