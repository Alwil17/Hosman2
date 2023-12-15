package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class PatientRequest {
    private String nom;
    private String prenoms;
    private LocalDateTime date_naissance;
    private char sexe = 'N';
    private String lieu_naissance;
    private String tel1;
    private String tel2 = "";
    private String email = "";
    private String type_piece = "CNI";
    private String no_piece;
    private LocalDateTime date_ajout;
    private int is_assure = 0;
    private long pays_origine_id = 0;
    private long nationalite_id = 0;

    private ProfessionRequest profession;
    private EmployeurRequest employeur;
    private PersonneAPrevenirRequest personne_a_prevenir;
    private AdresseRequest adresse;
    private AssuranceRequest assurance;
    private List<PatientMaladieRequest> maladies;
    private List<FiliationRequest> parents;

    private double taux_assurance = 0;
    private Date date_debut_assurance;
    private Date date_fin_assurance;

    private String commentaire;
    private String antecedent;

    private long structure_id = 1;
}