package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssuranceTarifRequest {
    private long assurance_id;
    private long tarif_id;
    private double base_remboursement;
}