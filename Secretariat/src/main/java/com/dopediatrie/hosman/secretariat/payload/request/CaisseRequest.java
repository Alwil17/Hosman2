package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CaisseRequest {
    private String libelle;
    private String slug;
    private LocalDateTime date_ouverture;
    private LocalDateTime date_fermeture;
    private boolean ouvert = true;
    private double montant = 0;
    private long caissiere_id = 1;
    private long structure_id = 1;
}