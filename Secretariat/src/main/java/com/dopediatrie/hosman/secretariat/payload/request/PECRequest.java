package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PECRequest {
    private long assurance_id;
    private long tarif_id;
    private long facture_id;
    private long patient_id;
    private double montant_pec;
}