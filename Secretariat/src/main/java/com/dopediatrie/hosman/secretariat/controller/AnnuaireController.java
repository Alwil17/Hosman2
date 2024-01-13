package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Annuaire;
import com.dopediatrie.hosman.secretariat.payload.request.AnnuaireRequest;
import com.dopediatrie.hosman.secretariat.payload.response.AnnuaireResponse;
import com.dopediatrie.hosman.secretariat.service.AnnuaireService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/annuaire")
@RequiredArgsConstructor
@Log4j2
public class AnnuaireController {

    private final AnnuaireService annuaireService;

    @GetMapping
    public ResponseEntity<List<Annuaire>> getAllAnnuaires() {

        log.info("AnnuaireController | getAllAnnuaires is called");
        return new ResponseEntity<>(annuaireService.getAllAnnuaires(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addAnnuaire(@RequestBody AnnuaireRequest annuaireRequest) {

        log.info("AnnuaireController | addAnnuaire is called");

        log.info("AnnuaireController | addAnnuaire | annuaireRequest : " + annuaireRequest.toString());

        long annuaireId = annuaireService.addAnnuaire(annuaireRequest);
        return new ResponseEntity<>(annuaireId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnuaireResponse> getAnnuaireById(@PathVariable("id") long annuaireId) {

        log.info("AnnuaireController | getAnnuaireById is called");

        log.info("AnnuaireController | getAnnuaireById | annuaireId : " + annuaireId);

        AnnuaireResponse annuaireResponse
                = annuaireService.getAnnuaireById(annuaireId);
        return new ResponseEntity<>(annuaireResponse, HttpStatus.OK);
    }

    @GetMapping("/bips")
    public ResponseEntity<Map<String, List<AnnuaireResponse>>> getAnnuaireBips() {
        log.info("AnnuaireController | getAnnuaireBips is called");

        Map<String, List<AnnuaireResponse>> responses = annuaireService.getAnnuaireBips();

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Annuaire>> getAnnuaireBySearch(@RequestParam(value = "q", required = false) String searchString, @RequestParam(value = "categorie", required = false) @Schema(type = "string", description = "Pas le nom de la catégorie mais le slug") String categorie_slug) {
        log.info("AnnuaireController | getAnnuaireBySearch is called");
        List<Annuaire> annuaires = Collections.emptyList();
        boolean checkCat = (categorie_slug != null && !categorie_slug.isBlank());
        boolean checkQ = (searchString != null && !searchString.isBlank());
        if(checkCat && checkQ){
            annuaires = annuaireService.getAnnuaireByCategorieAndQueryString(categorie_slug, searchString);
        }else if(checkCat == true && checkQ == false){
            annuaires = annuaireService.getAnnuaireByCategorie(categorie_slug);
        }else if(checkCat == false && checkQ == true){
            annuaires = annuaireService.getAnnuaireByQueryString(searchString);
        }else {
            annuaires = annuaireService.getAllAnnuaires();
        }

        return new ResponseEntity<>(annuaires, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> editAnnuaire(@RequestBody AnnuaireRequest annuaireRequest,
            @PathVariable("id") long annuaireId
    ) {

        log.info("AnnuaireController | editAnnuaire is called");

        log.info("AnnuaireController | editAnnuaire | annuaireId : " + annuaireId);

        annuaireService.editAnnuaire(annuaireRequest, annuaireId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteAnnuaireById(@PathVariable("id") long annuaireId) {
        annuaireService.deleteAnnuaireById(annuaireId);
    }
}