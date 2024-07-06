package com.dopediatrie.hosman.hospi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttenteResponse {
    private long id;
    private long num_attente;
    private int ordre;
    private boolean attente;
    private MedecinResponse medecin_consulteur;
    private LocalDateTime date_attente;
    private MedecinResponse medecin_receveur;
    private boolean urgence;
    private long structure_id;
}
