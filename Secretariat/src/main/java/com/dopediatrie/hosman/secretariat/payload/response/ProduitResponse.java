package com.dopediatrie.hosman.secretariat.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProduitResponse {
    private long id;
    private String code;
    private String nom;
    private String nom_officiel;
    private String dci;
    private String code_acte;
    private String autre;
    private double prix;
}
