package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mode_facture")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FactureMode {
    @EmbeddedId
    private FactureModePK id;

    @ManyToOne
    @MapsId("facture_id")
    @JoinColumn(name = "facture_id")
    private Facture facture;
    @ManyToOne
    @MapsId("mode_payement_id")
    @JoinColumn(name = "mode_payement_id")
    private ModePayement mode_payement;
    private double montant;
    private String no_transaction;
    private String nom_service;
}
