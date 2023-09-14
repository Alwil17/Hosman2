package com.dopediatrie.hosman.secretariat.payload.response;

import com.dopediatrie.hosman.secretariat.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse {
    private long id;
    private String reference;
    private String nom;
    private String prenoms;
    private LocalDateTime date_naissance;
    private char sexe;
    private String lieu_naissance;
    private String tel1;
    private String tel2;
    private String email;
    private String type_piece;
    private String no_piece;
    private LocalDateTime date_ajout;
    private int is_assure;
    private Adresse adresse;
    private Pays pays_origine;
    private Pays nationalite;
    private Profession profession;
    private Assurance assurance;
    private Employeur employeur;
    private PersonneAPrevenir personne_a_prevenir;
    private double taux_assurance;
    private Date date_debut_assurance;
    private Date date_fin_assurance;
    private long structure_id;
}
