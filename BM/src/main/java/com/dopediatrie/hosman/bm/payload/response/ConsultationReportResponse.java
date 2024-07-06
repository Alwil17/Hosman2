package com.dopediatrie.hosman.bm.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationReportResponse {
    private String reference;
    private String nom;
    private String prenoms;
    private Date date_naissance;
    private String sexe;
    private Date date_consultation;
    private String secteur;
    private String medecin;
    private String actes;
    private String motifs;
    private String diagnostics;
    private float montant_facture;
    // constantes
    private double poids;
    private double taille;
    private String tension;
    private double temperature;
    private double poul;
    private double perimetre_cranien;
    private double frequence_respiratoire;
}
