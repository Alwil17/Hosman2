package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Categorie;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.NameRequest;
import com.dopediatrie.hosman.secretariat.payload.response.NameResponse;
import com.dopediatrie.hosman.secretariat.repository.CategorieRepository;
import com.dopediatrie.hosman.secretariat.service.CategorieService;
import com.dopediatrie.hosman.secretariat.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class CategorieServiceImpl implements CategorieService {
    private final CategorieRepository categorieRepository;
    private final String NOT_FOUND = "CATEGORIE_NOT_FOUND";

    @Override
    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    @Override
    public String addCategorieGetSlug(String nom) {
        log.info("CategorieServiceImpl | addCategorieGetSlug is called");
        Categorie categorie;
        if(categorieRepository.existsBySlug(Str.slug(nom)) == null || !categorieRepository.existsBySlug(Str.slug(nom))){
            categorie = Categorie.builder()
                    .nom(nom)
                    .slug(Str.slug(nom))
                    .build();
            categorie = categorieRepository.save(categorie);
        }else{
            categorie = categorieRepository.findBySlugEquals(Str.slug(nom)).orElseThrow();
        }

        log.info("CategorieServiceImpl | addCategorie | Categorie Created");
        log.info("CategorieServiceImpl | addCategorie | Categorie Id : " + categorie.getId());
        return categorie.getSlug();
    }

    @Override
    public long addCategorie(NameRequest categorieRequest) {
        log.info("CategorieServiceImpl | addCategorie is called");
        Categorie categorie;
        if(categorieRepository.existsBySlug(Str.slug(categorieRequest.getNom())) == null || !categorieRepository.existsBySlug(Str.slug(categorieRequest.getNom()))){
            categorie = Categorie.builder()
                    .nom(categorieRequest.getNom())
                    .slug(Str.slug(categorieRequest.getNom()))
                    .build();
            categorie = categorieRepository.save(categorie);
        }else{
            categorie = categorieRepository.findBySlugEquals(Str.slug(categorieRequest.getNom())).orElseThrow();
        }

        log.info("CategorieServiceImpl | addCategorie | Categorie Created");
        log.info("CategorieServiceImpl | addCategorie | Categorie Id : " + categorie.getId());
        return categorie.getId();
    }

    @Override
    public void addCategorie(List<NameRequest> categorieRequests) {
        log.info("CategorieServiceImpl | addCategorie is called");

        for (NameRequest categorieRequest : categorieRequests) {
            Categorie categorie;
            if(categorieRepository.existsBySlug(Str.slug(categorieRequest.getNom())) == null || !categorieRepository.existsBySlug(Str.slug(categorieRequest.getNom()))){
                categorie = Categorie.builder()
                        .nom(categorieRequest.getNom())
                        .slug(Str.slug(categorieRequest.getNom()))
                        .build();
                categorieRepository.save(categorie);
            }
        }

        log.info("CategorieServiceImpl | addCategorie | Categorie Created");
    }

    @Override
    public NameResponse getCategorieById(long categorieId) {
        log.info("CategorieServiceImpl | getCategorieById is called");
        log.info("CategorieServiceImpl | getCategorieById | Get the categorie for categorieId: {}", categorieId);

        Categorie categorie
                = categorieRepository.findById(categorieId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Categorie with given Id not found", NOT_FOUND));

        NameResponse categorieResponse = new NameResponse();

        copyProperties(categorie, categorieResponse);

        log.info("CategorieServiceImpl | getCategorieById | categorieResponse :" + categorieResponse.toString());

        return categorieResponse;
    }

    @Override
    public NameResponse getCategorieBySlug(String slug) {
        log.info("CategorieServiceImpl | getCategorieBySlug is called");
        log.info("CategorieServiceImpl | getCategorieBySlug | Get the categorie for slug: {}", slug);

        Categorie categorie
                = categorieRepository.findBySlugEquals(slug)
                .orElseThrow(
                        () -> new SecretariatCustomException("Categorie with given Id not found", NOT_FOUND));

        NameResponse categorieResponse = new NameResponse();

        copyProperties(categorie, categorieResponse);

        log.info("CategorieServiceImpl | getCategorieById | categorieResponse :" + categorieResponse.toString());
        return categorieResponse;
    }

    @Override
    public void editCategorie(NameRequest categorieRequest, long categorieId) {
        log.info("CategorieServiceImpl | editCategorie is called");

        Categorie categorie
                = categorieRepository.findById(categorieId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Categorie with given Id not found",
                        NOT_FOUND
                ));
        categorie.setNom(categorieRequest.getNom());
        categorie.setSlug(Str.slug(categorieRequest.getNom()));
        categorieRepository.save(categorie);

        log.info("CategorieServiceImpl | editCategorie | Categorie Updated");
        log.info("CategorieServiceImpl | editCategorie | Categorie Id : " + categorie.getId());
    }

    @Override
    public void deleteCategorieById(long categorieId) {
        log.info("Categorie id: {}", categorieId);

        if (!categorieRepository.existsById(categorieId)) {
            log.info("Im in this loop {}", !categorieRepository.existsById(categorieId));
            throw new SecretariatCustomException(
                    "Categorie with given with Id: " + categorieId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Categorie with id: {}", categorieId);
        categorieRepository.deleteById(categorieId);
    }
}
