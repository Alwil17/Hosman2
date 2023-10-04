package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Caisse;
import com.dopediatrie.hosman.secretariat.entity.Depense;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.DepenseRequest;
import com.dopediatrie.hosman.secretariat.payload.response.CaisseResponse;
import com.dopediatrie.hosman.secretariat.payload.response.DepenseResponse;
import com.dopediatrie.hosman.secretariat.repository.DepenseRepository;
import com.dopediatrie.hosman.secretariat.repository.PersonneRepository;
import com.dopediatrie.hosman.secretariat.repository.RubriqueDepenseRepository;
import com.dopediatrie.hosman.secretariat.service.CaisseService;
import com.dopediatrie.hosman.secretariat.service.DepenseService;
import com.dopediatrie.hosman.secretariat.service.PersonneService;
import com.dopediatrie.hosman.secretariat.service.RubriqueDepenseService;
import com.dopediatrie.hosman.secretariat.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class DepenseServiceImpl implements DepenseService {
    private final DepenseRepository depenseRepository;
    private final RubriqueDepenseRepository rubriqueDepenseRepository;
    private final PersonneRepository personneRepository;

    private final RubriqueDepenseService rubriqueDepenseService;
    private final PersonneService personneService;
    private final CaisseService caisseService;
    private final String NOT_FOUND = "DEPENSE_NOT_FOUND";
    private final String NOT_PERMITTED = "OPERATION_NOT_PERMITTED";

    @Override
    public List<Depense> getAllDepenses() {
        return depenseRepository.findAll();
    }

    @Override
    public long addDepense(DepenseRequest depenseRequest) {
        log.info("DepenseServiceImpl | addDepense is called");

        long rubriqueId = rubriqueDepenseService.addRubriqueDepense(depenseRequest.getRubrique());
        long beneficaireId = personneService.addPersonne(depenseRequest.getBeneficiaire());

        Depense depense
                = Depense.builder()
                .montant(depenseRequest.getMontant())
                .motif(depenseRequest.getMotif())
                .rubrique(rubriqueDepenseRepository.findById(rubriqueId).orElseThrow())
                .beneficiaire(personneRepository.findById(beneficaireId).orElseThrow())
                .accordeur_id(depenseRequest.getAccordeur_id())
                .caissier_id(depenseRequest.getCaissier_id())
                .date_depense(depenseRequest.getDate_depense())
                .recu(depenseRequest.getRecu())
                .build();

        depense = depenseRepository.save(depense);

        caisseService.substractAmountCaisse(depense.getMontant());

        log.info("DepenseServiceImpl | addDepense | Depense Created");
        log.info("DepenseServiceImpl | addDepense | Depense Id : " + depense.getId());
        return depense.getId();
    }

    @Override
    public DepenseResponse getDepenseById(long depenseId) {
        log.info("DepenseServiceImpl | getDepenseById is called");
        log.info("DepenseServiceImpl | getDepenseById | Get the depense for depenseId: {}", depenseId);

        Depense depense
                = depenseRepository.findById(depenseId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Depense with given Id not found", NOT_FOUND));

        DepenseResponse depenseResponse = new DepenseResponse();

        copyProperties(depense, depenseResponse);

        log.info("DepenseServiceImpl | getDepenseById | depenseResponse :" + depenseResponse.toString());

        return depenseResponse;
    }

    @Override
    public void editDepense(DepenseRequest depenseRequest, long depenseId) {
        log.info("DepenseServiceImpl | editDepense is called");

        Depense depense
                = depenseRepository.findById(depenseId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Depense with given Id not found",
                        NOT_FOUND
                ));

        // Si la rubrique change
        long rubriqueId;
        if(!depense.getRubrique().getNom().equals(depenseRequest.getRubrique().getNom())){
            if(rubriqueDepenseRepository.existsByNom(depenseRequest.getRubrique().getNom())){
                rubriqueId = rubriqueDepenseRepository.findByNom(depenseRequest.getRubrique().getNom()).orElseThrow().getId();
            }else {
                rubriqueId = rubriqueDepenseService.addRubriqueDepense(depenseRequest.getRubrique());
            }
        }else{
            rubriqueId = rubriqueDepenseService.addRubriqueDepense(depenseRequest.getRubrique());
        }

        // si le bénéficiaire change
        personneService.addPersonne(depenseRequest.getBeneficiaire());

        // Si le montant change
        depense.setMotif(depenseRequest.getMotif());
        depense.setRubrique(rubriqueDepenseRepository.findById(rubriqueId).orElseThrow());
        depense.setAccordeur_id(depenseRequest.getAccordeur_id());
        depense.setCaissier_id(depenseRequest.getCaissier_id());
        depense.setDate_depense(depenseRequest.getDate_depense());
        depense.setRecu(depenseRequest.getRecu());
        depense.setDate_modification(LocalDateTime.now());
        depense = depenseRepository.save(depense);


        /*
        Motification du montant de la dépense
        * On ne peut modifier une dépenser que si sa caisse est ouverte.
        * Si la caisse est déjà fermée alors pas de modification de montant.
        */
        // On cherche la caisse ouverte
        CaisseResponse caisse = caisseService.getCurrentCaisse();
        // Si la date de la dépense est après l'ouverture de la caisse alors:
        if(depense.getDate_depense().isAfter(caisse.getDate_ouverture())){
            // Si le montant de la dépense est différent du nouveau montant alors:
            if(depense.getMontant() != depenseRequest.getMontant()){
                double toAdd = 0;
                // Si le montant de la dépense est supérieur au nouveau montant alors :
                if (depense.getMontant() > depenseRequest.getMontant()){
                    // On doit ajouter de l'argent à la caisse
                    // on calcul alors la somme à ajouter
                    toAdd = depense.getMontant() - depenseRequest.getMontant();
                    // Et on l'ajoute
                    caisseService.addAmountCaisse(toAdd);
                }else{
                    // Sinon si le montant de la dépense est inféieur au nouveau montant alors :
                    // On doit retirer de l'argent à la caisse
                    // on calcul alors la somme à retier
                    toAdd = depenseRequest.getMontant() - depense.getMontant();
                    // Et on la retire
                    caisseService.substractAmountCaisse(toAdd);
                }
                // On met à jour le nouveau montant de la dépense
                depense.setMontant(depenseRequest.getMontant());
                depense = depenseRepository.save(depense);
            }
            // Si les deux montants sont les mêmes, on ne fait rien
        }else {
            // Si la date de la dépense est avant l'ouverture de la caisse alors:
            // Nous sommes dans le cas d'une dépense antérieure
            // On signale à l'utilisateur que l'opération n'est pas permise.
            throw new SecretariatCustomException(
                    "Vous ne pouvez pas modifier cette dépense car la caisse à laquelle elle est liée a déjà été fermée.",
                    NOT_PERMITTED);
        }


        log.info("DepenseServiceImpl | editDepense | Depense Updated");
        log.info("DepenseServiceImpl | editDepense | Depense Id : " + depense.getId());
    }

    @Override
    public void deleteDepenseById(long depenseId) {
        log.info("Depense id: {}", depenseId);

        if (!depenseRepository.existsById(depenseId)) {
            log.info("Im in this loop {}", !depenseRepository.existsById(depenseId));
            throw new SecretariatCustomException(
                    "Depense with given with Id: " + depenseId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Depense with id: {}", depenseId);
        Depense depense = depenseRepository.findById(depenseId).get();
        /*
        Suppression de la dépense
        * On ne peut supprimer une dépense que si sa caisse est ouverte.
        * Si la caisse est déjà fermée alors pas de suppression.
        */
        // On cherche la caisse ouverte
        CaisseResponse caisse = caisseService.getCurrentCaisse();
        // Si la date de la dépense est après l'ouverture de la caisse alors:
        if(depense.getDate_depense().isAfter(caisse.getDate_ouverture())){
            // On restitue la somme à la caisse
            caisseService.addAmountCaisse(depense.getMontant());
            depenseRepository.deleteById(depenseId);
        }else {
            // Si la date de la dépense est avant l'ouverture de la caisse alors:
            // Nous sommes dans le cas d'une dépense antérieure
            // On signale à l'utilisateur que l'opération n'est pas permise.
            throw new SecretariatCustomException(
                    "Vous ne pouvez pas supprimer cette dépense car la caisse à laquelle elle est liée a déjà été fermée.",
                    NOT_PERMITTED);
        }
    }
}
