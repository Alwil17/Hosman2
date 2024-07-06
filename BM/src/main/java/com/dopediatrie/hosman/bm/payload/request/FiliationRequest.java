package com.dopediatrie.hosman.bm.payload.request;

import lombok.Builder;
import lombok.Data;

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