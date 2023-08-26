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
    private double majoration;
    private double reduction;
    private double a_payer;
    private double verse;
    private double reliquat;
    private double creance;
    private String mode_payement;
    private LocalDateTime date_facture;
    private LocalDateTime date_reglement;
    private int etat_id;
    private int exporte;
    @OneToOne(mappedBy = "facture")
    private Prestation prestation;
}
