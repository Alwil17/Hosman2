package com.dopediatrie.hosman.hospi.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MedecinRequest {
    private String matricule;
    private String nom;
    private String prenoms;
    private LocalDateTime date_naissance;
    private char sexe;
    private String lieu_naissance;
    private String tel1;
    private String tel2;
    private String email;
    private String adresse;
    private String localisation;
    private String autres;
    private String provenance;
    private LocalDateTime date_debut;
    private LocalDateTime date_fin;
    private boolean is_employe;
    private boolean is_temporaire;
    private boolean is_medecin;
    private String type_piece;
    private String no_piece;
    private String secteur;
    private long structure_id;
    private long profession_id;
    private long nationalite_id;
    private String type = "interne";
}