package com.dopediatrie.hosman.auth.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeResponse {
    private long id;
    private String matricule;
    private String nom;
    private String prenoms;
    private LocalDateTime date_naissance;
    private char sexe;
    private String lieu_naissance;
    private String bureau;
    private String tel1;
    private String tel2;
    private String domicile;
    private String email;
    private String adresse;
    private String localisation;
    private String type_piece;
    private String no_piece;
    private String provenance;
    private String bip;
    private String no_poste;
    private String autres;
    private LocalDateTime date_debut;
    private LocalDateTime date_fin;
    private boolean is_employe;
    private boolean is_temporaire;
    private boolean is_medecin;
    private String secteur;
    private long structure_id;
    private long profession_id;
    private long nationalite_id;
}
