package com.dopediatrie.hosman.hospi.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HospitRequest {
    private String motif;
    private String diagnostic;
    private String hdm;
    private String patient_ref;
    private String secteur_code;
    private String consultation_ref;
    private String arrive;
    private String extras;
    private int status;
    private LocalDateTime date_hospit;
}