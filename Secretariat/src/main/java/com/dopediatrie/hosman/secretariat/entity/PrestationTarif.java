package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "prestation_tarif")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrestationTarif {
    @EmbeddedId
    private PrestationTarifPK id;

    @ManyToOne
    @MapsId("prestation_id")
    @JoinColumn(name = "prestation_id")
    private Prestation prestation;
    @ManyToOne
    @MapsId("tarif_id")
    @JoinColumn(name = "tarif_id")
    private Tarif tarif;
    private int quantite;
    private double total_price_gros;
}

