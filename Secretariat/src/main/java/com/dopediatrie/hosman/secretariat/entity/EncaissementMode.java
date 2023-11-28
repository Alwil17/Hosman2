package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mode_encaissement")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EncaissementMode {
    @EmbeddedId
    private EncaissementModePK id;

    @ManyToOne
    @MapsId("encaissement_id")
    @JoinColumn(name = "encaissement_id")
    private Encaissement encaissement;
    @ManyToOne
    @MapsId("mode_payement_id")
    @JoinColumn(name = "mode_payement_id")
    private ModePayement mode_payement;
    private double montant;
    private String no_transaction;
    private String nom_service;
}
