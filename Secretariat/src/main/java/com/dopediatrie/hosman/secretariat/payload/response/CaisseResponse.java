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
public class CaisseResponse {
    private long id;
    private String libelle;
    private String slug;
    private LocalDateTime date_ouverture;
    private LocalDateTime date_fermeture;
    private boolean ouvert;
    private double montant;
    private long caissiere_id;
    private long structure_id;
}
