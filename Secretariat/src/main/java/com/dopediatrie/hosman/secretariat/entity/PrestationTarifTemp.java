package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "prestation_tarif_temp")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrestationTarifTemp {
    @EmbeddedId
    private PrestationTarifTempPK id;

    @ManyToOne
    @MapsId("prestation_temp_id")
    @JoinColumn(name = "prestation_temp_id")
    private PrestationTemp prestation;
    @ManyToOne
    @MapsId("tarif_id")
    @JoinColumn(name = "tarif_id")
    private Tarif tarif;
    private int quantite;
    private double total_price_gros;
}
