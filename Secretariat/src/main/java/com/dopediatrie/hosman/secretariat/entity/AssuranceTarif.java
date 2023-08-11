package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "assurance_tarif")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssuranceTarif {
    @EmbeddedId
    private AssuranceTarifPK id;

    @ManyToOne
    @MapsId("assurance_id")
    @JoinColumn(name = "assurance_id")
    private Assurance assurance;
    @ManyToOne
    @MapsId("tarif_id")
    @JoinColumn(name = "tarif_id")
    private Tarif tarif;
    private double base_remboursement;
}
