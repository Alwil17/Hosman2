package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreanceRequest {
    private double montant = 0;
    private long facture_id;
    private long etat_id;
}