package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EtatRequest {
    private String nom;
    private String couleur;
    private int indice = 0;
    private long structure_id = 1;
}