package com.dopediatrie.hosman.hospi.payload.response;

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
public class ConsultationResponse {
    private long id;
    private String reference;
    private LocalDateTime date_consultation;
    private String type;
    private String commentaire;
    private String hdm;
    private String patient_ref;
    private PatientResponse patient;
    private String secteur_code;
    private SecteurResponse secteur;
    private long attente_num;
    private AttenteResponse attente;
    private ConstanteResponse constante;
    private List<MotifResponse> motifs;
    private List<ActeResponse> actes;
    private List<DiagnosticResponse> diagnostics;
}
