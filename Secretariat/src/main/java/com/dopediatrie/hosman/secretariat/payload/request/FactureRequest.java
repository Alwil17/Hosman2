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
    private double majoration = 0;
    private double reduction = 0;
    private double a_payer = 0;
    private double verse = 0;
    private double reliquat = 0;
    private double creance = 0;
    private String mode_payement = "especes";
    private LocalDateTime date_facture = LocalDateTime.now();
    private LocalDateTime date_reglement = LocalDateTime.now();
    private int etat_id = 0;
    private int exporte = 0;
}