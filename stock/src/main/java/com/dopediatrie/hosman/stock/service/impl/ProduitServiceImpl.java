package com.dopediatrie.hosman.stock.service.impl;

import com.dopediatrie.hosman.stock.entity.Produit;
import com.dopediatrie.hosman.stock.exception.StockCustomException;
import com.dopediatrie.hosman.stock.payload.request.*;
import com.dopediatrie.hosman.stock.payload.response.ProduitResponse;
import com.dopediatrie.hosman.stock.repository.AgenceRepository;
import com.dopediatrie.hosman.stock.repository.DelegueRepository;
import com.dopediatrie.hosman.stock.repository.LaboratoireRepository;
import com.dopediatrie.hosman.stock.repository.ProduitRepository;
import com.dopediatrie.hosman.stock.service.*;
import com.dopediatrie.hosman.stock.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProduitServiceImpl implements ProduitService {
    private final String NOT_FOUND = "PRODUIT_NOT_FOUND";
    private final ProduitRepository produitRepository;

    private final AgenceService agenceService;
    private final LaboratoireService laboratoireService;
    private final DelegueService delegueService;
    private final IndicationService indicationService;
    private final PosologieService posologieService;
    private final FormeService formeService;
    private final ContreIndicationService contreIndicationService;
    private final EffetSecondaireService effetSecondaireService;
    private final ProduitClasseService produitClasseService;

    private final AgenceRepository agenceRepository;
    private final LaboratoireRepository laboratoireRepository;
    private final DelegueRepository delegueRepository;

    @Override
    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    @Override
    public long addProduit(ProduitRequest produitRequest) {
        log.info("ProduitServiceImpl | addProduit is called");

        long agence_id = (produitRequest.getAgence() != null) ? agenceService.addAgence(produitRequest.getAgence()) : 0;
        long laboratoire_id = (produitRequest.getLaboratoire() != null) ? laboratoireService.addLaboratoire(produitRequest.getLaboratoire()) : 0;
        long delegue_id = (produitRequest.getDelegue() != null) ? delegueService.addDelegue(produitRequest.getDelegue()) : 0;

        Produit produit
                = Produit.builder()
                .nom(produitRequest.getNom())
                .code(Str.slug(produitRequest.getCode()))
                .code_acte(Str.slug(produitRequest.getCode_acte()))
                .nom_officiel(produitRequest.getNom_officiel())
                .dci(produitRequest.getDci())
                .autre(produitRequest.getAutre())
                .prix(produitRequest.getPrix())
                .build();

        if(agence_id != 0)
            produit.setAgence(agenceRepository.findById(agence_id).orElseThrow());
        if(laboratoire_id != 0)
            produit.setLaboratoire(laboratoireRepository.findById(laboratoire_id).orElseThrow());
        if(delegue_id != 0)
            produit.setDelegue(delegueRepository.findById(delegue_id).orElseThrow());

        produit = produitRepository.save(produit);

        // indications
        if((produitRequest.getIndications() != null) && (produitRequest.getIndications().size() > 0)){
            for (NameRequest indicationRequest : produitRequest.getIndications()) {
                indicationRequest.setProduit_id(produit.getId());
                indicationService.addIndication(indicationRequest);
            }
        }
        // contre-indication
        if((produitRequest.getContre_indications() != null) && (produitRequest.getContre_indications().size() > 0)){
            for (NameRequest contreIndicationRequest : produitRequest.getContre_indications()) {
                contreIndicationRequest.setProduit_id(produit.getId());
                contreIndicationService.addContreIndication(contreIndicationRequest);
            }
        }
        // effet-secondaire
        if((produitRequest.getEffet_secondaires() != null) && (produitRequest.getEffet_secondaires().size() > 0)){
            for (NameRequest effetSecondaireRequest : produitRequest.getEffet_secondaires()) {
                effetSecondaireRequest.setProduit_id(produit.getId());
                effetSecondaireService.addEffetSecondaire(effetSecondaireRequest);
            }
        }
        // posologie
        if((produitRequest.getPosologies() != null) && (produitRequest.getPosologies().size() > 0)){
            for (PosologieRequest posologieRequest : produitRequest.getPosologies()) {
                posologieRequest.setProduit_id(produit.getId());
                posologieService.addPosologie(posologieRequest);
            }
        }
        // forme
        if((produitRequest.getFormes() != null) && (produitRequest.getFormes().size() > 0)){
            for (FormeRequest formeRequest : produitRequest.getFormes()) {
                formeRequest.setProduit_id(produit.getId());
                formeService.addForme(formeRequest);
            }
        }
        // produit-classe
        if((produitRequest.getClasses() != null) && (produitRequest.getClasses().size() > 0)){
            for (ProduitClasseRequest produitClasseRequest : produitRequest.getClasses()) {
                produitClasseRequest.setProduit_id(produit.getId());
                produitClasseService.addProduitClasse(produitClasseRequest);
            }
        }

        log.info("ProduitServiceImpl | addProduit | Produit Created");
        log.info("ProduitServiceImpl | addProduit | Produit Id : " + produit.getId());
        return produit.getId();
    }

    @Override
    public void addProduit(List<ProduitRequest> produitRequests) {
        log.info("ProduitServiceImpl | addProduit is called");

        for (ProduitRequest produitRequest: produitRequests) {
            long agence_id = (produitRequest.getAgence() != null) ? agenceService.addAgence(produitRequest.getAgence()) : 0;
            long laboratoire_id = (produitRequest.getLaboratoire() != null) ? laboratoireService.addLaboratoire(produitRequest.getLaboratoire()) : 0;
            long delegue_id = (produitRequest.getDelegue() != null) ? delegueService.addDelegue(produitRequest.getDelegue()) : 0;

            Produit produit
                    = Produit.builder()
                    .nom(produitRequest.getNom())
                    .code(Str.slug(produitRequest.getCode()))
                    .code_acte(Str.slug(produitRequest.getCode_acte()))
                    .nom_officiel(produitRequest.getNom_officiel())
                    .dci(produitRequest.getDci())
                    .prix(produitRequest.getPrix())
                    .autre(produitRequest.getAutre())
                    .build();

            if(agence_id != 0)
                produit.setAgence(agenceRepository.findById(agence_id).orElseThrow());
            if(laboratoire_id != 0)
                produit.setLaboratoire(laboratoireRepository.findById(laboratoire_id).orElseThrow());
            if(delegue_id != 0)
                produit.setDelegue(delegueRepository.findById(delegue_id).orElseThrow());

            produit = produitRepository.save(produit);

            // indications
            if((produitRequest.getIndications() != null) && (produitRequest.getIndications().size() > 0)){
                for (NameRequest indicationRequest : produitRequest.getIndications()) {
                    indicationRequest.setProduit_id(produit.getId());
                    indicationService.addIndication(indicationRequest);
                }
            }
            // contre-indication
            if((produitRequest.getContre_indications() != null) && (produitRequest.getContre_indications().size() > 0)){
                for (NameRequest contreIndicationRequest : produitRequest.getContre_indications()) {
                    contreIndicationRequest.setProduit_id(produit.getId());
                    contreIndicationService.addContreIndication(contreIndicationRequest);
                }
            }
            // effet-secondaire
            if((produitRequest.getEffet_secondaires() != null) && (produitRequest.getEffet_secondaires().size() > 0)){
                for (NameRequest effetSecondaireRequest : produitRequest.getEffet_secondaires()) {
                    effetSecondaireRequest.setProduit_id(produit.getId());
                    effetSecondaireService.addEffetSecondaire(effetSecondaireRequest);
                }
            }
            // posologie
            if((produitRequest.getPosologies() != null) && (produitRequest.getPosologies().size() > 0)){
                for (PosologieRequest posologieRequest : produitRequest.getPosologies()) {
                    posologieRequest.setProduit_id(produit.getId());
                    posologieService.addPosologie(posologieRequest);
                }
            }
            // forme
            if((produitRequest.getFormes() != null) && (produitRequest.getFormes().size() > 0)){
                for (FormeRequest formeRequest : produitRequest.getFormes()) {
                    formeRequest.setProduit_id(produit.getId());
                    formeService.addForme(formeRequest);
                }
            }
            // produit-classe
            if((produitRequest.getClasses() != null) && (produitRequest.getClasses().size() > 0)){
                for (ProduitClasseRequest produitClasseRequest : produitRequest.getClasses()) {
                    produitClasseRequest.setProduit_id(produit.getId());
                    produitClasseService.addProduitClasse(produitClasseRequest);
                }
            }

        }

        log.info("ProduitServiceImpl | addProduit | Produits Created");
    }

    @Override
    public List<Produit> getProduitByNom(String nom) {
        log.info("ProduitServiceImpl | getProduitByNom is called");

        return produitRepository.findByNomLike(nom);
    }

    @Override
    public List<Produit> getProduitByDci(String dci) {
        log.info("ProduitServiceImpl | getProduitByDci is called");

        return produitRepository.findByDciLike(dci);
    }

    @Override
    public List<Produit> getProduitByClasse(String classe) {
        log.info("ProduitServiceImpl | getProduitByClasse is called");

        return produitRepository.findByClasseLike(classe);
    }

    @Override
    public List<Produit> getProduitByCodeActe(String code_acte) {
        log.info("ProduitServiceImpl | getProduitByCodeActe is called");

        return produitRepository.findByCodeActeLike(code_acte);
    }

    @Override
    public List<Produit> getProduitByIndication(String indication) {
        log.info("ProduitServiceImpl | getProduitByIndication is called");

        return produitRepository.findByIndicationLike(indication);
    }

    @Override
    public ProduitResponse getProduitById(long produitId) {
        log.info("ProduitServiceImpl | getProduitById is called");
        log.info("ProduitServiceImpl | getProduitById | Get the produit for produitId: {}", produitId);

        Produit produit
                = produitRepository.findById(produitId)
                .orElseThrow(
                        () -> new StockCustomException("Produit with given Id not found", NOT_FOUND));

        ProduitResponse produitResponse = new ProduitResponse();

        copyProperties(produit, produitResponse);

        log.info("ProduitServiceImpl | getProduitById | produitResponse :" + produitResponse.toString());

        return produitResponse;
    }

    @Override
    public void editProduit(ProduitRequest produitRequest, long produitId) {
        log.info("ProduitServiceImpl | editProduit is called");

        Produit produit
                = produitRepository.findById(produitId)
                .orElseThrow(() -> new StockCustomException(
                        "Produit with given Id not found",
                        NOT_FOUND
                ));
        produit.setNom(produitRequest.getNom());
        produit.setCode(Str.slug(produitRequest.getCode()));
        produit.setCode_acte(Str.slug(produitRequest.getCode_acte()));
        produit.setNom_officiel(produitRequest.getNom_officiel());
        produit.setDci(produitRequest.getDci());
        produit.setAutre(produitRequest.getAutre());
        produitRepository.save(produit);

        log.info("ProduitServiceImpl | editProduit | Produit Updated");
        log.info("ProduitServiceImpl | editProduit | Produit Id : " + produit.getId());
    }

    @Override
    public void deleteProduitById(long produitId) {
        log.info("Produit id: {}", produitId);

        if (!produitRepository.existsById(produitId)) {
            log.info("Im in this loop {}", !produitRepository.existsById(produitId));
            throw new StockCustomException(
                    "Produit with given with Id: " + produitId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Produit with id: {}", produitId);
        produitRepository.deleteById(produitId);
    }
}
