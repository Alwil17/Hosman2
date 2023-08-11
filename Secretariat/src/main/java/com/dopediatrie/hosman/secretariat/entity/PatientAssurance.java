package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "patient_assurance")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientAssurance {
    @EmbeddedId
    private PatientAssurancePK id;
    @ManyToOne
    @MapsId("patient_id")
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne
    @MapsId("assurance_id")
    @JoinColumn(name = "assurance_id")
    private Assurance assurance;
    private double taux;
    private Date date_debut;
    private Date date_fin;
}
