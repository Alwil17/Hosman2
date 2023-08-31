package com.dopediatrie.hosman.secretariat.payload.request;

import com.dopediatrie.hosman.secretariat.entity.Personne;
import com.dopediatrie.hosman.secretariat.entity.RubriqueDepense;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DepenseRequest {
    private double montant;
    private String motif;
    private NameRequest rubrique;
    private PersonneRequest beneficiaire;
    private LocalDateTime date_depense;
    private long accordeur_id = 1;
    private long caissier_id = 1;
    private int recu = 0;
}