package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrestationTarifTempRequest {
    private long prestation_temp_id;
    private long tarif_id;
    private int quantite = 0;
    private double total_price_gros;
}