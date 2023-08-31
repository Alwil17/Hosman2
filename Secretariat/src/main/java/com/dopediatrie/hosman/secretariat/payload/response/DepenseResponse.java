package com.dopediatrie.hosman.secretariat.payload.response;

import com.dopediatrie.hosman.secretariat.entity.Personne;
import com.dopediatrie.hosman.secretariat.entity.RubriqueDepense;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepenseResponse {
    private long id;
    private double montant;
    private String motif;
    private RubriqueDepense rubrique;
    private Personne beneficiaire;
    private LocalDateTime date_depense;
    private long accordeur_id;
    private long caissier_id;
}
