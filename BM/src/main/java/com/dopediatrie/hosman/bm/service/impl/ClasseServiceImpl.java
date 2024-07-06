package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.entity.Classe;
import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.request.ClasseRequest;
import com.dopediatrie.hosman.bm.payload.response.ClasseResponse;
import com.dopediatrie.hosman.bm.repository.ClasseRepository;
import com.dopediatrie.hosman.bm.service.ClasseService;
import com.dopediatrie.hosman.bm.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class ClasseServiceImpl implements ClasseService {
    private final ClasseRepository classeRepository;
    private final String NOT_FOUND = "CLASSE_NOT_FOUND";

    @Override
    public List<Classe> getAllClasses() {
        return classeRepository.findAll();
    }

    @Override
    public long addClasse(ClasseRequest classeRequest) {
        log.info("ClasseServiceImpl | addClasse is called");

        Classe classe;
        if(classeRepository.existsBySlug(Str.slug(classeRequest.getNom()))){
            classe = classeRepository.findBySlug(Str.slug(classeRequest.getNom())).orElseThrow();
            editClasse(classeRequest, classe.getId());
        }else{
            classe = Classe.builder()
                    .nom(classeRequest.getNom())
                    .slug(Str.slug(classeRequest.getNom()))
                    .couleur(classeRequest.getCouleur())
                    .build();
            classe = classeRepository.save(classe);

        }

        log.info("ClasseServiceImpl | addClasse | Classe Created");
        log.info("ClasseServiceImpl | addClasse | Classe Id : " + classe.getId());
        return classe.getId();
    }

    @Override
    public void addClasse(List<ClasseRequest> classeRequests) {
        log.info("ClasseServiceImpl | addClasse is called");

        for (ClasseRequest classeRequest: classeRequests) {
            Classe classe;
            if(classeRepository.existsBySlug(Str.slug(classeRequest.getNom()))){
                classe = classeRepository.findBySlug(Str.slug(classeRequest.getNom())).orElseThrow();
                editClasse(classeRequest, classe.getId());
            }else{
                classe = Classe.builder()
                        .nom(classeRequest.getNom())
                        .slug(Str.slug(classeRequest.getNom()))
                        .couleur(classeRequest.getCouleur())
                        .build();
                classeRepository.save(classe);

            }
        }

        log.info("ClasseServiceImpl | addClasse | Classes Created");
    }

    @Override
    public ClasseResponse getClasseById(long classeId) {
        log.info("ClasseServiceImpl | getClasseById is called");
        log.info("ClasseServiceImpl | getClasseById | Get the classe for classeId: {}", classeId);

        Classe classe
                = classeRepository.findById(classeId)
                .orElseThrow(
                        () -> new BMCustomException("Classe with given Id not found", NOT_FOUND));

        ClasseResponse classeResponse = new ClasseResponse();

        copyProperties(classe, classeResponse);

        log.info("ClasseServiceImpl | getClasseById | classeResponse :" + classeResponse.toString());

        return classeResponse;
    }

    @Override
    public void editClasse(ClasseRequest classeRequest, long classeId) {
        log.info("ClasseServiceImpl | editClasse is called");

        Classe classe
                = classeRepository.findById(classeId)
                .orElseThrow(() -> new BMCustomException(
                        "Classe with given Id not found",
                        NOT_FOUND
                ));
        classe.setNom(classeRequest.getNom());
        classe.setSlug(Str.slug(classeRequest.getNom()));
        classe.setCouleur(classeRequest.getCouleur());
        classeRepository.save(classe);

        log.info("ClasseServiceImpl | editClasse | Classe Updated");
        log.info("ClasseServiceImpl | editClasse | Classe Id : " + classe.getId());
    }

    @Override
    public void deleteClasseById(long classeId) {
        log.info("Classe id: {}", classeId);

        if (!classeRepository.existsById(classeId)) {
            log.info("Im in this loop {}", !classeRepository.existsById(classeId));
            throw new BMCustomException(
                    "Classe with given with Id: " + classeId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Classe with id: {}", classeId);
        classeRepository.deleteById(classeId);
    }

    @Override
    public List<Classe> getClasseByQueryString(String q) {
        log.info("ClasseServiceImpl | getClasseByQueryString is called");
        return classeRepository.findByQueryString(q);
    }
}
