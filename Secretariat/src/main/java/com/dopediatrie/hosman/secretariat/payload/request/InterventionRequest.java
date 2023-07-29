package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InterventionRequest {
    private long patient_id;
    private LocalDateTime date_entree;
    private LocalDateTime date_sortie;
    private String commentaires;
}