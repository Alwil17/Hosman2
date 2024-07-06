package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Categorie;
import com.dopediatrie.hosman.secretariat.payload.request.NameRequest;
import com.dopediatrie.hosman.secretariat.payload.response.NameResponse;
import com.dopediatrie.hosman.secretariat.service.CategorieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/annuaire/categories")
@RequiredArgsConstructor
@Log4j2
public class CategorieController {

    private final CategorieService categorieService;

    @GetMapping
    public ResponseEntity<List<Categorie>> getAllCategories() {

        log.info("CategorieController | getAllCategories is called");
        return new ResponseEntity<>(categorieService.getAllCategories(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addCategorie(@RequestBody NameRequest categorieRequest) {

        log.info("CategorieController | addCategorie is called");

        log.info("CategorieController | addCategorie | categorieRequest : " + categorieRequest.toString());

        long categorieId = categorieService.addCategorie(categorieRequest);
        return new ResponseEntity<>(categorieId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NameResponse> getCategorieById(@PathVariable("id") long categorieId) {

        log.info("CategorieController | getCategorieById is called");

        log.info("CategorieController | getCategorieById | categorieId : " + categorieId);

        NameResponse categorieResponse
                = categorieService.getCategorieById(categorieId);
        return new ResponseEntity<>(categorieResponse, HttpStatus.OK);
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<NameResponse> getCategorieBySlug(@PathVariable("slug") String categorieSlug) {
        log.info("CategorieController | getCategorieBySlug is called");
        log.info("CategorieController | getCategorieBySlug | categorieId : " + categorieSlug);

        NameResponse categorieResponse
                = categorieService.getCategorieBySlug(categorieSlug);
        return new ResponseEntity<>(categorieResponse, HttpStatus.OK);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Void> editCategorie(@RequestBody NameRequest categorieRequest,
            @PathVariable("id") long categorieId
    ) {

        log.info("CategorieController | editCategorie is called");

        log.info("CategorieController | editCategorie | categorieId : " + categorieId);

        categorieService.editCategorie(categorieRequest, categorieId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteCategorieById(@PathVariable("id") long categorieId) {
        categorieService.deleteCategorieById(categorieId);
    }
}