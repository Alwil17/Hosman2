package com.dopediatrie.hosman.secretariat.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FactureModeResponse {
    private long id;
    private long facture_id;
    private long mode_payement_id;
    private double montant;
    private String no_transaction;
    private String nom_service;
}
