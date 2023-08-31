package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FactureModeRequest {
    private long facture_id;
    private long mode_payement_id;
    private double montant;
}