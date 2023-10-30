package com.dopediatrie.hosman.auth.service.impl;

import com.dopediatrie.hosman.auth.entity.Employe;
import com.dopediatrie.hosman.auth.exception.AuthCustomException;
import com.dopediatrie.hosman.auth.payload.request.EmployePosteRequest;
import com.dopediatrie.hosman.auth.payload.request.EmployeRequest;
import com.dopediatrie.hosman.auth.payload.response.EmployeResponse;
import com.dopediatrie.hosman.auth.repository.DepartementRepository;
import com.dopediatrie.hosman.auth.repository.EmployeRepository;
import com.dopediatrie.hosman.auth.repository.StructureRepository;
import com.dopediatrie.hosman.auth.service.EmployePosteService;
import com.dopediatrie.hosman.auth.service.EmployeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmployeServiceImpl implements EmployeService {
    private final String NOT_FOUND = "EMPLOYE_NOT_FOUND";

    private final EmployeRepository employeRepository;
    private final DepartementRepository departementRepository;
    private final StructureRepository structureRepository;

    private final EmployePosteService employePosteService;

    @Override
    public List<Employe> getAllEmployes() {
        return employeRepository.findAll();
    }

    @Override
    public long addEmploye(EmployeRequest employeRequest) {
        log.info("EmployeServiceImpl | addEmploye is called");

        Employe employe
                = Employe.builder()
                .matricule(employeRequest.getMatricule())
                .nom(employeRequest.getNom())
                .prenoms(employeRequest.getPrenoms())
                .sexe(employeRequest.getSexe())
                .email(employeRequest.getEmail())
                .adresse(employeRequest.getAdresse())
                .tel1(employeRequest.getTel1())
                .tel2(employeRequest.getTel2())
                .localisation(employeRequest.getLocalisation())
                .autres(employeRequest.getAutres())
                .date_debut(employeRequest.getDate_debut())
                .date_fin(employeRequest.getDate_fin())
                .is_employe(employeRequest.is_employe())
                .is_temporaire(employeRequest.is_temporaire())
                .profession_id(employeRequest.getProfession_id())
                .nationalite_id(employeRequest.getNationalite_id())
                .departement(departementRepository.findById(employeRequest.getDepartement_id()).get())
                .structure(structureRepository.findById(employeRequest.getStructure_id()).get())
                .build();

        employe = employeRepository.save(employe);

        for (EmployePosteRequest eMode : employeRequest.getPostes()) {
            eMode.setEmploye_id(employe.getId());
            employePosteService.addEmployePoste(eMode);
        }

        log.info("EmployeServiceImpl | addEmploye | Employe Created");
        log.info("EmployeServiceImpl | addEmploye | Employe Id : " + employe.getId());
        return employe.getId();
    }

    @Override
    public void addEmploye(List<EmployeRequest> employeRequests) {
        log.info("EmployeServiceImpl | addEmploye is called");

        for (EmployeRequest employeRequest: employeRequests) {
            Employe employe
                    = Employe.builder()
                    .matricule(employeRequest.getMatricule())
                    .nom(employeRequest.getNom())
                    .prenoms(employeRequest.getPrenoms())
                    .sexe(employeRequest.getSexe())
                    .email(employeRequest.getEmail())
                    .adresse(employeRequest.getAdresse())
                    .tel1(employeRequest.getTel1())
                    .tel2(employeRequest.getTel2())
                    .localisation(employeRequest.getLocalisation())
                    .autres(employeRequest.getAutres())
                    .date_debut(employeRequest.getDate_debut())
                    .date_fin(employeRequest.getDate_fin())
                    .is_employe(employeRequest.is_employe())
                    .is_temporaire(employeRequest.is_temporaire())
                    .profession_id(employeRequest.getProfession_id())
                    .nationalite_id(employeRequest.getNationalite_id())
                    .departement(departementRepository.findById(employeRequest.getDepartement_id()).get())
                    .structure(structureRepository.findById(employeRequest.getStructure_id()).get())
                    .build();
            employeRepository.save(employe);
        }

        log.info("EmployeServiceImpl | addEmploye | Employes Created");
    }

    @Override
    public EmployeResponse getEmployeById(long employeId) {
        log.info("EmployeServiceImpl | getEmployeById is called");
        log.info("EmployeServiceImpl | getEmployeById | Get the employe for employeId: {}", employeId);

        Employe employe
                = employeRepository.findById(employeId)
                .orElseThrow(
                        () -> new AuthCustomException("Employe with given Id not found", NOT_FOUND));

        EmployeResponse employeResponse = new EmployeResponse();

        copyProperties(employe, employeResponse);

        log.info("EmployeServiceImpl | getEmployeById | employeResponse :" + employeResponse.toString());

        return employeResponse;
    }

    @Override
    public void editEmploye(EmployeRequest employeRequest, long employeId) {
        log.info("EmployeServiceImpl | editEmploye is called");

        Employe employe
                = employeRepository.findById(employeId)
                .orElseThrow(() -> new AuthCustomException(
                        "Employe with given Id not found",
                        NOT_FOUND
                ));
        employe.setMatricule(employeRequest.getMatricule());
        employe.setNom(employeRequest.getNom());
        employe.setPrenoms(employeRequest.getPrenoms());
        employe.setSexe(employeRequest.getSexe());
        employe.setEmail(employeRequest.getEmail());
        employe.setAdresse(employeRequest.getAdresse());
        employe.setTel1(employeRequest.getTel1());
        employe.setTel2(employeRequest.getTel2());
        employe.setLocalisation(employeRequest.getLocalisation());
        employe.setAutres(employeRequest.getAutres());
        employe.setDate_debut(employeRequest.getDate_debut());
        employe.setDate_fin(employeRequest.getDate_fin());
        employe.set_employe(employeRequest.is_employe());
        employe.set_temporaire(employeRequest.is_temporaire());
        employe.setProfession_id(employeRequest.getProfession_id());
        employe.setNationalite_id(employeRequest.getNationalite_id());
        employe.setDepartement(departementRepository.findById(employeRequest.getDepartement_id()).get());
        employe.setStructure(structureRepository.findById(employeRequest.getStructure_id()).get());
        employeRepository.save(employe);

        log.info("EmployeServiceImpl | editEmploye | Employe Updated");
        log.info("EmployeServiceImpl | editEmploye | Employe Id : " + employe.getId());
    }

    @Override
    public void deleteEmployeById(long employeId) {
        log.info("Employe id: {}", employeId);

        if (!employeRepository.existsById(employeId)) {
            log.info("Im in this loop {}", !employeRepository.existsById(employeId));
            throw new AuthCustomException(
                    "Employe with given with Id: " + employeId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Employe with id: {}", employeId);
        employeRepository.deleteById(employeId);
    }
}
