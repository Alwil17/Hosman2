package com.dopediatrie.hosman.bm.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterventionResponse {
    private long id;
    private String reference;
    private LocalDateTime date_intervention;
    private String type;
    private String commentaire;
    private String hdm;
    private long patient_id;
    private long secteur_id;
    private long attente_id;
}
