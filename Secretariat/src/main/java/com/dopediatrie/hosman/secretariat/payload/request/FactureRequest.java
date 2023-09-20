package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class FactureRequest {
    private String reference = "";
    private double total = 0;
    private double montant_pec = 0;
    private MajorationRequest majoration;
    private ReductionRequest reduction;
    private double a_payer = 0;
    private ReliquatRequest reliquat;
    private CreanceRequest creance;
    private LocalDateTime date_facture = LocalDateTime.now();
    private LocalDateTime date_reglement = LocalDateTime.now();
    private long etat_id;
    private long patient_id;
    private long prestation_id;
    private int exporte = 0;
    private List<FactureModeRequest> mode_payements;
}