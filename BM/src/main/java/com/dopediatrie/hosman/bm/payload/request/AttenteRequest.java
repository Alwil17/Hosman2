package com.dopediatrie.hosman.bm.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AttenteRequest {
    private long num_attente;
    private int ordre = 0;
    private boolean attente;
    private boolean en_cours;
    private long patient_id;
    private String medecin;
    private String receveur;
    private String secteur_code;
}