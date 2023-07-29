package com.dopediatrie.hosman.secretariat.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssuranceTarifResponse {
    private long id;
    private long assurance_id;
    private long tarif_id;
    private double base_remboursement;
}
