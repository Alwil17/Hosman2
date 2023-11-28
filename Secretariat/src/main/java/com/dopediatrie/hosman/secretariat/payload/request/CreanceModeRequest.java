package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CreanceModeRequest {
    private long creance_id;
    private long mode_payement_id;
    private double montant;
    private LocalDateTime date_depot;
    private String no_transaction;
    private String nom_service;
}