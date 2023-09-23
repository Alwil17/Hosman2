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
    private long medecin_id;
    private long receveur_id;
    private long secteur_id;
    private long facture_id;
    private long structure_id = 1;
}