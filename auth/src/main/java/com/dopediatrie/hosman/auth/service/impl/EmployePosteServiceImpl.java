package com.dopediatrie.hosman.auth.service.impl;

import com.dopediatrie.hosman.auth.entity.Employe;
import com.dopediatrie.hosman.auth.entity.EmployePoste;
import com.dopediatrie.hosman.auth.entity.EmployePostePK;
import com.dopediatrie.hosman.auth.entity.Poste;
import com.dopediatrie.hosman.auth.exception.AuthCustomException;
import com.dopediatrie.hosman.auth.payload.request.EmployePosteRequest;
import com.dopediatrie.hosman.auth.payload.response.EmployePosteResponse;
import com.dopediatrie.hosman.auth.repository.EmployePosteRepository;
import com.dopediatrie.hosman.auth.repository.EmployeRepository;
import com.dopediatrie.hosman.auth.repository.PosteRepository;
import com.dopediatrie.hosman.auth.service.EmployePosteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmployePosteServiceImpl implements EmployePosteService {
    private final EmployePosteRepository employePosteRepository;
    private final EmployeRepository employeRepository;
    private final PosteRepository posteRepository;
    private final String NOT_FOUND = "EMPLOYE_POSTE_NOT_FOUND";

    @Override
    public List<EmployePoste> getAllEmployePostes() {
        return employePosteRepository.findAll();
    }

    @Override
    public EmployePostePK addEmployePoste(EmployePosteRequest employePosteRequest) {
        log.info("EmployePosteServiceImpl | addEmployePoste is called");

        Employe employe = employeRepository.findByMatriculeEquals(employePosteRequest.getEmploye_matricule()).orElseThrow();
        Poste poste = posteRepository.findByCodeEquals(employePosteRequest.getPoste_code()).orElseThrow();

        EmployePostePK pk = new EmployePostePK();
        pk.employe_id = employe.getId();
        pk.poste_id = poste.getId();

        EmployePoste employePoste
                = EmployePoste.builder()
                .id(pk)
                .employe(employe)
                .poste(poste)
                .build();

        employePoste = employePosteRepository.save(employePoste);

        log.info("EmployePosteServiceImpl | addEmployePoste | EmployePoste Created");
        log.info("EmployePosteServiceImpl | addEmployePoste | EmployePoste Id : " + employePoste.getId());
        return employePoste.getId();
    }

    @Override
    public EmployePosteResponse getEmployePosteById(long employePosteId) {
        log.info("EmployePosteServiceImpl | getEmployePosteById is called");
        log.info("EmployePosteServiceImpl | getEmployePosteById | Get the employePoste for employePosteId: {}", employePosteId);

        EmployePoste employePoste
                = employePosteRepository.findById(employePosteId)
                .orElseThrow(
                        () -> new AuthCustomException("EmployePoste with given Id not found", NOT_FOUND));

        EmployePosteResponse employePosteResponse = new EmployePosteResponse();

        copyProperties(employePoste, employePosteResponse);

        log.info("EmployePosteServiceImpl | getEmployePosteById | employePosteResponse :" + employePosteResponse.toString());

        return employePosteResponse;
    }

    @Override
    public void editEmployePoste(EmployePosteRequest employePosteRequest, long employePosteId) {
        log.info("EmployePosteServiceImpl | editEmployePoste is called");

        EmployePoste employePoste
                = employePosteRepository.findById(employePosteId)
                .orElseThrow(() -> new AuthCustomException(
                        "EmployePoste with given Id not found",
                        NOT_FOUND
                ));
        employePoste.setEmploye(employeRepository.findByMatriculeEquals(employePosteRequest.getEmploye_matricule()).orElseThrow());
        employePoste.setPoste(posteRepository.findByCodeEquals(employePosteRequest.getPoste_code()).orElseThrow());
        employePosteRepository.save(employePoste);

        log.info("EmployePosteServiceImpl | editEmployePoste | EmployePoste Updated");
        log.info("EmployePosteServiceImpl | editEmployePoste | EmployePoste Id : " + employePoste.getId());
    }

    @Override
    public void deleteEmployePosteById(long employePosteId) {
        log.info("EmployePoste id: {}", employePosteId);

        if (!employePosteRepository.existsById(employePosteId)) {
            log.info("Im in this loop {}", !employePosteRepository.existsById(employePosteId));
            throw new AuthCustomException(
                    "EmployePoste with given with Id: " + employePosteId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting EmployePoste with id: {}", employePosteId);
        employePosteRepository.deleteById(employePosteId);
    }
}
