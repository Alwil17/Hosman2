package com.dopediatrie.hosman.bm.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConstanteResponse {
    private long id;
    private double poids;
    private double taille;
    private String tension;
    private double temperature;
    private double poul;
    private double perimetre_cranien;
    private double frequence_respiratoire;
}
