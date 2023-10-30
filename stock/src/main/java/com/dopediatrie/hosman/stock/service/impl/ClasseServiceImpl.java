package com.dopediatrie.hosman.stock.service.impl;

import com.dopediatrie.hosman.stock.entity.Classe;
import com.dopediatrie.hosman.stock.exception.StockCustomException;
import com.dopediatrie.hosman.stock.payload.request.ClasseRequest;
import com.dopediatrie.hosman.stock.payload.response.ClasseResponse;
import com.dopediatrie.hosman.stock.repository.ClasseRepository;
import com.dopediatrie.hosman.stock.service.ClasseService;
import com.dopediatrie.hosman.stock.utils.Str;
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

        Classe classe
                = Classe.builder()
                .nom(classeRequest.getNom())
                .slug(Str.slug(classeRequest.getNom()))
                .couleur(classeRequest.getCouleur())
                .build();

        classe = classeRepository.save(classe);

        log.info("ClasseServiceImpl | addClasse | Classe Created");
        log.info("ClasseServiceImpl | addClasse | Classe Id : " + classe.getId());
        return classe.getId();
    }

    @Override
    public void addClasse(List<ClasseRequest> classeRequests) {
        log.info("ClasseServiceImpl | addClasse is called");

        for (ClasseRequest classeRequest: classeRequests) {
            Classe classe
                    = Classe.builder()
                    .nom(classeRequest.getNom())
                    .slug(Str.slug(classeRequest.getNom()))
                    .couleur(classeRequest.getCouleur())
                    .build();
            classeRepository.save(classe);
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
                        () -> new StockCustomException("Classe with given Id not found", NOT_FOUND));

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
                .orElseThrow(() -> new StockCustomException(
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
            throw new StockCustomException(
                    "Classe with given with Id: " + classeId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Classe with id: {}", classeId);
        classeRepository.deleteById(classeId);
    }
}
