package com.dopediatrie.hosman.auth.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class EmployeRequest {
    private String matricule;
    private String nom;
    private String prenoms;
    private char sexe;
    private String tel1;
    private String tel2;
    private String email;
    private String adresse;
    private String localisation;
    private String autres;
    private LocalDateTime date_debut;
    private LocalDateTime date_fin;
    private boolean is_employe;
    private boolean is_temporaire;
    private long departement_id;
    private long structure_id;
    private long profession_id;
    private long nationalite_id;

    private List<EmployePosteRequest> postes;
}
