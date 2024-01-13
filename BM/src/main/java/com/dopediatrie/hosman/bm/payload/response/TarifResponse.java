package com.dopediatrie.hosman.bm.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TarifResponse {
    private long id;
    private String libelle;
    private String slug;
    private String code;
    private String description;
    private double tarif_non_assure;
    private double tarif_etr_non_assure;
    private double tarif_assur_locale;
    private double tarif_assur_etr;
    private double tarif_assur_hors_zone;
    private long structure_id;
    private ActeResponse acte;
}
