package com.dopediatrie.hosman.auth.service.impl;

import com.dopediatrie.hosman.auth.entity.Employe;
import com.dopediatrie.hosman.auth.exception.AuthCustomException;
import com.dopediatrie.hosman.auth.payload.request.EmployePosteRequest;
import com.dopediatrie.hosman.auth.payload.request.EmployeRequest;
import com.dopediatrie.hosman.auth.payload.response.EmployeResponse;
import com.dopediatrie.hosman.auth.repository.SecteurRepository;
import com.dopediatrie.hosman.auth.repository.EmployeRepository;
import com.dopediatrie.hosman.auth.repository.StructureRepository;
import com.dopediatrie.hosman.auth.service.EmployePosteService;
import com.dopediatrie.hosman.auth.service.EmployeService;
import com.dopediatrie.hosman.auth.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmployeServiceImpl implements EmployeService {
    private final String NOT_FOUND = "EMPLOYE_NOT_FOUND";

    private final EmployeRepository employeRepository;
    private final SecteurRepository secteurRepository;
    private final StructureRepository structureRepository;

    private final EmployePosteService employePosteService;

    @Override
    public List<Employe> getAllEmployes() {
        return employeRepository.findAll();
    }

    @Override
    public long addEmploye(EmployeRequest employeRequest) {
        log.info("EmployeServiceImpl | addEmploye is called");

        String matricule = employeRequest.getMatricule();
        if(matricule == null || matricule.isBlank()){
            matricule = "PISJO_"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")) +"_EXT";
        }
        long structure_id = employeRequest.getStructure_id() != 0 ? employeRequest.getStructure_id() : 1;

        Employe employe
                = Employe.builder()
                .matricule(matricule)
                .nom(employeRequest.getNom())
                .prenoms(employeRequest.getPrenoms())
                .date_naissance(employeRequest.getDate_naissance())
                .lieu_naissance(employeRequest.getLieu_naissance())
                .sexe(employeRequest.getSexe())
                .email(employeRequest.getEmail())
                .adresse(employeRequest.getAdresse())
                .type_piece(employeRequest.getType_piece())
                .no_piece(employeRequest.getNo_piece())
                .provenance(employeRequest.getProvenance())
                .tel1(employeRequest.getTel1())
                .tel2(employeRequest.getTel2())
                .localisation(employeRequest.getLocalisation())
                .autres(employeRequest.getAutres())
                .date_debut(employeRequest.getDate_debut())
                .date_fin(employeRequest.getDate_fin())
                .is_medecin(employeRequest.is_medecin())
                .is_employe(employeRequest.is_employe())
                .is_temporaire(employeRequest.is_temporaire())
                .profession_id(employeRequest.getProfession_id())
                .nationalite_id(employeRequest.getNationalite_id())
                .structure(structureRepository.findById(structure_id).get())
                .build();
        if (employeRequest.getSecteur() != null && !employeRequest.getSecteur().isBlank()){
            employe.setSecteur(secteurRepository.findByCodeEquals(employeRequest.getSecteur()).get());
        }
        employe = employeRepository.save(employe);

        if(employeRequest.getPostes() != null && employeRequest.getPostes().size() > 0){
            for (EmployePosteRequest eMode : employeRequest.getPostes()) {
                if(eMode != null){
                    eMode.setEmploye_matricule(employe.getMatricule());
                    employePosteService.addEmployePoste(eMode);
                }
            }
        }

        log.info("EmployeServiceImpl | addEmploye | Employe Created");
        log.info("EmployeServiceImpl | addEmploye | Employe Id : " + employe.getId());
        return employe.getId();
    }

    @Override
    public String addEmployeGetMatricule(EmployeRequest employeRequest) {
        log.info("EmployeServiceImpl | addEmploye is called");

        String matricule = employeRequest.getMatricule();
        if(matricule == null || matricule.isBlank()){
            matricule = "PISJO_"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")) +"_EXT";
        }
        long structure_id = employeRequest.getStructure_id() != 0 ? employeRequest.getStructure_id() : 1;

        Employe employe
                = Employe.builder()
                .matricule(matricule)
                .nom(employeRequest.getNom())
                .prenoms(employeRequest.getPrenoms())
                .date_naissance(employeRequest.getDate_naissance())
                .lieu_naissance(employeRequest.getLieu_naissance())
                .sexe(employeRequest.getSexe())
                .email(employeRequest.getEmail())
                .adresse(employeRequest.getAdresse())
                .type_piece(employeRequest.getType_piece())
                .no_piece(employeRequest.getNo_piece())
                .provenance(employeRequest.getProvenance())
                .tel1(employeRequest.getTel1())
                .tel2(employeRequest.getTel2())
                .localisation(employeRequest.getLocalisation())
                .autres(employeRequest.getAutres())
                .date_debut(employeRequest.getDate_debut())
                .date_fin(employeRequest.getDate_fin())
                .is_medecin(employeRequest.is_medecin())
                .is_employe(employeRequest.is_employe())
                .is_temporaire(employeRequest.is_temporaire())
                .profession_id(employeRequest.getProfession_id())
                .nationalite_id(employeRequest.getNationalite_id())
                .structure(structureRepository.findById(structure_id).get())
                .build();
        if (employeRequest.getSecteur() != null && !employeRequest.getSecteur().isBlank()){
            employe.setSecteur(secteurRepository.findByCodeEquals(employeRequest.getSecteur()).get());
        }
        employe = employeRepository.save(employe);

        if(employeRequest.getPostes() != null && employeRequest.getPostes().size() > 0){
            for (EmployePosteRequest eMode : employeRequest.getPostes()) {
                if(eMode != null){
                    eMode.setEmploye_matricule(employe.getMatricule());
                    employePosteService.addEmployePoste(eMode);
                }
            }
        }

        log.info("EmployeServiceImpl | addEmploye | Employe Created");
        return matricule;
    }

    @Override
    public void addEmploye(List<EmployeRequest> employeRequests) {
        log.info("EmployeServiceImpl | addEmploye is called");

        for (EmployeRequest employeRequest: employeRequests) {
            String matricule = employeRequest.getMatricule();
            if(matricule == null || matricule.isBlank()){
                matricule = "PISJO"+Str.slug(Str.limit(employeRequest.getNom(), 4)+"-"+Str.limit(employeRequest.getPrenoms(), 4))+"_EXT_"+ Str.limit(Str.slug(employeRequest.getProvenance()), 6);
            }
            long structure_id = employeRequest.getStructure_id() != 0 ? employeRequest.getStructure_id() : 1;

            Employe employe
                    = Employe.builder()
                    .matricule(matricule)
                    .nom(employeRequest.getNom())
                    .prenoms(employeRequest.getPrenoms())
                    .date_naissance(employeRequest.getDate_naissance())
                    .lieu_naissance(employeRequest.getLieu_naissance())
                    .sexe(employeRequest.getSexe())
                    .email(employeRequest.getEmail())
                    .adresse(employeRequest.getAdresse())
                    .type_piece(employeRequest.getType_piece())
                    .no_piece(employeRequest.getNo_piece())
                    .provenance(employeRequest.getProvenance())
                    .tel1(employeRequest.getTel1())
                    .tel2(employeRequest.getTel2())
                    .localisation(employeRequest.getLocalisation())
                    .autres(employeRequest.getAutres())
                    .date_debut(employeRequest.getDate_debut())
                    .date_fin(employeRequest.getDate_fin())
                    .is_medecin(employeRequest.is_medecin())
                    .is_employe(employeRequest.is_employe())
                    .is_temporaire(employeRequest.is_temporaire())
                    .profession_id(employeRequest.getProfession_id())
                    .nationalite_id(employeRequest.getNationalite_id())
                    .structure(structureRepository.findById(structure_id).orElseThrow())
                    .build();
            if (employeRequest.getSecteur() != null && !employeRequest.getSecteur().isBlank()){
                employe.setSecteur(secteurRepository.findByCodeEquals(employeRequest.getSecteur()).get());
            }
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
        employe.setDate_naissance(employeRequest.getDate_naissance());
        employe.setAdresse(employeRequest.getAdresse());
        employe.setLieu_naissance(employeRequest.getLieu_naissance());
        employe.setTel1(employeRequest.getTel1());
        employe.setTel2(employeRequest.getTel2());
        employe.setLocalisation(employeRequest.getLocalisation());
        employe.set_medecin(employeRequest.is_medecin());
        employe.setAutres(employeRequest.getAutres());
        employe.setType_piece(employeRequest.getType_piece());
        employe.setNo_piece(employeRequest.getNo_piece());
        employe.setProvenance(employeRequest.getProvenance());
        employe.setDate_debut(employeRequest.getDate_debut());
        employe.setDate_fin(employeRequest.getDate_fin());
        employe.set_employe(employeRequest.is_employe());
        employe.set_temporaire(employeRequest.is_temporaire());
        employe.setProfession_id(employeRequest.getProfession_id());
        employe.setNationalite_id(employeRequest.getNationalite_id());
        employe.setSecteur(secteurRepository.findByCodeEquals(employeRequest.getSecteur()).get());
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

    @Override
    public EmployeResponse getEmployeByUserId(long userId) {
        log.info("EmployeServiceImpl | getEmployeByUserId is called | userId {}", userId);
        Employe employe = employeRepository.findByUserId(userId).orElseThrow(() -> new AuthCustomException(
                "Employe with given Id not found",
                NOT_FOUND
        ));
        EmployeResponse employeResponse = new EmployeResponse();
        copyProperties(employe, employeResponse);
        return employeResponse;
    }

    @Override
    public List<Employe> getEmployeByType(String type) {
        log.info("EmployeServiceImpl | getEmployeByType is called");
        List<Employe> employes = Collections.emptyList();
        if (type.equals("interne")){
            employes = employeRepository.findByEmployeAndMedecinTrue();
        }else{
            employes = employeRepository.findByEmployeFalseAndMedecinTrue();
        }
        return employes;
    }

    @Override
    public List<Employe> getEmployeByMatricule(String matricule) {
        log.info("EmployeServiceImpl | getEmployeByMatricule is called");
        List<Employe> employes = new java.util.ArrayList<Employe>();
        employes.add(employeRepository.findByMatriculeEquals(matricule).orElseThrow(
                () -> new AuthCustomException("Employe with given Matricule not found", NOT_FOUND)));
        return employes;
    }
}
