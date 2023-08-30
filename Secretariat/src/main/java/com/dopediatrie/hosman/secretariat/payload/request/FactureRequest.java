package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FactureRequest {
    private String reference = "";
    private double total = 0;
    private double montant_pec = 0;
    private MajorationRequest majorationRequest;
    private ReductionRequest reductionRequest;
    private double a_payer = 0;
    private double verse = 0;
    private ReliquatRequest reliquatRequest;
    private CreanceRequest creanceRequest;
    private String mode_payement = "especes";
    private LocalDateTime date_facture = LocalDateTime.now();
    private LocalDateTime date_reglement = LocalDateTime.now();
    private long etat_id;
    private int exporte = 0;
}