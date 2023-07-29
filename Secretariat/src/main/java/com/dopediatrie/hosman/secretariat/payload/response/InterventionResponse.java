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
public class InterventionResponse {
    private long id;
    private long patient_id;
    private LocalDateTime date_entree;
    private LocalDateTime date_sortie;
    private String commentaires;
}
