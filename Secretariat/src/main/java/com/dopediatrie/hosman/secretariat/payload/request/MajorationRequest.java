package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MajorationRequest {
    private double montant = 0;
    private String motif = "";
    private LocalDateTime date_operation;
    private long patient_id;
}