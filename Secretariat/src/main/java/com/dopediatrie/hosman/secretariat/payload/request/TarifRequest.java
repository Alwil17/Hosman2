package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TarifRequest {
    private String designation;
    private double tarif_non_assure = 0;
    private double tarif_etr_non_assure = 0;
    private double tarif_assur_locale = 0;
    private double tarif_assur_etr = 0;
    private double tarif_assur_hors_zone = 0;
    private long structure_id;
    private long sous_acte_id;
    private String description = "";
}