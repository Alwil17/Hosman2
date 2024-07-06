package com.dopediatrie.hosman.hospi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HospitResponse {
    private long id;
    private String motif_code;
    private MotifResponse motif;
    private String diagnostic_code;
    private DiagnosticResponse diagnostic;
    private String hdm;
    private String patient_ref;
    private PatientResponse patient;
    private String secteur_code;
    private SecteurResponse secteur;
    private String arrive;
    private String extras;
    private int status;
    private String consultation_ref;
    private ConsultationResponse consultation;
    private LocalDateTime date_hospit;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
