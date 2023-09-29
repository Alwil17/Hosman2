package com.dopediatrie.hosman.secretariat.payload.response;

import com.dopediatrie.hosman.secretariat.entity.Etat;
import com.dopediatrie.hosman.secretariat.entity.Facture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReliquatResponse {
    private long id;
    private double montant;
    private Etat etat;
    private String facture_ref;
    private LocalDateTime date_operation;
    private LocalDateTime date_retrait;
}
