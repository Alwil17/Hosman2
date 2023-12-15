package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "maladie_patient")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientMaladie {
    @EmbeddedId
    private PatientMaladiePK id;

    @ManyToOne
    @MapsId("patient_id")
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne
    @MapsId("maladie_id")
    @JoinColumn(name = "maladie_id")
    private Maladie maladie;
}
