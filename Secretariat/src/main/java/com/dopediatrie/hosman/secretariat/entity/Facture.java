package com.dopediatrie.hosman.secretariat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Facture {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String reference;
    private double total;
    private double montant_pec;
    @OneToOne
    @JoinColumn(name = "majoration_id")
    private Majoration majoration;
    @OneToOne
    @JoinColumn(name = "reduction_id")
    private Reduction reduction;
    private double a_payer;
    private double verse;
    @OneToOne
    @JoinColumn(name = "reliquat_id")
    private Reliquat reliquat;
    @OneToOne
    @JoinColumn(name = "creance_id")
    private Creance creance;
    private String mode_payement;
    private LocalDateTime date_facture;
    private LocalDateTime date_reglement;
    @ManyToOne
    @JoinColumn(name = "etat_id")
    private Etat etat;
    private int exporte;
    @OneToOne(mappedBy = "facture")
    private Prestation prestation;
}
