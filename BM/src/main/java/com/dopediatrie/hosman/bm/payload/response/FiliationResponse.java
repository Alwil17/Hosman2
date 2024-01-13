package com.dopediatrie.hosman.bm.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FiliationResponse {
    private long id;
    private String nom;
    private String prenoms;
    private String telephone;
    private char sexe;
    private String type;
    private int annee_naissance;
    private ProfessionResponse profession;
    private EmployeurResponse employeur;
    private AssuranceResponse assurance;
}
