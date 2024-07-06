package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FiliationRequest {
    private String nom;
    private String prenoms;
    private String profession;
    private String employeur;
    private int assurance;
    private String telephone;
    private char sexe;
    private String type;
    private int annee_naissance;
    private long patient_id;
}