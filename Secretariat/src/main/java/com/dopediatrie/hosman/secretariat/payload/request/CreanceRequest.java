package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CreanceRequest {
    private double montant = 0;
    private long etat_id;
    private long patient_id;
    private LocalDateTime date_operation;
}