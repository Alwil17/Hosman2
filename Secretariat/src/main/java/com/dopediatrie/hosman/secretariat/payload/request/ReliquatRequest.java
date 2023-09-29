package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReliquatRequest {
    private double montant = 0;
    private long etat_id;
    private LocalDateTime date_operation;
    private LocalDateTime date_retrait;
    private long patient_id;
}