package com.dopediatrie.hosman.secretariat.payload.response;

import com.dopediatrie.hosman.secretariat.entity.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FactureResponse {
    private long id;
    private String reference;
    private double total;
    private double montant_pec;
    private Majoration majoration;
    private Reduction reduction;
    private double a_payer;
    private double verse;
    private Reliquat reliquat;
    private Creance creance;
    private String mode_payement;
    private LocalDateTime date_facture;
    private LocalDateTime date_reglement;
    private Etat etat;
    private int exporte;
    private long prestation_id;
    private Prestation prestation;
    private String path;
}
