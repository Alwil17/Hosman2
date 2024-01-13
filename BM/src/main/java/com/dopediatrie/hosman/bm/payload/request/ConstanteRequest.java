package com.dopediatrie.hosman.bm.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConstanteRequest {
    private double poids;
    private double taille;
    private String tension;
    private double temperature;
    private double poul;
    private double perimetre_cranien;
    private double frequence_respiratoire;
}