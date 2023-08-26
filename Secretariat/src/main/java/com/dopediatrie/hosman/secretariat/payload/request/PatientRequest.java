package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PatientRequest {
    private String reference = "";
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
    private long pays_origine_id;
    private long nationalite_id;
    private long profession_id;
    private long employeur_id;

    private PersonneAPrevenirRequest personne_a_prevenir;
    private AdresseRequest adresse;
    private AssuranceRequest assurance;
    private PatientAssuranceRequest patient_assurance;

    private long structure_id = 1;
}