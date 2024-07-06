package com.dopediatrie.hosman.bm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ordonnance_prescriptions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdonnancePrescription {
    @EmbeddedId
    private OrdonnancePrescriptionPK id;
    @ManyToOne
    @MapsId("ordonnance_id")
    @JoinColumn(name = "ordonnance_id")
    private Ordonnance ordonnance;
    @ManyToOne
    @MapsId("prescription_id")
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;
}
