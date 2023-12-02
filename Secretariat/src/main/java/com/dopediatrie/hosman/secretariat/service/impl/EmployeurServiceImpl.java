package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Employeur;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.EmployeurRequest;
import com.dopediatrie.hosman.secretariat.payload.response.EmployeurResponse;
import com.dopediatrie.hosman.secretariat.repository.EmployeurRepository;
import com.dopediatrie.hosman.secretariat.service.EmployeurService;
import com.dopediatrie.hosman.secretariat.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmployeurServiceImpl implements EmployeurService {
    private final EmployeurRepository employeurRepository;
    private final String NOT_FOUND = "EMPLOYEUR_NOT_FOUND";

    @Override
    public List<Employeur> getAllEmployeurs() {
        return employeurRepository.findAll();
    }

    @Override
    public long addEmployeur(EmployeurRequest employeurRequest) {
        log.info("EmployeurServiceImpl | addEmployeur is called");

        Employeur employeur;
        if(employeurRepository.existsByNom(employeurRequest.getNom()) == null || !employeurRepository.existsByNom(employeurRequest.getNom())){
            employeur = Employeur.builder()
                    .nom(employeurRequest.getNom())
                    .email(employeurRequest.getEmail())
                    .tel1(employeurRequest.getTel1())
                    .tel2(employeurRequest.getTel2())
                    .build();
            employeur = employeurRepository.save(employeur);
        }else{
            employeur = employeurRepository.findByNom(employeurRequest.getNom()).orElseThrow();
            editEmployeur(employeurRequest, employeur.getId());
        }

        log.info("EmployeurServiceImpl | addEmployeur | Employeur Created");
        log.info("EmployeurServiceImpl | addEmployeur | Employeur Id : " + employeur.getId());
        return employeur.getId();
    }

    @Override
    public void addEmployeur(List<EmployeurRequest> employeurRequests) {
        log.info("EmployeurServiceImpl | addEmployeur is called");

        for (EmployeurRequest employeurRequest : employeurRequests
             ) {
            Employeur employeur;
            if(employeurRepository.existsByNom(employeurRequest.getNom()) == null || !employeurRepository.existsByNom(employeurRequest.getNom())){
                employeur = Employeur.builder()
                        .nom(employeurRequest.getNom())
                        .email(employeurRequest.getEmail())
                        .tel1(employeurRequest.getTel1())
                        .tel2(employeurRequest.getTel2())
                        .build();
                employeurRepository.save(employeur);
            }
        }

        log.info("EmployeurServiceImpl | addEmployeur | Employeur Created");
    }

    @Override
    public EmployeurResponse getEmployeurById(long employeurId) {
        log.info("EmployeurServiceImpl | getEmployeurById is called");
        log.info("EmployeurServiceImpl | getEmployeurById | Get the employeur for employeurId: {}", employeurId);

        Employeur employeur
                = employeurRepository.findById(employeurId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Employeur with given Id not found", NOT_FOUND));

        EmployeurResponse employeurResponse = new EmployeurResponse();

        copyProperties(employeur, employeurResponse);

        log.info("EmployeurServiceImpl | getEmployeurById | employeurResponse :" + employeurResponse.toString());

        return employeurResponse;
    }

    @Override
    public void editEmployeur(EmployeurRequest employeurRequest, long employeurId) {
        log.info("EmployeurServiceImpl | editEmployeur is called");

        Employeur employeur
                = employeurRepository.findById(employeurId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Employeur with given Id not found",
                        NOT_FOUND
                ));
        employeur.setNom(employeurRequest.getNom());
        employeur.setEmail(employeurRequest.getEmail());
        employeur.setTel1(employeurRequest.getTel1());
        employeur.setTel2(employeurRequest.getTel2());
        employeurRepository.save(employeur);

        log.info("EmployeurServiceImpl | editEmployeur | Employeur Updated");
        log.info("EmployeurServiceImpl | editEmployeur | Employeur Id : " + employeur.getId());
    }

    @Override
    public void deleteEmployeurById(long employeurId) {
        log.info("Employeur id: {}", employeurId);

        if (!employeurRepository.existsById(employeurId)) {
            log.info("Im in this loop {}", !employeurRepository.existsById(employeurId));
            throw new SecretariatCustomException(
                    "Employeur with given with Id: " + employeurId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Employeur with id: {}", employeurId);
        employeurRepository.deleteById(employeurId);
    }
}
