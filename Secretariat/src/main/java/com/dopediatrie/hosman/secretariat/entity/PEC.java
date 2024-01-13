package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pec")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PEC {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "assurance_id")
    private Assurance assurance;
    @ManyToOne
    @JoinColumn(name = "tarif_id")
    private Tarif tarif;
    @ManyToOne
    @JoinColumn(name = "facture_id")
    private Facture facture;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    private double montant_pec;
}
