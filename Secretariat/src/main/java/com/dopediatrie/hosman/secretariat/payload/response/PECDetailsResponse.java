package com.dopediatrie.hosman.secretariat.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PECDetailsResponse {
    private String assurance;
    private String nom_patient;
    private String prenom_patient;
    private String sexe;
    private String date_naissance;
    private String date_operation;
    private String secteur;
    private String no_facture;
    private double montant_facture;
    private double montant_pec;
    private double montant_verse;
}
