package com.dopediatrie.hosman.bm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "motif_intervention")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterventionMotif {
    @EmbeddedId
    private InterventionMotifPK id;

    @ManyToOne
    @MapsId("intervention_id")
    @JoinColumn(name = "intervention_id")
    private Intervention intervention;
    @ManyToOne
    @MapsId("motif_id")
    @JoinColumn(name = "motif_id")
    private Motif motif;
    private double montant;
}
