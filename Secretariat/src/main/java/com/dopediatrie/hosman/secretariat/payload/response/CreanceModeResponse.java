package com.dopediatrie.hosman.secretariat.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreanceModeResponse {
    private long id;
    private long creance_id;
    private long mode_payement_id;
    private double montant;
    private LocalDateTime date_depot;
    private String no_transaction;
    private String nom_service;
}
