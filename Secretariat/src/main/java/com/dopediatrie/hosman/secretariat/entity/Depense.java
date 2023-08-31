package com.dopediatrie.hosman.secretariat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Depense {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private double montant;
    private String motif;
    @ManyToOne
    @JoinColumn(name = "rubrique_id")
    private RubriqueDepense rubrique;
    @ManyToOne
    @JoinColumn(name = "beneficiaire_id")
    private Personne beneficiaire;
    private LocalDateTime date_depense;
    private long accordeur_id;
    private long caissier_id;
    private int recu;
}