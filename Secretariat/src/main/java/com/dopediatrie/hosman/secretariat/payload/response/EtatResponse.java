package com.dopediatrie.hosman.secretariat.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EtatResponse {
    private long id;
    private String nom;
    private String slug;
    private String couleur;
    private int indice;
    private long structure_id;
}
