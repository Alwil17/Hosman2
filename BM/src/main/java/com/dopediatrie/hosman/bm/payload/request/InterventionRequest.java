package com.dopediatrie.hosman.bm.payload.request;

import com.dopediatrie.hosman.bm.entity.Motif;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class InterventionRequest {
    private String reference;
    private LocalDateTime date_intervention;
    private String type;
    private String commentaire;
    private String hdm;
    private long patient_id;
    private long secteur_id;
    private long attente_id;
    private ConstanteRequest constante;
    private List<InterventionMotifRequest> motifs;
    private List<InterventionDiagnosticRequest> diagnostics;

}