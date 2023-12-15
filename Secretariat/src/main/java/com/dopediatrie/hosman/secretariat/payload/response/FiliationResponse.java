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
public class FiliationResponse {
    private long id;
    private String nom;
    private String prenoms;
    private String telephone;
    private char sexe;
    private String type;
    private int annee_naissance;
}
