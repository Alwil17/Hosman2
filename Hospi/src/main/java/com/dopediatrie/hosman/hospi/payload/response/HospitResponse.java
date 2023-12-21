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
    private String motif;
    private String diagnostic;
    private String hdm;
    private long patient_id;
    private PatientResponse patient;
    private long secteur_id;
    private SecteurResponse secteur;
    private long consultation_id;
    private LocalDateTime date_hospit;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
