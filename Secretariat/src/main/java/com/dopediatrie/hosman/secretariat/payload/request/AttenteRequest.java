package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AttenteRequest {
    private long num_attente;
    private int ordre = 0;
    private boolean attente;
    private LocalDateTime date_attente;
    private long patient_id;
    private String medecin;
    private String receveur;
    private String secteur_code;
    private long facture_id;
    private boolean en_cours;
    private boolean urgence;
    private long structure_id = 1;
}