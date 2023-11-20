package com.dopediatrie.hosman.bm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "diagnostic_intervention")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterventionDiagnostic {
    @EmbeddedId
    private InterventionDiagnosticPK id;

    @ManyToOne
    @MapsId("intervention_id")
    @JoinColumn(name = "intervention_id")
    private Intervention intervention;
    @ManyToOne
    @MapsId("diagnostic_id")
    @JoinColumn(name = "diagnostic_id")
    private Diagnostic diagnostic;
    private double montant;
}
