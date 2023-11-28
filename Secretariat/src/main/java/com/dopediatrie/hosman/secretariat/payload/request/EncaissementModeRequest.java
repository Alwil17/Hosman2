package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EncaissementModeRequest {
    private long encaissement_id;
    private long mode_payement_id;
    private double montant;
    private String no_transaction;
    private String nom_service;
}