package com.dopediatrie.hosman.bm.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ConsultationRequest {
    private String reference;
    private LocalDateTime date_consultation;
    private String type;
    private String commentaire;
    private String hdm;
    private String patient_ref;
    private String secteur_code;
    private Long consulteur_id;
    private Long attente_num;
    private ConstanteRequest constante;
    private Long ordonnance_id;
    private List<ConsultationMotifRequest> motifs;
    private List<ConsultationActeRequest> actes;
    private List<ConsultationDiagnosticRequest> diagnostics;
}