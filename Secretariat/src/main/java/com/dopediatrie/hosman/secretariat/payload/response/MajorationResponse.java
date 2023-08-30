package com.dopediatrie.hosman.secretariat.payload.response;

import com.dopediatrie.hosman.secretariat.entity.Etat;
import com.dopediatrie.hosman.secretariat.entity.Facture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MajorationResponse {
    private long id;
    private double montant;
    private String motif;
}
