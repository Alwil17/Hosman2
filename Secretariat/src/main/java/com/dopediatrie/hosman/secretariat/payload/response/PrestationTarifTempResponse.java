package com.dopediatrie.hosman.secretariat.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrestationTarifTempResponse {
    private long id;
    private long prestation_temp_id;
    private long tarif_id;
    private int quantite;
    private double total_price_gros;
}
