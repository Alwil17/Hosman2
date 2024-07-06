package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.entity.Produit;
import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.request.*;
import com.dopediatrie.hosman.bm.payload.response.ProduitResponse;
import com.dopediatrie.hosman.bm.repository.AgenceRepository;
import com.dopediatrie.hosman.bm.repository.DelegueRepository;
import com.dopediatrie.hosman.bm.repository.LaboratoireRepository;
import com.dopediatrie.hosman.bm.repository.ProduitRepository;
import com.dopediatrie.hosman.bm.service.*;
import com.dopediatrie.hosman.bm.utils.Str;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
    private final ClasseService classeService;
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

        LaboratoireRequest laboratoireRequest = produitRequest.getLaboratoire();
        if(laboratoireRequest != null) laboratoireRequest.setAgence(produitRequest.getAgence());
        DelegueRequest delegueRequest = produitRequest.getDelegue();
        if (delegueRequest != null) delegueRequest.setLaboratoire(laboratoireRequest);

        long agence_id = (produitRequest.getAgence() != null) ? agenceService.addAgence(produitRequest.getAgence()) : 0;
        long laboratoire_id = (laboratoireRequest != null) ? laboratoireService.addLaboratoire(laboratoireRequest) : 0;
        long delegue_id = (delegueRequest != null) ? delegueService.addDelegue(delegueRequest) : 0;

        Produit produit
                = Produit.builder()
                .nom(produitRequest.getNom())
                .dci(produitRequest.getDci())
                .autre(produitRequest.getAutre())
                .infos(produitRequest.getInfos())
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
            formeService.deleteAllForProduitId(produit.getId());
            for (FormeRequest formeRequest : produitRequest.getFormes()) {
                formeRequest.setProduit_id(produit.getId());
                formeService.addForme(formeRequest);
            }
        }
        // classe
        if((produitRequest.getClasses() != null) && (produitRequest.getClasses().size() > 0)){
            for (ClasseRequest classeRequest : produitRequest.getClasses()) {
                produitClasseService.deleteAllForProduitId(produit.getId());
                long classe_id = classeService.addClasse(classeRequest);
                if(classe_id != 0){
                    ProduitClasseRequest prc = ProduitClasseRequest.builder()
                            .produit_id(produit.getId())
                            .classe_id(classe_id)
                            .build();
                    produitClasseService.addProduitClasse(prc);
                }
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
            LaboratoireRequest laboratoireRequest = produitRequest.getLaboratoire();
            if(laboratoireRequest != null) laboratoireRequest.setAgence(produitRequest.getAgence());
            DelegueRequest delegueRequest = produitRequest.getDelegue();
            if (delegueRequest != null) delegueRequest.setLaboratoire(laboratoireRequest);

            long agence_id = (produitRequest.getAgence() != null) ? agenceService.addAgence(produitRequest.getAgence()) : 0;
            long laboratoire_id = (laboratoireRequest != null) ? laboratoireService.addLaboratoire(laboratoireRequest) : 0;
            long delegue_id = (delegueRequest != null) ? delegueService.addDelegue(delegueRequest) : 0;

            Produit produit
                    = Produit.builder()
                    .nom(produitRequest.getNom())
                    .dci(produitRequest.getDci())
                    .infos(produitRequest.getInfos())
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
                for (ClasseRequest classeRequest : produitRequest.getClasses()) {
                    long classe_id = classeService.addClasse(classeRequest);
                    if(classe_id != 0){
                        ProduitClasseRequest prc = ProduitClasseRequest.builder()
                                .produit_id(produit.getId())
                                .classe_id(classe_id)
                                .build();
                        produitClasseService.addProduitClasse(prc);
                    }
                }
            }

        }

        log.info("ProduitServiceImpl | addProduit | Produits Created");
    }

    @Override
    public List<Produit> getProduitByNomLike(String nom) {
        log.info("ProduitServiceImpl | getProduitByNom is called");

        return produitRepository.findByNomLike(nom);
    }

    @Override
    public List<Produit> getProduitByDciLike(String dci) {
        log.info("ProduitServiceImpl | getProduitByDci is called");

        return produitRepository.findByDciLike(dci);
    }

    @Override
    public List<Produit> getProduitByClasseLike(String classe) {
        log.info("ProduitServiceImpl | getProduitByClasse is called");
        return produitRepository.findByClasseLike(classe);
    }

    @Override
    public List<Produit> getProduitByLSearchString(String q) {
        log.info("ProduitServiceImpl | getProduitByClasse is called");
        return produitRepository.findByQueryString(q);
    }

    @Override
    public List<Produit> getProduitByLaboLike(String labo) {
        log.info("ProduitServiceImpl | getProduitByLaboLike is called");
        return produitRepository.findByLaboLike(labo);
    }

    @Override
    public List<Produit> getProduitByAgenceIdAndQueryString(long id, String q) {
        log.info("ProduitServiceImpl | getProduitByAgenceId is called");
        if(q != null && !q.isBlank()){
            return produitRepository.findByAgenceId(id);
        }
        return produitRepository.findByAgenceIdAndQueryString(id, q);
    }

    @Override
    public List<Produit> getProduitByLaboIdAndQueryString(long id, String q) {
        log.info("ProduitServiceImpl | getProduitByLaboId is called");
        if(q != null && !q.isBlank()){
            return produitRepository.findByLaboratoireId(id);
        }
        return produitRepository.findByLaboratoireIdAndQueryString(id, q);
    }

    @Override
    public List<Produit> getProduitByDelegueIdAndQueryString(long id, String q) {
        log.info("ProduitServiceImpl | getProduitByDelegueId is called");
        if(q != null && !q.isBlank()){
            return produitRepository.findByDelegueId(id);
        }
        return produitRepository.findByDelegueIdAndQueryString(id, q);
    }

    @Override
    public List<Produit> getProduitByClasseIdAndQueryString(long id, String q) {
        log.info("ProduitServiceImpl | getProduitByClasseId is called");
        if(q != null && !q.isBlank()){
            return produitRepository.findByClasseId(id);
        }
        return produitRepository.findByClasseIdAndQueryString(id, q);
    }


    @Override
    public List<Produit> getProduitByIndicationLike(String indication) {
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
                        () -> new BMCustomException("Produit with given Id not found", NOT_FOUND));


        ProduitResponse produitResponse = new ProduitResponse();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        produitResponse = mapper.convertValue(produit, ProduitResponse.class);
        //copyProperties(produit, produitResponse);

        return produitResponse;
    }

    @Override
    public void editProduit(ProduitRequest produitRequest, long produitId) {
        log.info("ProduitServiceImpl | editProduit is called");

        Produit produit
                = produitRepository.findById(produitId)
                .orElseThrow(() -> new BMCustomException(
                        "Produit with given Id not found",
                        NOT_FOUND
                ));
        produit.setNom(produitRequest.getNom());
        produit.setDci(produitRequest.getDci());
        produit.setAutre(produitRequest.getAutre());
        produit.setInfos(produitRequest.getInfos());
        produitRepository.save(produit);

        log.info("ProduitServiceImpl | editProduit | Produit Updated");
        log.info("ProduitServiceImpl | editProduit | Produit Id : " + produit.getId());
    }

    @Override
    public void deleteProduitById(long produitId) {
        log.info("Produit id: {}", produitId);

        if (!produitRepository.existsById(produitId)) {
            log.info("Im in this loop {}", !produitRepository.existsById(produitId));
            throw new BMCustomException(
                    "Produit with given with Id: " + produitId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Produit with id: {}", produitId);
        produitRepository.deleteById(produitId);
    }
}
